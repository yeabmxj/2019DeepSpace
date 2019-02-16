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
import frc.team5115.subsystems.Arm;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    public static Arm arm;

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();
// big yoshi <(^_^)/
        arm = new Arm();

        //start subsystem threads
        Scheduler.getInstance().add(new startLoopers());
        thread.start();
    }

    public void teleopInit() { // big yoshi
        im.checkControllers();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
//        if(im.primary.returnInstance().getRawButton(4)){
//            System.out.println("woo");
//            arm.move(0.5);
//        } else if(im.primary.returnInstance().getRawButton(2)){
//            arm.move(-0.5);
//        } else {
//            System.out.println("test");
//            arm.move(0);
//        }
        System.out.println(arm.getPosition());
    }

}

