package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrist extends Subsystem{

    TalonSRX xController;
    TalonSRX yController;

    public Wrist(){
        dictionary = new ArrayList<>(Arrays.asList("Neutral",
                "Up",
                "Down",
                "Left",
                "Right"));

        xController = new TalonSRX(6);
        yController = new TalonSRX(7);
    }

    public void moveX(double speed){
        xController.set(ControlMode.PercentOutput, speed);
    }

    public void moveY(double speed){
        yController.set(ControlMode.PercentOutput, speed);
    }

    public void update(){
        switch(state){
            case "Neutral":
                moveX(0);
                moveY(0);
                break;
            case "Up":
                moveY(0.75);
                //if limit switch hit
                //setState("Neutral");
                break;
            case "Down":
                moveY(-0.75);
                //if limit switch hit
                //setState("Neutral");
                break;
            case "Left":
                moveX(-0.75);
                //if limit switch hit OR controller isn't being held
                //setState("Neutral");
                break;
            case "Right":
                moveX(0.75);
                //if limit switch hit OR controller isn't being held
                //setState("Neutral");
                break;
        }
    }

}
