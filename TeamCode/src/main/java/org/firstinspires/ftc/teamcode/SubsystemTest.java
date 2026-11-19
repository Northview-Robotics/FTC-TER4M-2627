package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class SubsystemTest extends OpMode {
    static final double UP_ANGLE = 0.0;
    static final double DOWN_ANGLE = 1.0;

    final static double FLYWHEEL_GEAR_RATIO = 1.0 / 1.0;
    final static double FLYWHEEL_TICKS_PER_REVOLUTION = 28;

    Servo m_armServo;
    DcMotorEx m_flywheel;

    @Override
    public void init() {
        m_armServo = hardwareMap.get(Servo.class, "arm");
        m_flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");

        m_flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("MotorSpeed", m_flywheel.getVelocity() / FLYWHEEL_TICKS_PER_REVOLUTION);
        telemetry.addData("MotorPosition", m_flywheel.getCurrentPosition() / FLYWHEEL_TICKS_PER_REVOLUTION);
        m_flywheel.setPower(1.0);

        telemetry.update();
    }
}
