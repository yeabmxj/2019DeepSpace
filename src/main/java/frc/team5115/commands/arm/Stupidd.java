package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class Stupidd extends Command {

    private boolean flipflop = true;

    protected void initialize() {
        flipflop = !flipflop;
        ArmLooper.system.setState("Moving Up");
    }

    protected void end(){
        ArmLooper.system.setState("Stopped");
    }

    protected boolean isFinished(){ return false; }

}
