package frc.team5115.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.team5115.PID;
import frc.team5115.joysticks.Controller;
import frc.team5115.joysticks.LogitechPad;
import frc.team5115.joysticks.LogitechPadAlt;
import frc.team5115.joysticks.Thrustmaster;
import frc.team5115.subsystems.Limelight;
import frc.team5115.statemachines.StateMachineBase;
import frc.team5115.subsystems.Drivetrain;

public class InputLoop extends StateMachineBase {

    public static Controller primary;
    public static Controller secondary;



    public static final int INPUT = 1;
    public static final int DEBOUNCE = 2;

    double time;

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
                if(secondary.scanPressed()){
                    Robot.dt.addTask("Search Target");
                    time = Timer.getFPGATimestamp();
                    setState(DEBOUNCE);
                }
                break;
            case DEBOUNCE:
                if(Timer.getFPGATimestamp() > time + 2){
                    setState(INPUT);
                }
                break;
        }
    }
}