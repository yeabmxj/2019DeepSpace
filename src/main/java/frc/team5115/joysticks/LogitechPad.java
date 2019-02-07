package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechPad extends Controller{

    public LogitechPad(int port){
        this.port = port;
        stick = new Joystick(this.port);

        forwardAxis = 1;
        turnAxis = 2;
        throttleIncrease = 8;
        throttleDecrease = 7;
        scanBind = 1;
    }


}
