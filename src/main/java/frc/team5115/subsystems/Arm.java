package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import frc.team5115.commands.arm.ArmLooper;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    VictorSPX DART;

    AHRS navX;

    DigitalInput top;
    DigitalInput bottom;

    double max = 35;
    double level2 = 10;
    double level1 = -20;
    double min = -50;

    int level;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Manual Up",
                "Manual Down",
                "Stopped"));
        DART = new VictorSPX(2);
        navX = new AHRS(SerialPort.Port.kUSB);

        top = new DigitalInput(3);
        bottom = new DigitalInput(9);

        level = getCurrentPosition();
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
                if(level < ArmLooper.returnTarget() && level != -1){
                    setState("Moving Up");
                } else if(level == ArmLooper.returnTarget()){
                    setState("Stopped");
                } else {
                    move(-0.5);
                }
                break;
            case "Manual Up":
                move(0.5);
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

    private void checkPosition(){
        if(threshold(min)){
            level = 0;
        } else if(threshold(level1)){
            level = 1;
        } else if(threshold(level2)){
            level = 2;
        } else if(threshold(max)){
            level = 3;
        } else {
            level = -1;
        }
    }

    private boolean threshold(double val){
        return navX.getRoll() <= val + 2 && navX.getRoll() >= val - 2;
    }

    public int getCurrentPosition(){
        if(navX.getRoll() < min && navX.getRoll() <= level1){
            return 0;
        } else if(navX.getRoll() > min && navX.getRoll() <= level1){
            return 1;
        } else if(navX.getRoll() > level1 && navX.getRoll() <= level2){
            return 2;
        } else if(navX.getRoll() > level2 && navX.getRoll() <= max){
            return 3;
        }
        return level;
    }
}
