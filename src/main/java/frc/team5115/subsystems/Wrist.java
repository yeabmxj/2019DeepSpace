package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrist extends Subsystem {

    VictorSPX xAxis;
    VictorSPX yAxis;

    DigitalInput left;
    DigitalInput right;

    public Wrist(){
        dictionary = new ArrayList<>(Arrays.asList("Move Left",
                "Move Right",
                "Toggle Y",
                "Stopped"));
        xAxis = new VictorSPX(1);
        yAxis = new VictorSPX(0);

        left = new DigitalInput(8);
        right = new DigitalInput(7);
    }



    public void moveX(double speed){
        double temp = speed;
        DigitalInput controller = Math.signum(speed) == 1 ? right : left;
        if(!controller.get()){
            temp = 0;
        }
        xAxis.set(ControlMode.PercentOutput, temp);
    }

    public void moveY(double speed){
        yAxis.set(ControlMode.PercentOutput, speed);
    }

    public void update(){
        switch(state){
            case "Move Left":
                System.out.println("moving left");
                moveX(0.75);
                break;
            case "Move Right":
                System.out.println("moving right");
                moveX(-0.75);
                break;
            case "Toggle Y":
                moveY(0.75);
                if(compareTime(0.5)){
                    setState("Stopped");
                }
                break;
            case "Stopped":
                moveX(0);
                moveY(0);
                break;
        }
    }

}
