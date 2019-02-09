package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.commands.Drivetrain.Stop;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;


public class InputManager {

    public static Controller primary;

    JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = readJSONFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkControllers();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJSONFile() throws IOException, JSONException {
        try (InputStream is = new FileInputStream("/home/lvuser/Controllers.json")) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    public void checkControllers(){
        try {
            primary = new Controller(0, controllerData.getJSONObject(new Joystick(0).getName()));
        } catch (Exception e){
            e.printStackTrace();
        }


        JoystickButton stop = new JoystickButton(primary.stick, primary.scanBind);
        stop.whenPressed(new Stop());
    }

}
