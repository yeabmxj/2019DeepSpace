package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveUp extends Command {

    protected void initialize() {
        if(!Robot.arm.isManual()){
            ArmLooper.addLevel(1);
            //ArmLooper.setLevel(6);
            ArmLooper.system.setState("Moving Up");
        }
    }

    protected boolean isFinished(){ return true; }

}
