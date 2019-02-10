package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveLeft extends Command {

    protected void initialize() {
        WristLooper.wr.setState("Left");
    }

    protected boolean isFinished(){
        return true;
    }

}
