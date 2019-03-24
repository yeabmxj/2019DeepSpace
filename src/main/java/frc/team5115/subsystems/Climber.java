package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team5115.Konstanten;
import frc.team5115.lib.drivers.TalonWrapper;
import frc.team5115.robot.Robot;

import java.util.ArrayList;
import java.util.Arrays;

public class Climber extends Subsystem {

    TalonSRX front;
    TalonSRX back;
    TalonSRX wheels;

    public Climber(){
        dictionary = new ArrayList<>(Arrays.asList("Going Up",
                "Driving Back",
                "Retract Front",
                "Retract Back",
                "Stopped"));
        front = new TalonSRX(Konstanten.FRONT_CLIMBER);
        back = new TalonSRX(Konstanten.BACK_CLIMBER);
        wheels = new TalonSRX(7);

        front.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        back.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        front.setSelectedSensorPosition(0);
        back.setSelectedSensorPosition(0);
    }

    public void moveFront(double speed){
        front.set(ControlMode.PercentOutput, speed);
    }

    public double getFrontPos(){
        return front.getSelectedSensorPosition();
    }

    public void moveBack(double speed){
        back.set(ControlMode.PercentOutput, speed);
    }

    public double getBackPos(){
        return back.getSelectedSensorPosition();
    }

    public void moveWheels(double speed){ wheels.set(ControlMode.PercentOutput, speed);}

    public void update(){
        switch(state){
            case "Going Up":
                boolean frontfinished = false;
                boolean backfinished = false;
                if(getFrontPos() > 200){
                    moveFront(0);
                    frontfinished = true;
                } else {
                    moveFront(1);
                }

                if(getBackPos() > 19261){
                    moveBack(0);
                    backfinished = true;
                } else {
                    moveBack(0.5);
                }

                if(frontfinished && backfinished){
                    setState("Driving Back");
                    getTimestamp();
                }
                break;
            case "Driving Back":
                if(compareTime(3)){
                    setState("Retract Front");
                } else {
                    moveWheels(0.5);
                }
                break;
            case "Retract Front":
                if(getFrontPos() < 0 && Robot.drive.compareState("Climber Buffer")){
                    setState("Retract Back");
                } else {
                    moveFront(-0.5);
                }
                break;
            case "Retract Back":
                if(getBackPos() < 0){
                    setState("Stopped");
                } else {
                    moveBack(-1);
                }
                break;
            case "Stopped":
                moveFront(0);
                moveBack(0);
                break;
        }
    }

}
