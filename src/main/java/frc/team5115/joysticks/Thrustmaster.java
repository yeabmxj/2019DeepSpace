package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.commands.Drivetrain.Stop;

public class Thrustmaster extends Controller {

    public Thrustmaster(int port){
        this.port = port;
        stick = new Joystick(this.port);

        forwardAxis = 1;
        turnAxis = 0;
        throttleAxis = 3;
        scanBind = 4;

        stop = new JoystickButton(stick, scanBind);
        stop.whenPressed(new Stop());
    }

    @Override
    public double processThrottle(){
        throttle = stick.getRawAxis(throttleAxis);
        return (-throttle + 1) / 2;
    }

}
