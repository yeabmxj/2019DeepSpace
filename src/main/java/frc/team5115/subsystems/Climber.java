package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team5115.commands.drivetrain.DrivetrainLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Climber extends Subsystem {

    TalonSRX front;
    VictorSPX back;

    DigitalInput max;
    DigitalInput min;

    public Climber(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Retract Back",
                "Retract Front",
                "Stopped"));
        front = new TalonSRX(5);
        back = new VictorSPX(0);

        max = new DigitalInput(0);
        min = new DigitalInput(1);
    }

    public void moveFront(double speed){
        front.set(ControlMode.PercentOutput, speed);
    }

    public void moveBack(double speed){
        back.set(ControlMode.PercentOutput, speed);
    }

    public void update(){
        switch(state){
            case "Moving Up":
                moveFront(0.5);
                moveBack(0.5);
                if(compareTime(10)){
                    setState("Retract Back");
                }
                break;
            case "Retract Back":
                moveFront(0);
                moveBack(-0.5);
                if(DrivetrainLooper.system.currentState().equals("Climber Buffer")){
                    getTimestamp();
                    setState("Retract Front");
                }
                break;
            case "Retract Front":
                moveFront(-0.5);
                moveBack(0);
                if(compareTime(5)){
                    setState("Stopped");
                }
                break;
            case "Stopped":
                moveFront(0);
                moveBack(0);
                break;
        }
    }

}
