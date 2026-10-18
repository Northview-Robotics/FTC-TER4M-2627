package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class SubsystemTest extends OpMode {
    static final double UP_ANGLE = 0.0;
    static final double DOWN_ANGLE = 1.0;
    Servo m_armServo;

    @Override
    public void init() {
        m_armServo = hardwareMap.get(Servo.class, "arm_servo");
    }

    @Override
    public void loop() {
        telemetry.addData("ServoPosition", m_armServo.getPosition());

        if (gamepad1.dpadDownWasPressed()) {
            m_armServo.setPosition(DOWN_ANGLE);
        } else if (gamepad1.dpadUpWasPressed()) {
            m_armServo.setPosition(UP_ANGLE);
        }

        telemetry.update();
    }
}
