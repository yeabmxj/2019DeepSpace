package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import frc.team5115.Konstanten;
import frc.team5115.commands.arm.ArmLooper;
import frc.team5115.lib.control.SynchronousPIDF;
import frc.team5115.lib.drivers.VictorWrapper;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Arm extends Subsystem{

    private VictorWrapper DART;

    private AHRS navX;

    private SynchronousPIDF loop;
    private double timeDifference;

    private boolean manual = true;

    public Arm(){
        dictionary = new ArrayList<>(Arrays.asList("Moving Up",
                "Moving Down",
                "Manual Up",
                "Manual Down",
                "Transition",
                "PID",
                "Stopped"));
        DART = new VictorWrapper(Konstanten.DART_ID, Konstanten.TOP_SWITCH, Konstanten.BOTTOM_SWITCH);
        navX = new AHRS(SerialPort.Port.kUSB);

        loop = new SynchronousPIDF(1, 0 ,0, "arm", Konstanten.tab);
        loop.setInputRange(Konstanten.MIN_SCALED, Konstanten.MAX_SCALED);
        loop.setOutputRange(-1, 1);

        manual = verifyGyro();
    }

    public void update(){
        switch(state){
            case "Transition":
                //loop.setPIDLive();
                loop.setPID(1.2, 0.1, 0, 0.15);
                loop.setSetpoint(returnTarget());
                timeDifference = Timer.getFPGATimestamp();
                setState("PID");
                break;
            case "PID":
                move(loop.calculate(getCurrentPosition(), Timer.getFPGATimestamp() - timeDifference));
                System.out.println(getCurrentPosition() + " " + returnTarget());
                System.out.println(loop.calculate(getCurrentPosition(), Timer.getFPGATimestamp() - timeDifference));
                timeDifference = Timer.getFPGATimestamp();
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
                System.out.println("upping");
                move(0.6);
                break;
            case "Manual Down":
                System.out.println("downing");
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
        DART.set(ControlMode.PercentOutput, percent);
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
