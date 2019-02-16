package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveY extends Command {

    protected void initialize() {
        Robot.wrist.getTimestamp();
        WristLooper.returnSystem().setState("Toggle Y");
    }

    protected boolean isFinished(){ return true; }

}
