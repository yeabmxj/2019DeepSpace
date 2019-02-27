package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;

public class MoveRight extends Command {

    protected void initialize() {
        WristLooper.system.setState("Move Right");
    }

    protected void end(){
        WristLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return false; }

}
