package frc.team5115;

import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Drivetrain;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Debug implements Runnable{

    DriverStation DS;
    PowerDistributionPanel PDP;

    JSONObject CANBus;

    NetworkTableEntry voltage;

    double[] current;
    double currentThreshold = 2.5;
    double motorThreshold = 4;

    //0 ok, 1 low, 2 critical
    int batteryState = 0;

    public Debug(){
        DS = DriverStation.getInstance();
        PDP = new PowerDistributionPanel();
        current = new double[PortsJNI.getNumPDPChannels()];
        voltage = Robot.tab.add("Voltage", 0).getEntry();
        try {
            CANBus = Drivetrain.readJsonFromUrl();
            CANBus.getInt("UniqID");
        } catch (Exception e) {
            e.printStackTrace();
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
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
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
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
            }
            current[i] = PDP.getCurrent(i);
        }
    }

    private void batteryCheck(){
        if(PDP.getVoltage() > 10 && batteryState != 0){
            batteryState = 0;
        } else if((PDP.getVoltage() < 10 && PDP.getVoltage() > 8) && batteryState != 1){
            DS.reportWarning("Battery Low!", false);
            batteryState = 1;
        } else if((PDP.getVoltage() < 7) && batteryState != 2){
            DS.reportError("Battery Critical!", false);
            batteryState = 2;
        }
        voltage.setDouble(PDP.getVoltage());
    }

    public void run(){
        while(true) {
            try {
                PDPCheck();
                batteryCheck();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                DS.reportWarning("Monitor interrupted! stopping thread", e.getStackTrace());
            }
        }
    }
}
