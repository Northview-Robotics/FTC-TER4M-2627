package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class BunsAuto extends LinearOpMode {
    final static double ARM_UP_ANGLE = 0.0;

    DcMotor m_frontLeftDrive;
    DcMotor m_frontRightDrive;
    DcMotor m_backLeftDrive;
    DcMotor m_backRightDrive;
    Servo m_arm;

    @Override
    public void runOpMode() {
        m_frontLeftDrive = hardwareMap.get(DcMotor.class, "fl");
        m_frontRightDrive = hardwareMap.get(DcMotor.class, "fr");
        m_backLeftDrive = hardwareMap.get(DcMotor.class, "bl");
        m_backRightDrive = hardwareMap.get(DcMotor.class, "br");

        m_arm = hardwareMap.get(Servo.class, "arm");

        m_backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        m_frontRightDrive.setDirection(DcMotor.Direction.FORWARD);

        m_arm.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        m_arm.setPosition(ARM_UP_ANGLE);
        sleep(1000);
        drive(-1,0,0);
        sleep(700);
        drive(0,0,0);
    }

    public void drive(double forward, double right, double rotate) {
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        // TODO: Change me!
        m_frontLeftDrive.setPower(frontLeftPower);
        m_frontRightDrive.setPower(frontRightPower);
        m_backLeftDrive.setPower(backLeftPower);
        m_backRightDrive.setPower(backRightPower);
    }
}