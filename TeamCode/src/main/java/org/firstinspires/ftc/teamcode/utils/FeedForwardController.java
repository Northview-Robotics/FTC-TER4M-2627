package org.firstinspires.ftc.teamcode.utils;

/**
 * A simple object-oriented feedforward controller
 *
 * <p>
 * This feedforward controller is based on the DC-Motor Feedforward equation provided by
 * the WPILib documentation [1]. It includes the kS (static friction), kV (velocity) and
 * kA (acceleration) terms. Any terms may be set to 0.0 to omit them. See Ctrl-Alt-FTC guide
 * for reference implementation (does not include kS term) [2].
 * </p>
 *
 * <h1>Tuning</h1>
 *
 * <ul>
 *     <li>Tune kS by increasing it until the motor just starts to spin</li>
 *     <li>Tune kV until velocity gets close to a certain setpoint</li>
 * </ul>
 * <p>
 *     [1] https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html
 *     <br>
 *     [2] https://www.ctrlaltftc.com/feedforward-control
 * </p>
 *
 * @author Joseph Umana
 */
public class FeedForwardController {
    /**
     * Static friction gain
     */
    private double kS = 0.0;
    /**
     * Velocity gain
     */
    private double kV = 0.0;
    /**
     * Acceleration gain
     */
    private double kA = 0.0;

    /**
     * Constructs a FeedForward Controller with given parameters
     *
     * @param kS Static friction gain
     * @param kV Velocity gain
     * @param kA Acceleration gain
     */
    public FeedForwardController(double kS, double kV, double kA) {
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
    }

    /**
     * Calculate FF output based on velocity and acceleration. Outputs 0 if disabled.
     *
     * @param velocitySetpoint     The target velocity
     * @param accelerationSetpoint The target acceleration
     * @return Raw output
     */
    public double getOutput(double velocitySetpoint, double accelerationSetpoint) {
        return kS * Math.signum(velocitySetpoint) +
                kV * velocitySetpoint +
                kA * accelerationSetpoint;
    }
}
