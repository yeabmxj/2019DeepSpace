package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class Thrustmaster extends Controller {

    public Thrustmaster(int port){
        this.port = port;
        stick = new Joystick(this.port);

        forwardAxis = 1;
        turnAxis = 0;
        throttleAxis = 3;
        scanBind = 4;
    }

    @Override
    public double processThrottle(){
        throttle = stick.getRawAxis(throttleAxis);
        return (-throttle + 1) / 2;
    }

}
