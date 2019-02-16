package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.subsystems.Subsystem;

public class Looper extends Command {

    public static Subsystem system;
    boolean kill = false;

    public static Subsystem returnSystem(){
        return system;
    }

    protected void execute(){system.update();}

    protected void interrupted(){kill = true;}

    protected boolean isFinished() { return kill; }
}
