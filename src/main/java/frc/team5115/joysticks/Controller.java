package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    Joystick stick;
    int port;

    public int forwardAxis;
    public int turnAxis;

    double throttle = 0.5;
    String throttleMethod;
    int throttleAxis;
    int throttleIncreaseAxis;
    int throttleDecreaseAxis;
    int throttleIncrease;
    int throttleDecrease;

    int scanBind;
    int killBind;

    public Controller(int port, JSONObject data) {
        this.port = port;
        stick = new Joystick(this.port);

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
            DriverStation.getInstance().reportError("Critical controller binds not detected, using defaults...", false);
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


}
