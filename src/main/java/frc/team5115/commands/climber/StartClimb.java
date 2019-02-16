package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class StartClimb extends Command {

    protected void initialize() {
        Robot.climb.getTimestamp();
        ClimberLooper.returnSystem().setState("Moving Up");
    }

    protected boolean isFinished(){ return true; }


}
