package frc.team5115.commands.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Drivetrain;

public class DrivetrainLooper extends Command {

    public static Drivetrain dt;

    public DrivetrainLooper(){
        dt = new Drivetrain();
        dt.setState("Driving");
    }

    protected void execute() {
        dt.update();
    }

    protected void interrupted(){
        dt.setState("Stopped");
    }

    protected boolean isFinished() {
        return false;
    }

}
