package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveDown extends Command {

    protected void initialize() {
        if(!Robot.arm.isManual()){
            ArmLooper.addLevel(-1);
            ArmLooper.system.setState("Moving Down");
        }
    }

    protected boolean isFinished(){ return true; }

}
