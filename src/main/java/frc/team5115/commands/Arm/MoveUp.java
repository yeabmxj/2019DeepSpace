package frc.team5115.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.commands.Wrist.WristLooper;

public class MoveUp extends Command {

    protected void initialize() { ArmLooper.arm.setState("Moving Up"); }

    protected void end(){ ArmLooper.arm.setState("Stopped");}

    protected boolean isFinished(){ return true; }

}
