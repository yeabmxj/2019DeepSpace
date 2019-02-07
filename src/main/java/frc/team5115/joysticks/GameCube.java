package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class GameCube extends Controller{

    public GameCube(int port){
        this.port = port;
        stick = new Joystick(this.port);

        forwardAxis = 1;
        turnAxis = 0;
        throttleIncrease = 6;
        throttleDecrease = 5;
        scanBind = 8;
    }

}
