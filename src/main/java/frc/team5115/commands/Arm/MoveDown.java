package frc.team5115.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveDown extends Command {

    protected void initialize() {
        ArmLooper.addLevel(-1);
        ArmLooper.arm.setState("Moving Up");
    }

    protected void end(){ ArmLooper.arm.setState("Stopped");}

    protected boolean isFinished(){ return true; }

}
