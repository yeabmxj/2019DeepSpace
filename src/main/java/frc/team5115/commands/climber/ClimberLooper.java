package frc.team5115.commands.climber;

import frc.team5115.commands.Looper;
import frc.team5115.robot.Robot;

public class ClimberLooper extends Looper {

    protected void initialize() {
        system = Robot.succ;
    }


}
