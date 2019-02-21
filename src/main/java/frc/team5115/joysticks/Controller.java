package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.Debug;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    private Joystick stick;

    private int forwardAxis;
    private int turnAxis;

    private double throttle = 0.5;
    private String throttleMethod;
    private int throttleAxis;
    private int throttleIncreaseAxis;
    private int throttleDecreaseAxis;
    private int throttleIncrease;
    private int throttleDecrease;

    int scanBind;
    int killBind;

    Controller(int port, JSONObject data) {
        stick = new Joystick(port);

        try {
            forwardAxis = data.getInt("Forward");
            turnAxis = data.getInt("Turn");
            throttleMethod = data.getString("Throttle Method");
            switch(throttleMethod){
                case "Dedicated Axis":
                    throttleAxis = data.getInt("Throttle Axis");
                    break;
                case "Triggers":
                    throttleIncrease = data.getInt("Throttle Increase");
                    throttleDecrease = data.getInt("Throttle Decrease");
                    break;
                case "Analog Triggers":
                    throttleIncreaseAxis = data.getInt("Throttle Increase Axis");
                    throttleDecreaseAxis = data.getInt("Throttle Decrease Axis");
                    break;
            }
            scanBind = data.getInt("Scan Bind");
        } catch (JSONException e) {
            Debug.getInstance().reportWarning("Critical controller binds not detected, using defaults...", false);
            e.printStackTrace();
            forwardAxis = 0;
            turnAxis = 1;
            throttleMethod = "Triggers";
            throttleIncrease = 1;
            throttleDecrease = 2;
            scanBind = 1;
        }
        killBind = 2;
    }

    Controller(int port){
        stick = new Joystick(port);
        forwardAxis = 0;
        turnAxis = 1;
        throttleMethod = "Triggers";
        throttleIncrease = 1;
        throttleDecrease = 2;
        scanBind = 1;
        killBind = 2;
    }

    public Joystick returnInstance(){
        return stick;
    }

    public double processThrottle(){
        switch(throttleMethod){
            case "Dedicated Axis":
                throttle = (-stick.getRawAxis(throttleAxis) + 1) / 2;
                break;
            case "Triggers":
                if(stick.getRawButton(throttleIncrease)){
                    throttle += 0.005;
                } else if (stick.getRawButton(throttleDecrease)){
                    throttle -= 0.005;
                }
                break;
            case "Analog Triggers":
                throttle += 0.01 *(stick.getRawAxis(throttleIncreaseAxis) - stick.getRawAxis(throttleDecreaseAxis));
                break;
        }

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

    public double getLeft(){
        return deadband(-stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis));
    }

    public double getRight(){
        return deadband(stick.getRawAxis(forwardAxis) + stick.getRawAxis(turnAxis));
    }

    public double deadband(double val){
        if(val <= 0.075 && val >= -0.075){
            return 0;
        }

        return val;
    }

    public boolean debugRawButton(int button) { return stick.getRawButton(button); }


}
