package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class ArmCommandUp extends Command {
    protected boolean isFinished(){
        return true;
    }
    protected void initialize(){
        System.out.println("arm up");
        Robot.armdomination.target += 1;
        if(Robot.armdomination.target > 3){
            Robot.armdomination.target = 3;
        }
        if(Robot.armdomination.target < 0) {
            Robot.armdomination.target = 0;
        }
        Robot.armdomination.setState(Robot.armdomination.MOVING_UP);
    }

}
