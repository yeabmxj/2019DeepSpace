package frc.team5115.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class FollowPath extends Command {

    protected void initialize(){
        Robot.limelight.scannerMode();
//        if(!Robot.drive.generatePath()){
//            this.cancel();
//        }
        Robot.limelight.cameraMode();
    }

    protected void execute(){
        DrivetrainLooper.system.setState("Following");
    }

    protected void end(){
        DrivetrainLooper.system.setState("Driving");
    }

    protected boolean isFinished(){
        return false;
    }

}
