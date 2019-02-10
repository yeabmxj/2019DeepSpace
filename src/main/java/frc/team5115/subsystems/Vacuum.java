package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Spark;

import java.util.ArrayList;
import java.util.Arrays;

public class Vacuum extends Subsystem{

    Spark sucker;

    public Vacuum(){
        dictionary = new ArrayList<>(Arrays.asList("Succ",
                "Stopped"));

        sucker = new Spark(1);
    }

    public void update(){
        switch(state){
            case "Stopped":
                sucker.set(0);
                break;
            case "Succ":
                sucker.set(0.5);
                break;
        }
    }

}
