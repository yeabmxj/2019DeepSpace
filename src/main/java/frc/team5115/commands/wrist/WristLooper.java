package frc.team5115.commands.wrist;

import frc.team5115.commands.Looper;
import frc.team5115.robot.Robot;

public class WristLooper extends Looper {

    protected void initialize() {
        system = Robot.wrist;
    }

}
