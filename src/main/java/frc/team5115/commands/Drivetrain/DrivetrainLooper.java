package frc.team5115.commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class DrivetrainLooper extends Command {


    protected void execute() {
        System.out.println("executing");
        Robot.dt.update();
    }

    protected void interrupted(){
        Robot.dt.setState("Stopped");
    }

    protected boolean isFinished() {
        return false;
    }

}
