package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SerialPort;
import frc.team5115.commands.Arm.ArmLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    TalonSRX DART;

    AHRS navX;

    double max = 30;
    double level2 = 10;
    double level1 = -10;
    double min = -30;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Stopped"));
        DART = new TalonSRX(5);
        navX = new AHRS(SerialPort.Port.kUSB);
    }

    public void update(){
        switch(state){
            case "Moving Up":
                if(getPosition() < ArmLooper.returnTarget()){
                    setState("Moving Down");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    DART.set(ControlMode.PercentOutput, 0.5);
                }
                break;
            case "Moving Down":
                if(getPosition() > ArmLooper.returnTarget()){
                    setState("Moving Up");
                } else if(getPosition() == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    DART.set(ControlMode.PercentOutput, -0.5);
                }
                break;
            case "Stopped":
                DART.set(ControlMode.PercentOutput, 0);
                break;
        }
    }

    public int getPosition(){
        if(navX.getPitch() <= min){
            min = navX.getPitch();
            return 0;
        } else if(navX.getPitch() > min && navX.getPitch() <= level1){
            return 1;
        } else if(navX.getPitch() > level1 && navX.getPitch() <= level2){
            return 2;
        } else if(navX.getPitch() > level2 && navX.getPitch() <= max){
            return 3;
        }
        return -1;
    }
}
