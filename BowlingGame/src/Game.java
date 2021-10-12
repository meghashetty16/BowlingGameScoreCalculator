import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game {

    public Bowler bowler;
    public ArrayList<Frame> frames;
    BufferedReader reader;

    public void PlayGame() throws IOException {
        RegisterPlayer();
        MakeTry();
        CalculateResults();
    }

    public Game() {
        bowler = new Bowler();
        bowler.setFrames(NewScoreTable());
        frames = bowler.getFrames();
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void RegisterPlayer() throws IOException {
        System.out.println("Please enter the name of the player");
        bowler.setName(reader.readLine());
        System.out.println("BOWLING GAME BEGINS...");
        System.out.println("please start inserting values...");
    }

    private ArrayList<Frame> NewScoreTable() {
        int triesCounter = 0;
        int indexOfTry = 0;
        ArrayList<Frame> createFrames = new ArrayList<Frame>();
        while (createFrames.size() < 10) {
            if (createFrames.size() + 1 == 10) {
                triesCounter = 3;
            } else {
                triesCounter = 2;
            }
            Frame frame = new Frame();
            indexOfTry = 0;
            while (frame.getTries().size() < triesCounter) {
                Knock newKnock = new Knock();
                newKnock.setKnockNum(++indexOfTry);
                frame.getTries().add(newKnock);
            }
            createFrames.add(frame);
        }
        return createFrames;
    }

    private void MakeTry() throws IOException {
        try {
            int counter = 0;
            for (int i = 0; i <= 9; i++) {
                Frame currentFrame = frames.get(i);
                for (Knock thisKnock : currentFrame.getTries()) {
                    if (!thisKnock.getDone()) {
                        if (i == 9) {
                            counter++;
                            thisKnock = lastFrameKnock(counter, thisKnock, currentFrame);
                        } else {
                            thisKnock.setKnockedDownPins(checkInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisKnock, false));
                        }
                        currentFrame.score += thisKnock.getKnockedDownPins();
                        currentFrame = checkStrikeOrSpare(i, currentFrame, thisKnock);
                    }
                }
                currentFrame.isDone = true;
                CalculateResults();
            }
        } catch (Exception ex) {
            System.out.println("IOException");
            PlayGame();
        }
    }

    private Knock lastFrameKnock(int counter, Knock thisKnock, Frame currentFrame) throws IOException {
        if (counter != 3) {
            thisKnock.setKnockedDownPins(checkInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisKnock, true));
        } else {
            if (currentFrame.isSpare || currentFrame.isStrike) {
                thisKnock.setKnockedDownPins(checkInsertedValue(Integer.parseInt(reader.readLine()), currentFrame, thisKnock, true));
            }
        }
        return thisKnock;
    }

    private void firstFrameKnock(int i, Frame currentFrame) {
        if (!currentFrame.isStrike && !currentFrame.isSpare) {
            currentFrame.totalScore = currentFrame.score;
        }
        if (currentFrame.isStrike) {
            if (frames.get(i + 1).isStrike) {
                currentFrame.totalScore = currentFrame.score + frames.get(i + 1).score + frames.get(i + 2).getTries().get(0).getKnockedDownPins();
            } else {
                currentFrame.totalScore = currentFrame.score + frames.get(i + 1).score;
            }
        }
        if (currentFrame.isSpare) {
            currentFrame.totalScore = currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins();
        }
    }

    private int checkInsertedValue(Integer value, Frame currentFrame, Knock currentKnock, boolean isLastFrame) {
        if (currentKnock.knockNum == 2 && !isLastFrame) {
            if (value > 10 - currentFrame.getTries().get(0).getKnockedDownPins()) {
                value = 10 - currentFrame.getTries().get(0).getKnockedDownPins();
            }
        }
        if (value >= 10) {
            return 10;
        } else {
            return value;
        }
    }

    private Frame checkStrikeOrSpare(int i, Frame currentFrame, Knock thisKnock) {
        if (thisKnock.getKnockedDownPins() == 10 && thisKnock.knockNum == 1) {
            currentFrame.isStrike = true;
            if (i != 9) {
                currentFrame.getTries().get(1).setDone(true);
            }
        }
        if (thisKnock.knockNum == 2 && currentFrame.score >= 10) {
            currentFrame.isSpare = true;
        }
        return currentFrame;
    }

    private void CalculateResults() {
        for (int i = 0; i <= 9; i++) {
            Frame currentFrame = frames.get(i);
            if (i == 9) {
                currentFrame.isSpare = false;
                currentFrame.isStrike = false;
            }
            if (validateFrame(i, currentFrame)) {
                if (i == 0) {
                    firstFrameKnock(i, currentFrame);
                } else {
                    if (!currentFrame.isStrike && !currentFrame.isSpare) {
                        currentFrame.totalScore = currentFrame.score + frames.get(i - 1).totalScore;
                    }
                    if (currentFrame.isStrike && i != 8) {
                        if (frames.get(i + 1).isStrike) {
                            currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).score + frames.get(i + 2).getTries().get(0).getKnockedDownPins();
                        } else {
                            currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).score;
                        }
                    }
                    if (currentFrame.isStrike && i == 8) {
                        currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins() + frames.get(i + 1).getTries().get(1).getKnockedDownPins();
                    }
                    if (currentFrame.isSpare) {
                        currentFrame.totalScore = frames.get(i - 1).totalScore + currentFrame.score + frames.get(i + 1).getTries().get(0).getKnockedDownPins();
                    }
                }
            }
        }
        // print all frames
        for (int i=0; i<frames.size(); i++){
            System.out.println();
        }
        printFrames(frames);
    }

    private boolean validateFrame(int i, Frame currentFrame) {
        boolean isValid = false;
        if (currentFrame.isDone && i != 8) {
            if (!currentFrame.isStrike && !currentFrame.isSpare) {
                isValid = true;
            }
            if (currentFrame.isStrike) {
                if (frames.get(i + 1).isStrike) {
                    if (frames.get(i + 1).isDone && frames.get(i + 2).isDone) {
                        isValid = true;
                    }
                } else {
                    if (frames.get(i + 1).isDone) {
                        isValid = true;
                    } else {
                        isValid = false;
                    }
                }
            }
            if (currentFrame.isSpare) {
                if (frames.get(i + 1).isDone) {
                    isValid = true;
                }
            }
        }
        if (currentFrame.isDone && i == 8) {
            if (!currentFrame.isStrike && !currentFrame.isSpare) {
                isValid = true;
            }
            if (currentFrame.isStrike || currentFrame.isSpare) {
                if (frames.get(i + 1).isDone) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    private void printFrames(ArrayList<Frame> frames) {
        for (Frame frame : frames) {
            System.out.print("#####     ");
        }
        System.out.println();
        for (Frame frame : frames) {
            System.out.print(" " + frame.totalScore + "       ");
        }
        System.out.println();
        for (Frame frame : frames) {
            for (Knock currentKnock : frame.getTries()) {
                System.out.print("## ");
            }
            System.out.print("    ");
        }
        System.out.println();
        for (Frame frame : frames) {
            for (Knock currentKnock  : frame.getTries()) {
                System.out.print(currentKnock.getKnockedDownPins() + " ");
            }
            System.out.print("     ");
        }
        System.out.println();
        System.out.println();
    }
}
