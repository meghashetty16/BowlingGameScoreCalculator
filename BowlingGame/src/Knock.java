/*
Knock class represents
 */
public class Knock {

    private int pinsKnocked;
    private boolean isDone;
    public int knockNum;

    public Knock(){
        this.isDone = false;
        this.knockNum = 0;
    }

    public int getKnockedDownPins() {
        return pinsKnocked;
    }

    public void setKnockedDownPins(int pinsKnocked) {
        this.pinsKnocked = pinsKnocked;
    }

    public int getKnockNum() {
        return knockNum;
    }

    public void setKnockNum(int knockNum) {
        this.knockNum = knockNum;
    }

    public boolean getDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

}
