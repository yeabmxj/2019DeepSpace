package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveY extends Command {

    protected void initialize() {
        WristLooper.system.getTimestamp();
        WristLooper.system.setState("Toggle Y");
    }

    protected boolean isFinished(){ return true; }

}
