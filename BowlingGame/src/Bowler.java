import java.util.ArrayList;

public class Bowler {

    private String name;
    public ArrayList<Frame> frames;

    public Bowler(){
        this.frames = new ArrayList<Frame>();
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<Frame> frames) {
        this.frames = frames;
    }
}
