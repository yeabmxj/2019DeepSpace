package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrist extends Subsystem {

    VictorSPX xAxis;
    VictorSPX yAxis;

    double time;

    public Wrist(){
        xAxis = new VictorSPX(1);
        yAxis = new VictorSPX(2);
    }

    public void getTimestamp(){
        dictionary = new ArrayList<>(Arrays.asList("Move Left",
                "Move Right",
                "Toggle Y",
                "Stopped"));
        time = Timer.getFPGATimestamp();
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
                if(Timer.getFPGATimestamp() > time + 0.5){
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
