package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.team5115.robot.Robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Drivetrain extends Subsystem{


    //define motor objects
    TalonSRX frontleft;
    TalonSRX frontright;
    TalonSRX backleft;
    TalonSRX backright;

    NetworkTableEntry throttleDisplay;

    Map<String, Object> settings = new HashMap<String, Object>();



    public int direction = 1;

    public Drivetrain(){
        dictionary = new ArrayList<>(Arrays.asList("Driving",
                "HAB3 Drive",
                "Climber Buffer",
                "Stopped"));

        //instantiate the things
        frontleft = new TalonSRX(2);
        frontright = new TalonSRX(1);
        backleft = new TalonSRX(4);
        backright = new TalonSRX(3);

        //front left and front right motors will do the same thing that the back left and back right motor does
        frontright.set(ControlMode.Follower, 2);
        frontleft.set(ControlMode.Follower, 1);

        backright.setInverted(true);

        //assign encoder data to back left and back right motors respectively
        backright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        backleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);

        settings.put("min", 0);
        settings.put("max", 1);
        throttleDisplay = Robot.tab.add("Max Speed", 0.5)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(settings) // specify widget properties here
                .getEntry();
    }



    public void drive(double left, double right, double throttle){
        throttleDisplay.setDouble(throttle);
        //if our joystick gets weird, and somehow surpasses 1.0
        //assume it's 1.0
        if(Math.abs(left) > 1){
            left = Math.signum(left);
        }
        if(Math.abs(right) > 1){
            right = Math.signum(right);
        }
        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, left*throttle);
        backright.set(ControlMode.PercentOutput, right*throttle);
    }

    public double rightDist() {
        System.out.println(backright.getSelectedSensorPosition(0));
        double rightDist = direction * backright.getSelectedSensorPosition(0);
        return rightDist / 1440 * 6 * Math.PI / 12;
    }

    public double rightSpeed() {
        double rightspeed = backright.getSelectedSensorVelocity(0);
        return ((rightspeed * 6 * Math.PI * 10) / (1440 * 12));
    }

    public double averageSpeed() {
        return rightSpeed();
    }

    public void update(){
        switch(state){
            case "Driving":
                System.out.println("driving");
                drive(Robot.im.getLeft(),
                        Robot.im.getRight(),
                        Robot.im.processThrottle());
                break;
            case "HAB3 drive":
                if(compareTime(2)){
                    setState("Climber Buffer");
                }
                drive(0.5, 0.5, 0.5);
                break;
            case "Climber Buffer":
                setState("Driving");
                break;
            case "Stopped":
                drive(0,0,0);
                System.out.println("stopped");
                break;
        }
    }


}
