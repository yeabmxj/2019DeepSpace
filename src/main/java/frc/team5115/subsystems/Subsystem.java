package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;
import java.util.Arrays;

public class Subsystem {

    ArrayList<String> dictionary;

    String state = "Stopped";
    private String lastState;

    private double time;

    public Subsystem(){
        //in case a dictionary isn't defined for whatever reason
        dictionary = new ArrayList<>(Arrays.asList("Stopped"));
    }


    public void getTimestamp(){
        time = Timer.getFPGATimestamp();
    }

    public boolean compareTime(double seconds){
        if (Timer.getFPGATimestamp() > time + seconds) {
            time = 0;
            return true;
        }
        return false;
    }

    public void setState(String state){
        if(dictionary.contains(state)){
            lastState = this.state;
            this.state = state;
        }
    }

    public boolean compareState(String comparator){
        return state.equals(comparator);
    }

    public boolean compareLastState(String comparator){return lastState.equals(comparator); }

    public void update(){
        switch(state){
            case "Stopped":
                break;
        }
    }

}
