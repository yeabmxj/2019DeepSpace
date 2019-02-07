package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class WristLooper extends Command {

    protected void execute() {
        System.out.println("executing");
        Robot.wr.update();
    }

    protected void interrupted(){
        Robot.wr.setState("Neutral");
    }

    protected boolean isFinished() {
        return false;
    }

}
