package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class Stupidd extends Command {

    private boolean flipflop = true;

    protected void initialize() {
        //flipflop = !flipflop;
        System.out.println("wow");
    }

    protected void end(){
        System.out.println("incredible");
    }

    protected boolean isFinished(){ return false; }

}
