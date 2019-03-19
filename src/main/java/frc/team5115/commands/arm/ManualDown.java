package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class ManualDown extends Command {

    protected void initialize(){
        if(Robot.arm.isManual()){
            ArmLooper.system.setState("Manual Down");
        }
    }

    protected void interrupted(){
        if(Robot.arm.isManual()){
            ArmLooper.system.setState("Stopped");
        }
    }

    protected boolean isFinished(){return false;}

}
