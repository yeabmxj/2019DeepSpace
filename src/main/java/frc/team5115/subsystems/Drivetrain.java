package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.PID;
import frc.team5115.robot.InputLoop;
import frc.team5115.robot.Robot;

import java.util.HashMap;
import java.util.Map;


public class Drivetrain extends Subsystem{
    //define motor objects
    TalonSRX frontleft;
    TalonSRX frontright;
    TalonSRX backleft;
    TalonSRX backright;
    //define gyroscope object
    AHRS navx;

    Limelight limelight;

    PID forwardControl;
    PID turnControl;
    double forwardVal;
    double turnVal;

    NetworkTableEntry throttleDisplay;
    NetworkTableEntry gyroDisplay;

    Map<String, Object> settings = new HashMap<String, Object>();

    public int direction = 1;

    public Drivetrain(){
        //instantiate the things
        navx = new AHRS(SPI.Port.kMXP);
        frontleft = new TalonSRX(3);
        frontright = new TalonSRX(4);
        backleft = new TalonSRX(1);
        backright = new TalonSRX(2);

        limelight = new Limelight();

        //front left and front right motors will do the same thing that the back left and back right motor does
        frontright.set(ControlMode.Follower, 2);
        frontleft.set(ControlMode.Follower, 1);

        //assign encoder data to back left and back right motors respectively
        backright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        backleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        navx.reset();

        settings.put("min", 0);
        settings.put("max", 1);
        throttleDisplay = Robot.tab.add("Max Speed", 0.5)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(settings) // specify widget properties here
                .getEntry();
        gyroDisplay = Robot.tab.add("Gyro", 0)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
    }


    public void drive(double speed, double turn, double throttle){
        throttleDisplay.setDouble(throttle);
        double leftspeed = -speed + turn;
        double rightspeed = speed + turn;
        //if our joystick gets weird, and somehow surpasses 1.0
        //assume it's 1.0
        if(Math.abs(leftspeed) > 1){
            leftspeed = Math.signum(leftspeed);
        }
        if(Math.abs(rightspeed) > 1){
            rightspeed = Math.signum(rightspeed);
        }
        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, leftspeed*throttle);
        backright.set(ControlMode.PercentOutput, rightspeed*throttle);
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
    public double getTurnVelocity(){
        return navx.getRate();
    }

    public void update(){
        try {
            state = queue.peek();
        } catch (NullPointerException e){
            state = "nothing in queue";
        }
        switch (state){
            default:
                drive(0, 0, 0);
                break;
            case "Drive":
                drive(InputLoop.primary.getForward(), InputLoop.primary.getTurn(), InputLoop.primary.processThrottle());
                break;
            case "Search Target":
                if(limelight.isValid()){
                    limelight.scannerMode();
                    forwardControl = new PID("forward");
                    turnControl = new PID("turn");
                    addTask("Auto");
                    removeCurrentTask();
                } else {
                    addTask("Drive");
                    removeCurrentTask();
                }
                break;
            case "Auto":
                forwardVal = forwardControl.getPID(0, limelight.getYOffset(), averageSpeed());
                turnVal = turnControl.getPID(0, limelight.getXOffset(), getTurnVelocity());
                drive(forwardVal, turnVal, 1);
                if((forwardControl.isFinished(0.03, 0.5) && turnControl.isFinished(0.03, 0.5) || !InputLoop.primary.scanPressed())){
                    forwardControl = null;
                    turnControl = null;
                    limelight.cameraMode();
                    addTask("Drive");
                    removeCurrentTask();
                }
                break;
        }
    }

}
