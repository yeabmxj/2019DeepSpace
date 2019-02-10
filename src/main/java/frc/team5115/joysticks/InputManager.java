package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.Drivetrain.Stop;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;


public class InputManager {

    public static Controller primary;
    public static Controller secondary;

    int primaryPort = 0;
    int secondaryPort = -1;

    JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/Controllers.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkControllers();
    }

    public void findControllers(){
        for(int i = 0; i < 5; i++){
            if((!new Joystick(i).getName().equals("")) && (new Joystick(i).getButtonCount() > 0)){
                primaryPort = i;
                break;
            }
        }
        for(int i = primaryPort + 1; i < 5; i++){
            if((!new Joystick(i).getName().equals("") || !(new Joystick(primaryPort).getName().equals("MAYFLASH GameCube Controller Adapter"))) && (new Joystick(i).getButtonCount() > 0)){
                secondaryPort = i;
                break;
            }
        }
        System.out.println(primaryPort);
        System.out.println(secondaryPort);
    }

    public void checkControllers(){
        primary = null;
        secondary = null;
        findControllers();
        try {
            primary = new Controller(primaryPort, controllerData.getJSONObject(new Joystick(primaryPort).getName()));
            if(secondaryPort == -1){
                secondary = primary;
            } else {
                secondary = new Controller(secondaryPort, controllerData.getJSONObject(new Joystick(secondaryPort).getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        JoystickButton stop = new JoystickButton(secondary.stick, secondary.scanBind);
        stop.whenPressed(new Stop());
    }

}
