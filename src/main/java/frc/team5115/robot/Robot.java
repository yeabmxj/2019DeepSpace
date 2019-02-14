package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.commands.startLoopers;
import frc.team5115.joysticks.InputManager;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();
        //start subsystem thread
        Scheduler.getInstance().add(new startLoopers());
        thread.start();
    }

    public void teleopInit() {
        im.checkControllers();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

}

