package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;

public class Stupidd extends Command {

    protected void initialize() {
        System.out.println("hello");
    }

    protected void end(){ System.out.println("goodbye");}

    protected boolean isFinished(){ return true; }

}
