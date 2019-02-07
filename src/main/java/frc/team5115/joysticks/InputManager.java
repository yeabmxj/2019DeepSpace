package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.joysticks.*;
import frc.team5115.robot.Robot;


public class InputManager {

    public static Controller primary;
    public static Controller secondary;

    public InputManager(){
        primary = controllerCheck(0);
        if(new Controller(1).controllerExists()){
            secondary = controllerCheck(1);
        } else {
            secondary = primary;
        }
    }

    public void checkControllers(){
        primary = controllerCheck(0);
        if(new Controller(1).controllerExists()){
            secondary = controllerCheck(1);
        } else {
            secondary = primary;
        }
    }


    public Controller controllerCheck(int port){
        Controller selected = null;
        switch(new Joystick(port).getName()){
            case "T.16000M":
                selected = new Thrustmaster(port);
                break;
            case "Logitech RumblePad 2 USB":
                selected = new LogitechPad(port);
                break;
            case "Controller (Gamepad F310)":
                selected = new LogitechPadAlt(port);
                break;
            case "Controller (Xbox One For Windows)":
                selected = new XboxController(port);
                break;
            case "MAYFLASH GameCube Controller Adapter":
                selected = new GameCube(port);
                break;
            default:
                selected = new Controller(port);
                break;

        }
        return selected;
    }

}
