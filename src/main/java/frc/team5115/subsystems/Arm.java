package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import frc.team5115.commands.arm.ArmLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    TalonSRX DART;

    AHRS navX;

    DigitalInput top;
    DigitalInput bottom;

    double max = 35;
    double level2 = 10;
    double level1 = -20;
    double min = -50;

    int level = 0;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Stopped"));
        DART = new TalonSRX(0);
        navX = new AHRS(SerialPort.Port.kUSB);
    }

    public boolean gyroStatus(){
        return navX.isConnected();
    }
    

    public void update(){
        checkPosition();
        switch(state){
            case "Moving Up":
                if(level > ArmLooper.returnTarget()){
                    setState("Moving Down");
                } else if(level == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    move(0.5);
                }
                break;
            case "Moving Down":
                if(level < ArmLooper.returnTarget()){
                    setState("Moving Up");
                } else if(level == ArmLooper.returnTarget()){
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

    private void move(double percent){
        DART.set(ControlMode.PercentOutput, percent);
    }

    private void checkPosition(){
        System.out.println(navX.getRoll());
        if(threshold(min)){
            level = 0;
        } else if(threshold(level1)){
            level = 1;
        } else if(threshold(level2)){
            level = 2;
        } else if(threshold(max)){
            level = 3;
        }
    }

    private boolean threshold(double val){
        return navX.getRoll() <= val + 2 && navX.getRoll() >= val - 2;
    }

    public int getCurrentPosition(){
        return level;
    }
}
