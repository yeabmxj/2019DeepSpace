package frc.team5115.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.joysticks.InputManager;
import frc.team5115.statemachines.ArmStateMachine;
import frc.team5115.statemachines.VacuumStateMachine;
import frc.team5115.subsystems.Arm;
import frc.team5115.subsystems.Vacuum;
import org.json.JSONException;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("main");

    public static Arm armSubsystem;
    public static ArmStateMachine armdomination;

    public static Vacuum vacSubsystem;
    public static VacuumStateMachine vacMachine;

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();

        thread.start();

        armSubsystem = new Arm();
        armdomination = new ArmStateMachine();

        vacSubsystem = new Vacuum();
        vacMachine = new VacuumStateMachine();
    }

    public void teleopInit() {
        try {
            im.findController();
            im.createBinds();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void robotPeriodic() {
        if (RobotState.isEnabled()) {
            Scheduler.getInstance().run();
        }
    }
}
