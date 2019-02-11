package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveDown extends Command {

    protected void initialize() {
        WristLooper.wr.setState("Down");
    }

    protected void end(){ WristLooper.wr.setState("Stopped");}

    protected boolean isFinished(){
        return true;
    }

}
