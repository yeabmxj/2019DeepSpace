package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class POVButton extends Button {

    private final GenericHID m_joystick;
    private final int m_POVDegrees;


    public POVButton(GenericHID joystick, int degrees){
        m_joystick = joystick;
        m_POVDegrees = degrees;
    }

    @Override
    public boolean get() {
        return m_joystick.getPOV() == m_POVDegrees;
    }

}
