package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrist extends Subsystem {

    VictorSPX xAxis;
    VictorSPX yAxis;

    public Wrist(){
        dictionary = new ArrayList<>(Arrays.asList("Move Left",
                "Move Right",
                "Toggle Y",
                "Stopped"));
        xAxis = new VictorSPX(0);
        yAxis = new VictorSPX(1);
    }



    public void moveX(double speed){
        xAxis.set(ControlMode.PercentOutput, speed);
    }

    public void moveY(double speed){
        yAxis.set(ControlMode.PercentOutput, speed);
    }

    public void update(){
        switch(state){
            case "Move Left":
                moveX(0.5);
                break;
            case "Move Right":
                moveX(-0.5);
                break;
            case "Toggle Y":
                moveY(0.5);
                if(compareTime(0.5)){
                    setState("Stopped");
                }
                break;
            case "Stopped":
                System.out.println("test4");
                moveX(0);
                moveY(0);
                break;
        }
    }

}
