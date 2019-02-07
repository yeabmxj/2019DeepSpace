package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveUp extends Command {

    protected void initialize() {
        Robot.wr.setState("Up");
    }

    protected boolean isFinished(){
        return true;
    }

}
