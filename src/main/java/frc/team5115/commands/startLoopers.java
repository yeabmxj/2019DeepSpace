package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.commands.arm.ArmLooper;
import frc.team5115.commands.climber.ClimberLooper;
import frc.team5115.commands.succ.SuccLooper;
import frc.team5115.commands.wrist.WristLooper;

public class startLoopers extends CommandGroup {

    public startLoopers(){
        addParallel(new ArmLooper());
        addParallel(new SuccLooper());
        //addParallel(new ClimberLooper());
        addParallel(new WristLooper());
    }

}