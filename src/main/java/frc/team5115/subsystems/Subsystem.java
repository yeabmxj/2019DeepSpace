package frc.team5115.subsystems;

import java.util.ArrayList;

public class Subsystem {

    ArrayList<String> dictionary;

    String state = "Stopped";
    String lastState;

    public void setState(String state){
        if(dictionary.contains(state)){
            lastState = currentState();
            this.state = state;
        }
    }

    public String currentState(){
        return this.state;
    }

    public void update(){
        switch(state){
            case "Stopped":
                break;
        }
    }

}
