package frc.team5115.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.joysticks.Controller;
import frc.team5115.joysticks.InputManager;
import frc.team5115.statemachines.ArmStateMachine;
import frc.team5115.subsystems.Arm;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("main");

    public static Controller joy;

    public static ArmStateMachine armdomination;
    public static Arm arm;

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();

        thread.start();

        arm = new Arm();
        armdomination = new ArmStateMachine();


    }

    public void teleopInit() {
        im.findControllers();
    }

    public void robotPeriodic() {
        if (RobotState.isEnabled()) {
            Scheduler.getInstance().run();
        }
    }
}
