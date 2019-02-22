package frc.team5115.commands.succ;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleSucc extends Command {

    protected void initialize() {
        SuccLooper.system.setState("Succ");
    }

    protected void end(){
        SuccLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return false; }

}
