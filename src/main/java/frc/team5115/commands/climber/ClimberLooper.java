package frc.team5115.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Subsystem;

public class ClimberLooper extends Command {

    public static Subsystem system;
    private boolean kill = false;

    protected void initialize() {
        system = Robot.climb;
    }

    protected void execute(){system.update();}

    protected boolean isFinished() { return kill; }

}
