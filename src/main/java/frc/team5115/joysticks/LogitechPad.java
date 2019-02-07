package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.commands.Drivetrain.Stop;

public class LogitechPad extends Controller{

    public LogitechPad(int port){
        this.port = port;
        stick = new Joystick(this.port);

        forwardAxis = 1;
        turnAxis = 2;
        throttleIncrease = 8;
        throttleDecrease = 7;
        scanBind = 1;

        stop = new JoystickButton(stick, scanBind);
        stop.whenPressed(new Stop());
    }


}
