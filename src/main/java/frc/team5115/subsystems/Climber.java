package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team5115.Konstanten;
import frc.team5115.commands.drivetrain.DrivetrainLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Climber extends Subsystem {

    TalonSRX front;
    VictorSPX back;

    public Climber(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Stopped"));
        front = new TalonSRX(Konstanten.FRONT_CLIMBER);
        back = new VictorSPX(Konstanten.BACK_CLIMBER);
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
                moveFront(1);
                moveBack(1);
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
