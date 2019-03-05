package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveUp extends Command {

    protected void initialize() {
        ArmLooper.addLevel(0.5);
        ArmLooper.system.setState("Moving Up");
    }

    protected boolean isFinished(){ return true; }

}
