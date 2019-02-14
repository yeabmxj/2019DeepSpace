package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.commands.arm.ArmLooper;

public class startLoopers extends CommandGroup {

    public startLoopers(){
        addParallel(new ArmLooper());
    }

}