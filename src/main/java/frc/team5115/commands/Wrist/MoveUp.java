package frc.team5115.commands.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveUp extends Command {

    protected void initialize() { WristLooper.wr.setState("Up"); }

    protected void end(){ WristLooper.wr.setState("Stopped");}

    protected boolean isFinished(){ return true; }

}
