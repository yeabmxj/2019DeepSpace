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
    double min = -50;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Stopped"));
        DART = new TalonSRX(0);
        navX = new AHRS(SerialPort.Port.kUSB);
    }
    

    public void update(){
        switch(state){
            case "Moving Up":
                if(getPosition() > ArmLooper.returnTarget()){
                    setState("Moving Down");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    move(0.5);
                }
                break;
            case "Moving Down":
                if(getPosition() < ArmLooper.returnTarget()){
                    setState("Moving Up");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    move(-0.5);
                }
                break;
            case "Stopped":
                move(0);
                break;
        }
    }

    public void move(double percent){
        DART.set(ControlMode.PercentOutput, percent);
    }

    public int getPosition(){
        System.out.println(navX.getRoll());
        if(navX.getRoll() < min && navX.getRoll() <= level1){
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
