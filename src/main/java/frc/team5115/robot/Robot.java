package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.subsystems.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

  }
}
