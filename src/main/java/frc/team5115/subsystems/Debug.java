package frc.team5115.subsystems;

import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.robot.Robot;

public class Debug implements Runnable{

    DriverStation DS;
    PowerDistributionPanel PDP;

    NetworkTableEntry voltage;

    double[] current;
    double currentThreshold = 2.5;
    double motorThreshold = 4;

    //0 ok, 1 low, 2 critical
    int batteryState = 0;
    //0 checking, 1 verified
    int motorState = 0;

    public Debug(){
        DS = DriverStation.getInstance();
        PDP = new PowerDistributionPanel();
        current = new double[PortsJNI.getNumPDPChannels()];
        voltage = Robot.tab.add("Voltage", 0).getEntry();
        try{
            //Robot.dt.verify();
        } catch(NullPointerException e) {
            DS.reportError("One or more motor controllers are not being detected!", e.getStackTrace());
        }
    }

    public void PDPCheck(){
        for(int i = 0; i < PortsJNI.getNumPDPChannels(); i++){
            switch(i){
                case 0:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
                case 1:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
                case 15:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
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

    public void batteryCheck(){
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
