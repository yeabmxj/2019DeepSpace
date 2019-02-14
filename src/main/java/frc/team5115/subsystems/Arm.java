package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import frc.team5115.commands.arm.ArmLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    TalonSRX DART;

    AHRS navX;

    double max = 35;
    double level2 = 10;
    double level1 = -20;
    double min = -54;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Stopped"));
        DART = new TalonSRX(5);
        navX = new AHRS(SerialPort.Port.kUSB);
        System.out.println("instantiated objects");
    }
    

    public void update(){
        //System.out.println("current position" + getPosition());
        //System.out.println("target position" + ArmLooper.returnTarget());
        //System.out.println(currentState());
        switch(state){
            case "Moving Up":
                if(getPosition() < ArmLooper.returnTarget()){
                    setState("Moving Down");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    DART.set(ControlMode.PercentOutput, 0.5);
                }
                System.out.println("moving up");
                break;
            case "Moving Down":
                if(getPosition() > ArmLooper.returnTarget()){
                    setState("Moving Up");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    DART.set(ControlMode.PercentOutput, -0.5);
                }
                System.out.println("moving down");
                break;
            case "Stopped":
                DART.set(ControlMode.PercentOutput, 0);
                System.out.println("neutral");
                break;
        }
    }

    public int getPosition(){
        if(navX.getRoll() <= min){
            min = navX.getRoll();
            return 0;
        } else if(navX.getRoll() > min && navX.getRoll() <= level1){
            return 1;
        } else if(navX.getRoll() > level1 && navX.getRoll() <= level2){
            return 2;
        } else if(navX.getRoll() > level2 && navX.getRoll() <= max){
            return 3;
        }
        return -1;
    }
}
