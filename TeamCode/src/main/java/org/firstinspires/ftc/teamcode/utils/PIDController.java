package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * A simple object-oriented PID controller
 *
 * <p>
 * Feedback controller with Proportional, Integral and Derivative terms. Takes inspiration from
 * Team #22377 Ctrl-Alt-FTC guide[1], but object-oriented.
 * </p>
 *
 * <p>
 *     [1] https://www.ctrlaltftc.com/the-pid-controller
 * </p>
 *
 * @author Joseph Umana
 */
public class PIDController {
    /**
     * Proportional gain
     */
    private double kP;
    /**
     * Integral gain
     */
    private double kI;
    /**
     * Derivative gain
     */
    private double kD;

    private ElapsedTime m_timer = new ElapsedTime();
    private double m_lastError = 0.0;
    private double m_integralSum = 0.0;

    /**
     * Constructs a FeedForward Controller with given parameters
     *
     * @param kP Proportional gain
     * @param kI Integral gain
     * @param kD Derivative gain
     */
    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Calculates the error
     *
     * @param setpoint Target velocity
     * @param feedback Actual velocity
     * @return the difference of setpoint and feedback
     */
    private double calculateError(double setpoint, double feedback) {
        return setpoint - feedback;

    }

    /**
     * Calculate PID output based on velocity and acceleration
     *
     * @param setpoint The target velocity
     * @param feedback Actual velocity
     * @return Raw output
     */
    public double getOutput(double setpoint, double feedback) {
        double error = calculateError(setpoint, feedback);

        double derivative = (error - m_lastError) / m_timer.seconds();

        m_integralSum += error * m_timer.seconds();

        m_lastError = error;

        return kP * error + kI * m_integralSum + kD * derivative;
    }

}
