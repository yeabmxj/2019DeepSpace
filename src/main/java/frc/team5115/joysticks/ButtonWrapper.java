package frc.team5115.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class ButtonWrapper extends Button {

    private final GenericHID m_joystick;
    private final int m_bind;
    private final boolean m_comparator;


    public ButtonWrapper(GenericHID joystick, int bind){
        m_joystick = joystick;
        m_bind = bind;
        switch(m_bind){
            case 0:
            case 45:
            case 90:
            case 135:
            case 180:
            case 225:
            case 270:
            case 315:
                m_comparator = (m_joystick.getPOV() == m_bind);
                break;
            default:
                m_comparator = m_joystick.getRawButton(m_bind);
                break;
        }
    }

    @Override
    public boolean get() {
        return m_comparator;
    }

}
