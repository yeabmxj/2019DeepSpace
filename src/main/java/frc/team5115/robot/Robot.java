package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.commands.MainLoop;
import frc.team5115.joysticks.InputManager;
import frc.team5115.subsystems.*;

public class Robot extends TimedRobot {

    public static Drivetrain dt;
    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    TalonSRX test;


    Thread thread = new Thread(new Debug());

    public void robotInit() {
        dt = new Drivetrain();
        im = new InputManager();
        thread.start();
  }

  public void teleopInit(){
        dt.setState("Driving");
        im.checkControllers();
      //start subsystem thread

    }

  public void teleopPeriodic() {
    dt.update();
  }
  public void testInit(){
    test = new TalonSRX(3);
  }

  public void testPeriodic(){
        if (test.getSensorCollection().isFwdLimitSwitchClosed() || test.getSensorCollection().isRevLimitSwitchClosed()){
          test.set(ControlMode.PercentOutput, 0.5);
        } else {
            test.set(ControlMode.PercentOutput, 0);
        }
        System.out.println("forward" + test.getSensorCollection().isFwdLimitSwitchClosed());
      System.out.println("backwards" + test.getSensorCollection().isRevLimitSwitchClosed());


  }
}
