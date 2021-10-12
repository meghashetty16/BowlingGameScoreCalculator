import java.util.ArrayList;

/*
Frame class represents 2 chances that a player has to knock down 10 pins except for the last frame.
One Game has 10 frames.
 */
public class Frame {

    private ArrayList<Knock> tries;
    int score;
    public boolean isStrike;
    public boolean isSpare;
    public int totalScore;
    public boolean isDone;

    public Frame(){
        this.tries = new ArrayList<Knock>();
        this.score = 0;
        this.isStrike = false;
        this.isSpare = false;
        this.totalScore = 0;
        this.isDone = false;
    }

    public ArrayList<Knock> getTries() {
        return tries;
    }

    public void setTries(ArrayList<Knock> tries) {
        this.tries = tries;
    }


}
