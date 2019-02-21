package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Spark;

import java.util.ArrayList;
import java.util.Arrays;

public class Vacuum extends Subsystem{

    Spark succer;

    public Vacuum(){
        dictionary = new ArrayList<>(Arrays.asList("Succ",
                "Stopped"));
        succer = new Spark(0);
    }

    public void succSpeed(double speed){
        succer.set(speed);
    }

    public void update(){
        switch(state){
            case "Succ":
                succSpeed(0.75);
                break;
            case "Stopped":
                succSpeed(0);
                break;
        }
    }

}
