package despresso;

import java.util.ArrayList;
import java.util.Arrays;

public class MoodState {

    private String currentMood = "None";

    public MoodState () {

    }

    public String getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood = currentMood;
    }

    public ArrayList<String> splitMood() {
        switch(currentMood) {
            case "Good":
                return new ArrayList<>( Arrays.asList("Good1", "Good2", "Good3", "Good4"));
            case "Bad":
                return new ArrayList<>( Arrays.asList("Bad1", "Bad2", "Bad3", "Bad4"));
            default:
                return null;
        }
    }
}