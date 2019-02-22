package frc.team5115.commands.succ;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

public class SuccLooper extends Command {

    public static Subsystem system;
    boolean kill = false;

    protected void execute(){system.update();}

    protected boolean isFinished() { return kill; }

    protected void initialize() {
        system = Robot.succ;
    }

}
