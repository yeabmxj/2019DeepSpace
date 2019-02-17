package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveY extends Command {

    protected void initialize() {
        WristLooper.returnSystem().getTimestamp();
        WristLooper.returnSystem().setState("Toggle Y");
    }

    protected boolean isFinished(){ return true; }

}
