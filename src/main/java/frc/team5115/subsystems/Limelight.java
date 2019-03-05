package frc.team5115.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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

    public boolean isValid(){
        return tv.getNumber(1).intValue() == 1;
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
    public void cameraMode(){
        LED.setNumber(1);
        CAM.setNumber(1);
    }
    public void scannerMode(){
        LED.setNumber(3);
        CAM.setNumber(0);
    }
    public void switchPipeilines(int pipeline){
        this.pipeline.setNumber(pipeline);
    }
}
