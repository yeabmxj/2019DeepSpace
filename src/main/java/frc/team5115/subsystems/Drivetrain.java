package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.robot.Robot;

import java.util.HashMap;
import java.util.Map;


public class Drivetrain {
    public boolean inuse;
    //define motor objects
    TalonSRX frontleft;
    TalonSRX frontright;
    TalonSRX backleft;
    TalonSRX backright;
    //define gyroscope object
    AHRS navx;

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

    public double leftDist() {
        double leftDist = -direction * backleft.getSelectedSensorPosition(0);
        return leftDist / 1440 * 6 * Math.PI / 12;
    }

    public double rightDist() {
        System.out.println(backright.getSelectedSensorPosition(0));
        double rightDist = direction * backright.getSelectedSensorPosition(0);
        return rightDist / 1440 * 6 * Math.PI / 12;
    }

    public double distanceTraveled() {
        return rightDist();
    }

    public double leftSpeed() {
        double leftspeed = -backleft.getSelectedSensorVelocity(0);
        return ((leftspeed * 6 * Math.PI * 10) / (1440 * 12));

    }

    public double rightSpeed() {
        double rightspeed = backright.getSelectedSensorVelocity(0);
        return ((rightspeed * 6 * Math.PI * 10) / (1440 * 12));
    }

    public double leftSpeedRaw(){
        return backleft.getSelectedSensorVelocity(0);
    }
    public double rightSpeedRaw(){
        return backright.getSelectedSensorVelocity(0);
    }
    public double leftPosRaw(){
        return backleft.getSelectedSensorPosition(0);
    }
    public double rightPosRaw(){
        return backright.getSelectedSensorPosition(0);
    }

    public double averageSpeed() {
        return rightSpeed();
    }
    public double getYaw() {
        return navx.getYaw();
    }
    public double getPitch(){ return navx.getPitch();}
    public double getRoll(){return navx.getRoll();}
    public double getTurnVelocity(){
        return navx.getRate();
    }
    public void resetEncoders() {
        backleft.setSelectedSensorPosition(0, 0, 5); //5 ms
        backright.setSelectedSensorPosition(0, 0, 5);
    }
    public void resetGyro(){
        navx.reset(); //takes some time
    }


}
