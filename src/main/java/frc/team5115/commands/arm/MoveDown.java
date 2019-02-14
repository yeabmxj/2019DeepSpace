package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveDown extends Command {

    protected void initialize() {
        ArmLooper.addLevel(-1);
        ArmLooper.returnSystem().setState("Moving Down");
    }

    protected void end(){ ArmLooper.system.setState("Stopped");}

    protected boolean isFinished(){ return true; }

}
