package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveY extends Command {

    protected void initialize() {
        WristLooper.system.setState("Toggle Y");
    }

    protected void interrupted(){ WristLooper.system.setState("Stopped"); }

    protected boolean isFinished(){ return false; }

}
