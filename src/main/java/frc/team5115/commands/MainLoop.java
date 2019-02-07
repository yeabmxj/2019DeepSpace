package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.commands.Drivetrain.DrivetrainLooper;

public class MainLoop extends CommandGroup {
    public MainLoop(){
        addParallel(new DrivetrainLooper());
    }
}