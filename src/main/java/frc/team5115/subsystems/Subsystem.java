package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;
import java.util.Arrays;

public class Subsystem {

    ArrayList<String> dictionary;

    String state = "Stopped";
    String lastState;

    double time;

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
            lastState = currentState();
            this.state = state;
        }
    }

    public String currentState(){
        return state;
    }

    public void update(){
        switch(state){
            case "Stopped":
                break;
        }
    }

}
