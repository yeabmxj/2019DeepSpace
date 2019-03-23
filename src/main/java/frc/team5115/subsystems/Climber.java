package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5115.Konstanten;
import frc.team5115.lib.drivers.TalonWrapper;

import java.util.ArrayList;
import java.util.Arrays;

public class Climber extends Subsystem {

    TalonWrapper front;
    TalonWrapper back;
    TalonWrapper wheels;

    public Climber(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Stopped"));
        front = new TalonWrapper(Konstanten.FRONT_CLIMBER);
        back = new TalonWrapper(Konstanten.BACK_CLIMBER);
        wheels = new TalonWrapper(7);
    }

    public void moveFront(double speed){
        front.set(ControlMode.PercentOutput, speed);
    }

    public void moveBack(double speed){
        back.set(ControlMode.PercentOutput, speed);
    }

    public void moveWheels(double speed){ wheels.set(ControlMode.PercentOutput, speed);}

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
