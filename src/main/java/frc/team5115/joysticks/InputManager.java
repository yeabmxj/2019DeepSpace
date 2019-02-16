package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.arm.MoveDown;
import frc.team5115.commands.arm.MoveUp;
import frc.team5115.commands.arm.Stupidd;
import org.json.JSONObject;

import java.io.*;


public class InputManager {

    public static Controller primary;
    public static Controller secondary;

    Joystick joy;

    int primaryPort = 0;
    int secondaryPort = -1;

    JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/Controllers.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            JoystickButton test = new JoystickButton(primary.returnInstance(), 4);
            test.whenPressed(new MoveUp());

            JoystickButton test2 = new JoystickButton(primary.returnInstance(), 2);
            test2.whenPressed(new MoveDown());

            POVButton test3 = new POVButton(primary.returnInstance(), 0);
            test3.toggleWhenPressed(new Stupidd());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
