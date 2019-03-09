package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.ArmCommandDown;
import frc.team5115.commands.ArmCommandUp;
import frc.team5115.commands.VacuumSucc;
import org.json.JSONException;
import org.json.JSONObject;
import frc.team5115.commands.MoveWrist;

import java.io.*;


public class InputManager {

    public Joystick joystick;

    private int forwardAxis;
    private int turnAxis;

    private double throttle = 0.5;
    private String throttleMethod;
    private int throttleAxis;
    private int throttleIncreaseAxis;
    private int throttleDecreaseAxis;
    private int throttleIncrease;
    private int throttleDecrease;

    private int moveUpBind;
    private int moveDownBind;
    private int succBind;
    private int moveLeftBind;
    private int moveRightBind;
    private int moveYBind;

    private JSONObject controllerData;

    public InputManager() {
        try {
            controllerData = Debug.readJSON(new FileInputStream("/home/lvuser/Controllers.json"));
        } catch (FileNotFoundException file){
            Debug.reportWarning("Controller file not found on roborio!");
        } catch (IOException io){
            Debug.reportWarning("Controller file corrupted!");
        } catch (JSONException json){
            Debug.reportWarning("Controller file not formatted properly!");
        }
    }

    public void findController() throws JSONException {
        joystick = new Joystick(0);
        JSONObject controller = controllerData.getJSONObject(joystick.getName());

        forwardAxis = controller.getInt("Forward");
        turnAxis = controller.getInt("Turn");
        throttleMethod = controller.getString("Throttle Method");
        switch(throttleMethod){
            case "Dedicated Axis":
                throttleAxis = controller.getInt("Throttle Axis");
                break;
            case "Triggers":
                throttleIncrease = controller.getInt("Throttle Increase");
                throttleDecrease = controller.getInt("Throttle Decrease");
                break;
            case "Analog Triggers":
                throttleIncreaseAxis = controller.getInt("Throttle Increase Axis");
                throttleDecreaseAxis = controller.getInt("Throttle Decrease Axis");
                break;
        }
        moveUpBind = controller.getInt("Move Up Bind");
        moveDownBind = controller.getInt("Move Down Bind");
        succBind = controller.getInt("Toggle Vacuum");
        moveLeftBind = controller.getInt("Move Left Bind");
        moveRightBind = controller.getInt("Move Right Bind");
        moveYBind = controller.getInt("Move Y Bind");
    }


    public void createBinds(){
        JoystickButton armCommandUp;
        JoystickButton armCommandDown;
        armCommandUp = new JoystickButton(joystick, 1);
        armCommandUp.whenPressed(new ArmCommandUp());
        armCommandDown = new JoystickButton(joystick, 2);
        armCommandDown.whenPressed(new ArmCommandDown());

        JoystickButton nine = new JoystickButton(joystick, 9);
        nine.toggleWhenPressed(new VacuumSucc());

    }

    public boolean debugRawButton(int button)
    {
        return joystick.getRawButton(button);
    }

      public boolean debugPOV(int comparator)
      {
          return joystick.getPOV()== comparator;
      }


}
