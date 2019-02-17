package frc.team5115.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.commands.startLoopers;
import frc.team5115.joysticks.InputManager;
import frc.team5115.subsystems.Arm;
import frc.team5115.subsystems.Climber;
import frc.team5115.subsystems.Vacuum;
import frc.team5115.subsystems.Wrist;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    public static Arm arm;
    public static Vacuum succ;
    public static Climber climb;
    public static Wrist wrist;

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();

        arm = new Arm();
        succ = new Vacuum();
        climb = new Climber();
        wrist = new Wrist();

        //start subsystem threads
        Scheduler.getInstance().add(new startLoopers());
        thread.start();
    }

    public void robotPeriodic(){
        if(RobotState.isEnabled()){
            Scheduler.getInstance().run();
        }
    }
}

