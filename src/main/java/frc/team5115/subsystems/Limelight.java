package frc.team5115.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5115.Konstanten;
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
    public void switchPipelines(int pipeline){
        this.pipeline.setNumber(pipeline);
    }

    public double getDistance() {
        return ((Konstanten.TARGET_HEIGHT - Konstanten.CAMERA_HEIGHT) / Math.tan(Math.toRadians(getYOffset() + Konstanten.CAMERA_ANGLE)) + 6);
    }

    public double getHorizontalOffset(){
        return getDistance() * Math.sin(Math.toRadians(getXOffset()));
    }
}

