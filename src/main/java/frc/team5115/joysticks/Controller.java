package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.commands.Drivetrain.Stop;

public class Controller {

    Joystick stick;
    int port;

    public int forwardAxis;
    public int turnAxis;

    double throttle;
    int throttleAxis;
    int throttleIncreaseAxis;
    int throttleDecreaseAxis;
    int throttleIncrease;
    int throttleDecrease;

    int scanBind;
    int killBind;

    Button stop;

    public Controller(){ this(0); }

    public Controller(int port){
        this.port = port;
        stick = new Joystick(this.port);


        forwardAxis = 0;
        turnAxis = 1;
        throttleIncrease = 1;
        throttleDecrease = 2;
        scanBind = 1;

        stop = new JoystickButton(stick, scanBind);
        stop.whenPressed(new Stop());
    }

    public boolean controllerExists(){
        return stick.getButtonCount() > 0;
    }

    public double processThrottle(){
        if(stick.getRawButton(throttleIncrease)){
            throttle += 0.005;
        } else if (stick.getRawButton(throttleDecrease)){
            throttle -= 0.005;
        }

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }

        return throttle;
    }

    public double getLeft(){
        return -stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis);
    }

    public double getRight(){
        return stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis);
    }

    public boolean scanPressed(){
        return stick.getRawButton(scanBind);
    }

}
