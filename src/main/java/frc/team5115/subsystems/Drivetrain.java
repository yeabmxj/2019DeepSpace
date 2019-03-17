package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.team5115.Konstanten;
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

    public Trajectory trajectory;
    public Trajectory.Config config;
    public TankModifier modifier;
    public EncoderFollower left, right;

    public Drivetrain(){
        dictionary = new ArrayList<>(Arrays.asList("Driving",
                "Following",
                "Stopped"));

        //instantiate the things
        frontleft = new TalonWrapper(Konstanten.FRONT_LEFT_DRIVE);
        frontright = new TalonWrapper(Konstanten.FRONT_RIGHT_DRIVE);
        backleft = new TalonWrapper(Konstanten.BACK_LEFT_DRIVE);
        backright = new TalonWrapper(Konstanten.BACK_RIGHT_DRIVE);

        navX = new AHRS(SPI.Port.kMXP);

        //front left and front right motors will do the same thing that the back left and back right motor does
        frontleft.set(ControlMode.Follower, Konstanten.BACK_LEFT_DRIVE);
        frontright.set(ControlMode.Follower, Konstanten.BACK_RIGHT_DRIVE);

        //assign encoder data to back left and back right motors respectively
        frontright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        frontleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);

        frontright.setInverted(true);
        backright.setInverted(true);

        settings.put("min", 0);
        settings.put("max", 1);
        throttleDisplay = Konstanten.tab.add("Max Speed", 0.5)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(settings) // specify widget properties here
                .getEntry();

        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.05, 1.7, 2, 60.0);

    }

    public void drive(double left, double right, double throttle){
        throttleDisplay.setDouble(throttle);
        //if our joystick gets weird, and somehow surpasses 1.0
        //assume it's 1.0
//        if(Math.abs(left) > 1){
//            left = Math.signum(left);
//        }
//        if(Math.abs(right) > 1){
//            right = Math.signum(right);
//        }
        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, left*throttle);
        backright.set(ControlMode.PercentOutput, right*throttle);
    }

    @Log
    public void resetEncoders(){
        frontright.setSelectedSensorPosition(0);
        frontleft.setSelectedSensorPosition(0);
    }

    public int getRightEncoder(){
        return frontright.getSelectedSensorPosition();
    }

    public int getLeftEncoder(){
        return frontleft.getSelectedSensorPosition();
    }

    public double returnYaw(){
        return navX.getYaw();
    }

    @Log
    public void resetGyro(){
        navX.reset();
    }

    @Log
    public boolean generatePath(){
        if(Robot.limelight.isValid()){
            Waypoint[] points = new Waypoint[]{
                    new Waypoint(0,0, 0),
                    new Waypoint(Robot.limelight.getDistance() * 0.0254, Robot.limelight.getHorizontalOffset() * 0.0254, Pathfinder.d2r(0))
            };
            trajectory = Pathfinder.generate(points, config);


            modifier = new TankModifier(trajectory).modify(Konstanten.BASE_WIDTH);

            left = new EncoderFollower(modifier.getLeftTrajectory());
            right = new EncoderFollower(modifier.getRightTrajectory());
            left.configureEncoder(getLeftEncoder(), Konstanten.TICK_COUNT, Konstanten.WHEEL_RADIUS);
            right.configureEncoder(getRightEncoder(), Konstanten.TICK_COUNT, Konstanten.WHEEL_RADIUS);
            left.configurePIDVA(.5, 0.0, 0, 1 / 1.7, 0);
            right.configurePIDVA(.5, 0.0, 0, 1 /1.7, 0);
            return true;
        } else {
            return false;
        }
    }

    @Log
    public boolean pathFinished(){
        return left.isFinished() && right.isFinished();
    }

    public void update(){
        switch(state){
            case "Driving":
//                System.out.println(navX.getYaw());
//                System.out.println(navX.getPitch());
//                System.out.println(navX.getRoll());
                drive(Robot.im.getLeft(),
                        Robot.im.getRight(),
                        Robot.im.processThrottle());
                break;
            case "Following":
                double angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d((left.getHeading() + right.getHeading())/2) - returnYaw());
                if (Math.abs(angleDifference) > 180.0) {
                    angleDifference = (angleDifference > 0) ? angleDifference - 360 : angleDifference + 360;
                }

                double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

                drive(left.calculate(getLeftEncoder()) + turn, right.calculate(getRightEncoder()) - turn, 1);
                break;
            case "Stopped":
                drive(0,0,0);
                System.out.println("stopped");
                break;
        }
    }


}
