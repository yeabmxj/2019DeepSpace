package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.subsystems.*;

public class Robot extends TimedRobot {

    public static Drivetrain dt;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    Thread thread = new Thread(new Debug());

  public void robotInit() {
    dt = new Drivetrain();
    thread.start();
  }

  public void teleopInit(){
      dt.addTask("Drive");
  }

  public void teleopPeriodic() {
      dt.update();
  }
  public void testInit(){
      //H 70-80
      //S 155-185
      //V 194-223

      //75-85
      //155-155
      //194-240

      //Final
      //H50-100
      //S117-255
      //V171-255
  }

}
