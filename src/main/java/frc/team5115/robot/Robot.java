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
    Map<Object, ArrayList<Object>> CANBus;


    public void robotInit() {
    dt = new Drivetrain();
    thread.start();
  }

  public void teleopInit(){
        dt.addTask("Drive");
  }

  public void teleopPeriodic() {
        System.out.println(CANBus.get(0));
        dt.update();
  }
  public void testInit(){
      try {
          readJsonFromUrl();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl() throws IOException, JSONException {
        try (InputStream is = new URL("http://172.22.11.2:1250/?action=getversion").openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            System.out.println("yay");
            return json;
        }
    }

    public Map<Object, ArrayList<Object>> returnCANBus(){
        try {
            JSONArray server = readJsonFromUrl().getJSONArray("DeviceArray");
            Map<Object, ArrayList<Object>> CANBus = new HashMap<>();
            for(int i = 0; i < 6/*This should be the number of expected device*/; i++){
                JSONObject current = server.getJSONObject(i);
                ArrayList<Object> metadata = new ArrayList<>();
                metadata.add(current.get("Model"));
                metadata.add(current.get("Name"));
                metadata.add(current.get("SoftStatus"));
                CANBus.put(current.get("UniqID"), metadata);
            }
            return CANBus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
