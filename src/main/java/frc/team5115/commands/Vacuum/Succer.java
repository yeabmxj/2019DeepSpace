package frc.team5115.commands.Vacuum;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Debug;
import frc.team5115.commands.Wrist.WristLooper;

public class Succer extends Command {

    protected void initialize() { VacuumLooper.succ.setState("Succ"); }

    protected void end(){ WristLooper.wr.setState("Stopped");}

    protected boolean isFinished(){
        return true;
    }

}
