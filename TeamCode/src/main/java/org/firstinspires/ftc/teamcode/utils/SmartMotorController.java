package org.firstinspires.ftc.teamcode.utils;

public class SmartMotorController {
    private FeedForwardController m_ffController;

    // TODO: Add PID controller

    private double m_velocitySetpoint = 0.0;
    private double m_accelerationSetpoint = 0.0;
    private boolean m_isEnabled = false;

    public SmartMotorController(FeedForwardController ffController) {
        m_ffController = ffController;
    }

    public void setVelocitySetpoint(double m_velocitySetpoint) {
        this.m_velocitySetpoint = m_velocitySetpoint;
    }

    public void setAccelerationSetpoint(double m_accelerationSetpoint) {
        this.m_accelerationSetpoint = m_accelerationSetpoint;
    }

    public double getVelocitySetpoint() {
        return m_velocitySetpoint;
    }

    public double getAccelerationSetpoint() {
        return m_accelerationSetpoint;
    }

    public void setEnabled(boolean enabled) {
        m_isEnabled = enabled;
    }

    public boolean getEnabled() {
        return m_isEnabled;
    }

    public double getOutput() {
        return m_ffController.getOutput(m_velocitySetpoint, m_accelerationSetpoint) * (m_isEnabled ? 1.0 : 0.0);
    }
}
