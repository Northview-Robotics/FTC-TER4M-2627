package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ArmStick extends LinearOpMode {
    Servo m_arm;

    @Override
    public void runOpMode() {
        m_arm = hardwareMap.get(Servo.class, "arm");
        m_arm.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            m_arm.setPosition(gamepad1.left_stick_y);

            telemetry.addData("armpos", gamepad1.left_stick_y);
            telemetry.update();
        }
    }


}
