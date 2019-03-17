package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import frc.team5115.Konstanten;
import frc.team5115.commands.arm.ArmLooper;
import frc.team5115.lib.control.SynchronousPIDF;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    private VictorSPX DART;

    private AHRS navX;

    private DigitalInput top;
    private DigitalInput bottom;

    private SynchronousPIDF loop;
    private double time;

    public boolean manual = false;//verifyGyro();

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Manual Up",
                "Manual Down",
                "Transition",
                "PID",
                "Stopped"));
        DART = new VictorSPX(Konstanten.DART_ID);
        navX = new AHRS(SerialPort.Port.kUSB);

        top = new DigitalInput(Konstanten.TOP_SWITCH);
        bottom = new DigitalInput(Konstanten.BOTTOM_SWITCH);

        loop = new SynchronousPIDF(1, 0 ,0, "arm", Konstanten.tab);
        loop.setInputRange(Konstanten.MIN_SCALED, Konstanten.MAX_SCALED);
        loop.setOutputRange(-1, 1);
    }

    public void update(){
        switch(state){
            case "Transition":
                loop.setPIDLive();
                loop.setSetpoint(returnTarget());
                time = Timer.getFPGATimestamp();
                setState("PID");
                break;
            case "PID":
                //move(loop.calculate(getCurrentPosition(), Timer.getFPGATimestamp() - time));
                System.out.println(getCurrentPosition() + " " + returnTarget());
                System.out.println(loop.calculate(getCurrentPosition(), Timer.getFPGATimestamp() - time));
                time = Timer.getFPGATimestamp();
                if(loop.onTarget(Konstanten.ARM_THRESHOLD)){
                    System.out.println("finished!");
                    setState("Stopped");
                }
                break;
            case "Moving Up":
                if(threshold(returnTarget())){
                  setState("Stopped");
                } else if(getCurrentPosition() > returnTarget()){
                    setState("Moving Down");
                } else {
                    System.out.println("moving up");
                    move(0.6);
                }
                break;
            case "Moving Down":
                if(threshold(returnTarget())){
                    setState("Stopped");
                } else if(getCurrentPosition() < returnTarget()){
                    setState("Moving Up");
                } else {
                    System.out.println("moving down");
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

    @Log
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
                if(ArmLooper.returnLastTarget() == 0){
                    target = Konstanten.LEVEL1BOTTOM;
                } else {
                    target = Konstanten.LEVEL1TOP;
                }
                break;
            case 2:
                target = Konstanten.LEVEL2;
                break;
            case 3:
                target = Konstanten.BALL_PICKUP;
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

    public void toggleManual(){
        manual = !manual;
    }

    public boolean isManual(){
        return manual;
    }
}
