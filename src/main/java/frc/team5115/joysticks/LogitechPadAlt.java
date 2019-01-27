package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechPadAlt extends Controller{

    public LogitechPadAlt(int port){
        this.port = port;
        stick = new Joystick(port);

        forwardAxis = 1;
        turnAxis = 4;
        throttleIncreaseAxis = 3;
        throttleDecreaseAxis = 2;
        scanBind = 1;
    }

    @Override
    public double processThrottle(){
        throttle += 0.01 *(stick.getRawAxis(throttleIncreaseAxis) - stick.getRawAxis(throttleDecreaseAxis));
        if(throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

}
