package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;

import java.util.ArrayList;
import java.util.Arrays;

public class Vacuum extends Subsystem{

    Spark succer;

    Relay blower;

    public Vacuum(){
        dictionary = new ArrayList<>(Arrays.asList("Succ",
                "Stopped"));
        succer = new Spark(9);
        blower = new Relay(0);
    }

    public void succSpeed(double speed){
        succer.set(speed);
    }

    public void update(){
        switch(state){
            case "Succ":
                blower.set(Relay.Value.kForward);
                succSpeed(0.75);
                break;
            case "Stopped":
                blower.set(Relay.Value.kReverse);
                succSpeed(0);
                break;
        }
    }

}
