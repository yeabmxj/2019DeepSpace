package frc.team5115;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.team5115.robot.Robot;

public class PID {

    /**
     * What is PID?
     * PID stands for Proportional-Integral-Derivative
     * It is a type of feedback loop that takes in an error value and tries to compensate for the error
     * Proportional control changes the motor input as a function of some fraction of the error in speed
     * Integral control changes the motor input as a function of some fraction of the error in distance travelled (or distance left to travel)
     * Derivative control changes the rate of change of the motor input as a function of some fraction of the error in the motor's acceleration
     */

    NetworkTableEntry kp;
    NetworkTableEntry ki;
    NetworkTableEntry kd;
    double maxOutput = 1;

    double error;
    double errorAccum = 0;
    double dError;
    double output;

    /**
     * Create a PID loop with a certain maximum output
     * @param loopName, name of the loop on shuffleboard
     */
    public PID(String loopName) {
        kp = Robot.tab.add("P" + loopName, 0).getEntry();
        ki = Robot.tab.add("I" + loopName, 0).getEntry();
        kd = Robot.tab.add("D" + loopName, 0).getEntry();
    }

    /**
     * Create a PID loop. Maximum output defaults to +/- 1, with the assumption that this will be used as a % of vBus for a motor
     * @param p
     * @param i
     * @param d
     */
    /**
     * Runs the PID loop. Must be run every Constants.DELAY seconds. dReading should be in reading units per second
     * @param setpoint
     * @param reading
     * @param dReading
     * @return output of PID controller
     */
    public double getPID(double setpoint, double reading, double dReading) {
        error = setpoint - reading;
        dError = -dReading;		// result of taking the derivative of the equation above with respect to time

        output = kp.getDouble(0) * error + ki.getDouble(0) * errorAccum + kd.getDouble(0) * dError;

        // Do not integrate if the output exceeds max to avoid intergral windup. See youtu.be/fusr9eTceEo
        if (Math.abs(output) <= maxOutput) {
            errorAccum += error * 0.05;
        }

        // Do not return a value greater than the maximum, but make sure it's the right sign
        if (Math.abs(output) > maxOutput) {
            output = maxOutput * Math.signum(output);
        }

        return output;
    }


    /**
     * Returns true if PID loop has settled. Takes tolerance in reading units and dErrorTolerance in reading units per second
     * @param tolerance
     * @param dErrorTolerance
     * @return true if PID loop has settled to within tolerances, false otherwise
     */
    public boolean isFinished(double tolerance, double dErrorTolerance) {
        return (Math.abs(error) < tolerance && Math.abs(dError) < dErrorTolerance);
    }

    public double getError() {
        return error;
    }
}
