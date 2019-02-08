package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Debug;
import frc.team5115.commands.Drivetrain.Stop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    Joystick stick;
    int port;

    public int forwardAxis;
    public int turnAxis;

    double throttle;
    int throttleAxis;
    int throttleIncreaseAxis;
    int throttleDecreaseAxis;
    int throttleIncrease;
    int throttleDecrease;

    int scanBind;
    int killBind;

    Button stop;

    public Controller(int port, JSONObject data) {
        this.port = port;
        stick = new Joystick(this.port);

        try {
            forwardAxis = data.getInt("Forward");
            turnAxis = data.getInt("Turn");
            scanBind = data.getInt("scanBind");
        } catch (JSONException e) {
            DriverStation.getInstance().reportError("Critical values not detected!", false);
        }

        try{
            throttleAxis = data.getInt("Throttle Axis");
            throttleIncrease = data.getInt("Throttle Increase");
            throttleDecrease = data.getInt("Throttle Decrease");
            throttleIncreaseAxis = data.getInt("Throttle Increase Axis");
            throttleDecreaseAxis = data.getInt("Throttle Decrease Axis");
        } catch (JSONException e){
            DriverStation.getInstance().reportWarning("Some values (that aren't neccessarily required) aren't being detected for this specific controller", false);
        }

        stop = new JoystickButton(stick, scanBind);
        stop.whenPressed(new Stop());
    }

    public boolean controllerExists(){
        return stick.getButtonCount() > 0;
    }

    public double processThrottle(){
        if(stick.getRawButton(throttleIncrease)){
            throttle += 0.005;
        } else if (stick.getRawButton(throttleDecrease)){
            throttle -= 0.005;
        }

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }

        return throttle;
    }

    public double getLeft(){
        return -stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis);
    }

    public double getRight(){
        return stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis);
    }

    public boolean scanPressed(){
        return stick.getRawButton(scanBind);
    }

}
