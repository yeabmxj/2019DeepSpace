package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.commands.Drivetrain.DrivetrainLooper;
import frc.team5115.commands.Vacuum.VacuumLooper;
import frc.team5115.commands.Wrist.WristLooper;

public class MainLoop extends CommandGroup {
    public MainLoop(){
        addParallel(new DrivetrainLooper());
        addParallel(new WristLooper());
        addParallel(new VacuumLooper());
    }
}