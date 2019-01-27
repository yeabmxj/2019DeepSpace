package frc.team5115.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.PID;
import frc.team5115.joysticks.Controller;
import frc.team5115.joysticks.LogitechPad;
import frc.team5115.joysticks.LogitechPadAlt;
import frc.team5115.joysticks.Thrustmaster;
import frc.team5115.subsystems.Limelight;
import frc.team5115.statemachines.StateMachineBase;
import frc.team5115.subsystems.Drivetrain;

public class InputLoop extends StateMachineBase {

    Controller primary;
    Controller secondary;

    Limelight limelight;
    Drivetrain drivetrain;

    public static final int INPUT = 1;
    public static final int VALID = 2;
    public static final int AUTO = 3;

    PID forwardControl;
    PID turnControl;

    double forwardVal;
    double turnVal;

    public InputLoop(){
        drivetrain = new Drivetrain();
        limelight = new Limelight();

    }

    public void controllerCheck(){
        switch(new Joystick(0).getName()){
            case "T.16000M":
                primary = new Thrustmaster(0);
                System.out.println("primary controller 1");
                break;
            case "Logitech RumblePad 2 USB":
                primary = new LogitechPad(0);
                System.out.println("primary controller 2");
                break;
            case "Controller (Gamepad F310)":
                primary = new LogitechPadAlt(0);
                System.out.println("primary controller 3");
                break;
            default:
                    primary = new Controller(0);
                System.out.println("primary controller 4");

                break;
        }
        switch(new Joystick(1).getName()){
            case "T.16000M":
                secondary = new Thrustmaster(1);
                System.out.println("secondary controller 1");
                break;
            case "Logitech RumblePad 2 USB":
                secondary = new LogitechPad(1);
                System.out.println("secondary controller 2");
                break;
            case "Controller (Gamepad F310)":
                secondary = new LogitechPadAlt(1);
                System.out.println("secondary controller 3");
                break;
            default:
                if(new Controller(1).controllerExists()){
                    secondary = new Controller(1);
                    System.out.println("secondary controller 4");

                } else {
                    secondary = primary;
                    System.out.println("secondary controller 5");

                }
        }
    }

    public void resetControllers(){
        primary = null;
        secondary = null;
    }

    public void update(){
        switch(state){
            case INPUT:
                drivetrain.drive(primary.getForward(), primary.getTurn(), primary.processThrottle());
                if(secondary.scanPressed()){
                    System.out.println("wow");
                    setState(AUTO);
                }
                break;
            case VALID:
                if(limelight.isValid()){
                    limelight.scannerMode();
                    forwardControl = new PID("forward");
                    turnControl = new PID("turn");
                    setState(AUTO);
                } else {
                    setState(INPUT);
                }
                break;
            case AUTO:
                forwardVal = forwardControl.getPID(0, limelight.getYOffset(), drivetrain.averageSpeed());
                turnVal = turnControl.getPID(0, limelight.getXOffset(), drivetrain.getTurnVelocity());
                drivetrain.drive(forwardVal, turnVal, 1);
                if((forwardControl.isFinished(0.03, 0.5) && turnControl.isFinished(0.03, 0.5) || !primary.scanPressed())){
                    forwardControl = null;
                    turnControl = null;
                    limelight.cameraMode();
                    setState(INPUT);
                }
                break;
        }
    }
}