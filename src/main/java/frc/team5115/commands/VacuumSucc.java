package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class VacuumSucc extends Command {
    protected boolean isFinished() {
        return false;
    }

    protected void initialize() {
        Robot.vacMachine.setState(Robot.vacMachine.SUCKING);
    }

    protected void end() {
        Robot.vacMachine.getTimeStamp();
        Robot.vacMachine.setState(Robot.vacMachine.STOP);
    }
}
