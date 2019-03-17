package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class MoveUp extends Command {

    protected void initialize() {
        if(!Robot.arm.isManual()){
            System.out.println("executed");
            ArmLooper.addLevel(1);
            ArmLooper.system.setState("Transition");
        }
    }

    protected boolean isFinished(){ return true; }

}
