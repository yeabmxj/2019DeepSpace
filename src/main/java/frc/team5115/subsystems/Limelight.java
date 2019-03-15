package frc.team5115.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5115.robot.Robot;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Limelight {

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry LED;
    private NetworkTableEntry CAM;
    private NetworkTableEntry pipeline;

    public static double cameraHeight = 7.5; // Height of limelight
    public static double level1 = 36; //Height of the target(s) in IN
    public static double theta = 24; // Angle of limelight
    public static double xOffset; // The X-Offset from the target
    public static double targetAngle; // The theta used in calculations
    public static double aDistance; // The alignment distance
    public static double PIDAlign; // The analog value resembling distance
    public static double xOffAngle; // The x-offset angle from the target

    public Limelight(){
        NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
        tx = limelight.getEntry("tx");
        ty = limelight.getEntry("ty");
        ta = limelight.getEntry("ta");
        tv = limelight.getEntry("tv");
        LED = limelight.getEntry("ledMode");
        CAM = limelight.getEntry("camMode");
        pipeline = limelight.getEntry("pipeline");
    }

    @Log
    public boolean isValid(){
        return tv.getNumber(1).equals(1);
    }

    public double getXOffset(){
        return tx.getDouble(0);
    }
    public double getYOffset(){
        return ty.getDouble(0);
    }
    public double getContourArea(){
        return ta.getDouble(0);
    }

    @Log
    public void cameraMode(){
        LED.setNumber(1);
        CAM.setNumber(1);
    }

    @Log
    public void scannerMode(){
        LED.setNumber(3);
        CAM.setNumber(0);
    }

    @Log
    public void switchPipeilines(int pipeline){
        this.pipeline.setNumber(pipeline);
    }

    public void distance() {
        System.out.println("NAVX Value: " + Robot.drive.returnYaw()); // Print the NAVX angle
        System.out.println("TX: " + tx.getDouble(0)); // Print the target-limelight X angle
        targetAngle = (ty.getDouble(0) + theta); // Create the new angle (incline + Y-offset angle)
        aDistance = ((level1 - cameraHeight) / Math.tan(Math.toRadians(targetAngle)) + 6); // Return the distance
        xOffAngle = tx.getDouble(0);
        System.out.println("Distance to target: " + aDistance);
        System.out.println(' ');
        xOffset = (aDistance * Math.sin(Math.toRadians(tx.getDouble(0)))); // Return the x offset from being aligned
        System.out.println("The horizontal offset to be aligned with target: " + xOffset);
        System.out.println("-----------------------------------------------------"); // Spliter
    }

    public double getDistance() {
        targetAngle = (ty.getDouble(0) + theta); // Create the new angle (incline + Y-offset angle)
        aDistance = ((level1 - cameraHeight) / Math.tan(Math.toRadians(targetAngle)) + 6);

        return (aDistance);

    }

    public double getHorizontalOffset(){
        xOffAngle = tx.getDouble(0);

        xOffset = (this.getDistance() * Math.sin(Math.toRadians(tx.getDouble(0))));

        return ((xOffset));
    }

    public double testDistance(){

        aDistance=(Math.sin(90-Math.abs(getYOffset())*(level1-cameraHeight)))/Math.sin(Math.abs(getYOffset()));

        System.out.println("Real Distance " + aDistance);
        return aDistance;

    }
}

