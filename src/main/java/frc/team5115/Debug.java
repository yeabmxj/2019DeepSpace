package frc.team5115;

import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team5115.robot.Robot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Debug implements Runnable{
    PowerDistributionPanel PDP;
    Map<Object, ArrayList<Object>> CANBus;

    NetworkTableEntry voltage;

    double[] current;
    double currentThreshold = 2.5;
    double motorThreshold = 4;

    //0 ok, 1 low, 2 critical
    batteryState battery = batteryState.OK;

    enum batteryState{
        OK,
        LOW,
        CRITICAL
    }


    public Debug(){
        PDP = new PowerDistributionPanel();
        current = new double[PortsJNI.getNumPDPChannels()];
        voltage = Robot.tab.add("Voltage", 0).getEntry();
        try {
            CANBus = returnCANBus();
            for(int i = 0; i < CANBus.size(); i++){
                ArrayList<Object> device = CANBus.get(i);
                for(Object value: device){
                    System.out.println(value);
                }

            }
        } catch (Exception e){
            e.printStackTrace();
            reportWarning("Something went wrong while trying to get phoenix diagnostics!", e);
        }
    }

    public static void reportWarning(String display, Exception e){
        DriverStation.getInstance().reportWarning(display, e.getStackTrace());
    }

    public static void reportWarning(String display){
        DriverStation.getInstance().reportWarning(display, false);
    }

    public static void reportError(String display, Exception e){
        DriverStation.getInstance().reportError(display, e.getStackTrace());
    }

    public static void reportError(String display){
        DriverStation.getInstance().reportError(display, false);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJSON(InputStream stream) throws IOException, JSONException {
        try (InputStream is = stream) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    public static Map<Object, ArrayList<Object>> returnCANBus(){
        try {
            JSONArray server = readJSON(new URL("http://172.22.11.2:1250/?action=getdevices").openStream()).getJSONArray("DeviceArray");
            Map<Object, ArrayList<Object>> CANBus = new HashMap<>();
            for(int i = 0; i < 5/*This should be the number of expected device*/; i++){
                JSONObject current = server.getJSONObject(i);
                ArrayList<Object> metadata = new ArrayList<>();
                metadata.add(current.get("Name"));
                metadata.add(current.get("Model"));
                metadata.add(current.get("SoftStatus"));
                CANBus.put(current.get("UniqID"), metadata);
            }
            return CANBus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void PDPCheck(){
        for(int i = 0; i < PortsJNI.getNumPDPChannels(); i++){
            switch(i){
                //ports with motor plugged in
                case 0:
                case 1:
                case 14:
                case 15:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!");
                    }
                    break;
                    //ports returning bad voltages
                case 2:
                case 3:
                case 12:
                case 13:
                    break;
                default:
                    if(current[i] >  PDP.getCurrent(i) + currentThreshold || current[i] < PDP.getCurrent(i) - currentThreshold){
                        reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!");
                    }
                    break;
            }
            current[i] = PDP.getCurrent(i);
        }
    }

    private void batteryCheck(){
        if(PDP.getVoltage() > 10 && battery != batteryState.OK){
            battery = batteryState.OK;
        } else if((PDP.getVoltage() < 10 && PDP.getVoltage() > 8) && battery != batteryState.LOW){
            reportWarning("Battery Low!");
            battery = batteryState.LOW;
        } else if((PDP.getVoltage() < 7) && battery != batteryState.CRITICAL){
            reportError("Battery Critical!");
            battery = batteryState.CRITICAL;
        }
        voltage.setDouble(PDP.getVoltage());
    }

    public batteryState getBattery() {
        return battery;
    }

    public void run(){
        while(true) {
            try {
                PDPCheck();
                batteryCheck();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                reportWarning("Monitor interrupted! stopping thread", e);
                break;
            }
        }
    }
}
