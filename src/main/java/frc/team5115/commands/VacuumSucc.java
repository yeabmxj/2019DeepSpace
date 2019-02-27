package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;

public class VacuumSucc extends Command {
    protected boolean isFinished() {
        return false;
    }

    protected void initialize() {
        System.out.println("things have started");
        Robot.vacMachine.setState(Robot.vacMachine.SUCKING);
    }

    protected void end() {
        System.out.println("terminated");
        Robot.vacMachine.setState(Robot.vacMachine.STOP);
    }
}
