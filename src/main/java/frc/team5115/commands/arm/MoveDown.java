package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveDown extends Command {

    protected void initialize() {
        ArmLooper.addLevel(-0.5);
        ArmLooper.system.setState("Moving Down");
    }

    protected boolean isFinished(){ return true; }

}
