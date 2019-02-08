package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class InputManager {

    public static Controller primary;
    public static Controller secondary;

    JSONObject controllerData;

    public InputManager() throws JSONException, IOException, ParseException {
        controllerData = new JSONObject(new JSONParser().parse(new FileReader("Controllers.JSON")));
        primary = new Controller(0, controllerData);
        if(new Joystick(1).getButtonCount() > 0){
            secondary = new Controller(1, controllerData);
        } else {
            secondary = primary;
        }
    }

}
