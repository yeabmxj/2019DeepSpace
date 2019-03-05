package frc.team5115.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

public class DrivetrainLooper extends Command {

    public static Subsystem system;
    private boolean kill = false;

    protected void initialize() {
        system = Robot.drive;
        system.setState("Driving");
    }

    protected void execute(){system.update();}

    protected boolean isFinished() { return kill; }

}
