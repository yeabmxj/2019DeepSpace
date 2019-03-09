package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import frc.team5115.Konstanten;
import frc.team5115.commands.arm.ArmLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    VictorSPX DART;

    AHRS navX;

    DigitalInput top;
    DigitalInput bottom;



    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Manual Up",
                "Manual Down",
                "Stopped"));
        DART = new VictorSPX(Konstanten.DART_ID);
        navX = new AHRS(SerialPort.Port.kUSB);

        top = new DigitalInput(Konstanten.TOP_SWITCH);
        bottom = new DigitalInput(Konstanten.BOTTOM_SWITCH);
    }

    public void update(){
//        System.out.println(getCurrentPosition());
//        System.out.println(ArmLooper.returnTarget() + " " + returnTarget());
        switch(state){
            case "Moving Up":
                if(threshold(returnTarget())){
                  setState("Stopped");
                } else if(getCurrentPosition() > returnTarget()){
                    setState("Moving Down");
                } else {
                    //System.out.println("moving up");
                    move(0.6);
                }
                break;
            case "Moving Down":
                if(threshold(returnTarget())){
                    setState("Stopped");
                } else if(getCurrentPosition() < returnTarget()){
                    setState("Moving Up");
                } else {
                    //System.out.println("moving down");
                    move(-0.5);
                }
                break;
            case "Manual Up":
                move(0.6);
                break;
            case "Manual Down":
                move(-0.5);
                break;
            case "Stopped":
                move(0);
                break;
        }
    }

    public boolean verifyGyro(){
        return navX.isConnected();
    }

    private void move(double percent){
        double temp = percent;
        DigitalInput controller = Math.signum(percent) == 1 ? top : bottom;
        if(!controller.get()){
            temp = 0;
        }
        DART.set(ControlMode.PercentOutput, temp);
    }

    private boolean threshold(double val){
        return getCurrentPosition() <= val + Konstanten.ARM_THRESHOLD && getCurrentPosition() >= val - Konstanten.ARM_THRESHOLD;
    }

    public double getCurrentPosition(){
        return (Konstanten.SLOPE * navX.getRoll()) + Konstanten.Y_INTERCEPT;
    }

    public double returnTarget(){
        double target = 0;
        switch(ArmLooper.returnTarget()){
            case 0:
                target = Konstanten.MIN_SCALED;
                break;
            case 1:
                target = Konstanten.LEVEL1;
                break;
            case 2:
                target = Konstanten.LEVEL2;
                break;
            case 3:
                target = Konstanten.BALLPICKUP;
                break;
            case 4:
                target = Konstanten.LEVEL3;
                break;
            case 5:
                target = Konstanten.LEVEL4;
                break;
            case 6:
                target = Konstanten.LEVEL5;
                break;
            case 7:
                target = Konstanten.LEVEL6;
                break;
        }
        return target;
    }
}
