package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import frc.team5115.Konstanten;

import java.util.ArrayList;
import java.util.Arrays;

public class Vacuum extends Subsystem{

    Spark succer;

    Relay blower;

    public Vacuum(){
        dictionary = new ArrayList<>(Arrays.asList("Succ",
                "Stopped"));
        succer = new Spark(Konstanten.VACUUM_SPARK);
        blower = new Relay(Konstanten.SOLENOID_RELAY, Relay.Direction.kBoth);
        solenoidClose();
    }

    public void succSpeed(double speed){
        succer.set(speed);
    }

    public void solenoidClose(){blower.set(Relay.Value.kOff);}
    public void solenoidOpen(){blower.set(Relay.Value.kForward);}


    public void update(){
        //System.out.println(blower.get());
        switch(state){
            case "Succ":
                succSpeed(0.75);
                break;
            case "Stopped":
                succSpeed(0);
                solenoidOpen();
                if(compareTime(1)){
                    solenoidClose();
                }
                break;
        }
    }

}
