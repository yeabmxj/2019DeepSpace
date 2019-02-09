package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class WristLooper extends Command {

    protected void execute() {
        Robot.wr.update();
    }

    protected void interrupted(){
        Robot.wr.setState("Stopped");
    }

    protected boolean isFinished() {
        return false;
    }

}
