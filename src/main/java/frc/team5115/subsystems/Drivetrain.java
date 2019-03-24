package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.team5115.Konstanten;
import frc.team5115.commands.climber.ClimberLooper;
import frc.team5115.lib.control.SynchronousPIDF;
import frc.team5115.lib.drivers.TalonWrapper;
import frc.team5115.robot.Robot;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Drivetrain extends Subsystem {


    //define motor objects
    public TalonWrapper frontleft;
    public TalonWrapper frontright;
    public TalonWrapper backleft;
    public TalonWrapper backright;

    NetworkTableEntry throttleDisplay;

    Map<String, Object> settings = new HashMap<String, Object>();

    public AHRS navX;

    public SynchronousPIDF loop;

    public double timeDifference;

    public Drivetrain(){
        dictionary = new ArrayList<>(Arrays.asList("Driving",
                "PID",
                "Climber Start",
                "Platform Drive",
                "Climber Buffer",
                "Stopped"));

        //instantiate the things
        frontleft = new TalonWrapper(Konstanten.FRONT_LEFT_DRIVE);
        frontright = new TalonWrapper(Konstanten.FRONT_RIGHT_DRIVE);
        backleft = new TalonWrapper(Konstanten.BACK_LEFT_DRIVE);
        backright = new TalonWrapper(Konstanten.BACK_RIGHT_DRIVE);

        navX = new AHRS(SPI.Port.kMXP);

//        front left and front right motors will do the same thing that the back left and back right motor does
        frontleft.set(ControlMode.Follower, Konstanten.BACK_LEFT_DRIVE);
        frontright.set(ControlMode.Follower, Konstanten.BACK_RIGHT_DRIVE);

        //assign encoder data to back left and back right motors respectively
        frontright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        frontleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
//
        frontright.setInverted(true);
        backright.setInverted(true);

        settings.put("min", 0);
        settings.put("max", 1);
        throttleDisplay = Konstanten.tab.add("Max Speed", 1)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(settings) // specify widget properties here
                .getEntry();

        loop = new SynchronousPIDF(1, 0 ,0, 0);
        loop.setSetpoint(0);
    }

    public void drive(double left, double right, double throttle){
        throttleDisplay.setDouble(throttle);

        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, left*throttle);
        backright.set(ControlMode.PercentOutput, right*throttle);
    }

    public void update(){
        switch(state){
            case "Driving":
                drive(Robot.im.getLeft(),
                        Robot.im.getRight(),
                        Robot.im.processThrottle());
                break;
            case "PID":
                double output = loop.calculate(Robot.limelight.getXOffset(), Timer.getFPGATimestamp() - timeDifference);
                drive(0.5 + output, 0.5 - output, 0.5);
                timeDifference = Timer.getFPGATimestamp();
                break;
            case "Climber Start":
                if(ClimberLooper.system.compareState("Driving Back")){
                    setState("Platform Drive");
                    getTimestamp();
                }
                break;
            case "Platform Drive":
                if(compareTime(3)){
                    setState("Climber Buffer");
                }
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
