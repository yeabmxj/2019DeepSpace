package frc.team5115.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

public class DrivetrainLooper extends Command {

    public static Subsystem system;
    boolean kill = false;

    public static Subsystem returnSystem(){
        return system;
    }

    protected void execute(){system.update();}

    protected void end(){kill = true;}

    protected boolean isFinished() { return kill; }

    protected void initialize() {
        system = Robot.drive;
        system.setState("Driving");
    }
}
