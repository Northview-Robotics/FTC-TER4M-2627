package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utils.FeedForwardController;
import org.firstinspires.ftc.teamcode.utils.SmartMotorController;

@TeleOp
public class MainTeleop extends OpMode {
    final static double ARM_DOWN_ANGLE = 0.5;
    final static double ARM_UP_ANGLE = 0.0;
    final static double TRIGGER_THRESHOLD = 0.1;
    final static double FLYWHEEL_GEAR_RATIO = 1.0 / 1.0;
    final static double FLYWHEEL_TICKS_PER_REVOLUTION = FLYWHEEL_GEAR_RATIO * 28.0 * 4.0;

    DcMotor m_frontLeftDrive;
    DcMotor m_frontRightDrive;
    DcMotor m_backLeftDrive;
    DcMotor m_backRightDrive;

//    DcMotorEx m_flywheel;
    DcMotorEx m_intake;
//    SmartMotorController m_flywheelController;

    Servo m_arm;
    IMU m_imu;


    @Override
    public void init() {
        // Telemetry Setup (FTC Dashboard)
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

//        m_flywheelController =
//                new SmartMotorController(
//                        new FeedForwardController(100.0, 100.0, 0.0)
//                );

        initHardware();
    }

    @Override
    public void loop() {
        if (gamepad1.dpadUpWasPressed()) {
            m_arm.setPosition(ARM_UP_ANGLE);
        } else if (gamepad1.dpadDownWasPressed()) {
            m_arm.setPosition(ARM_DOWN_ANGLE);
        }

        if (gamepad1.yWasPressed()) {
            m_imu.resetYaw();
        }

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
            m_intake.setPower(1.0);
        }
        else if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            m_intake.setPower(-1.0);
        } else {
            m_intake.setPower(0.0);
        }

        driveFieldOriented(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);

        updateHardware();

        telemetry.update();
    }

    public void updateHardware() {
        //m_flywheel.setPower(m_flywheelController.getOutput());
//        m_flywheel.setPower(1.0);
//        telemetry.addData("FlywheelRPS", m_flywheel.getVelocity() / FLYWHEEL_TICKS_PER_REVOLUTION);
//        telemetry.addData("FlywheelPosition", m_flywheel.getCurrentPosition());
//        telemetry.addData("FlywheelPositionRotations", m_flywheel.getCurrentPosition() / FLYWHEEL_TICKS_PER_REVOLUTION);
//        telemetry.addData("FlywheelRPSSetpoint", m_flywheelController.getVelocitySetpoint());
    }

    public void initHardware() {
        m_frontLeftDrive = hardwareMap.get(DcMotor.class, "fl");
        m_frontRightDrive = hardwareMap.get(DcMotor.class, "fr");
        m_backLeftDrive = hardwareMap.get(DcMotor.class, "bl");
        m_backRightDrive = hardwareMap.get(DcMotor.class, "br");
//        m_flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        m_intake = hardwareMap.get(DcMotorEx.class, "intake");

        m_backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        m_frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
//        m_flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
        m_intake.setDirection(DcMotorSimple.Direction.REVERSE);

        m_arm = hardwareMap.get(Servo.class, "arm");
        m_arm.setDirection(Servo.Direction.FORWARD);
        m_arm.setPosition(ARM_UP_ANGLE);

        m_imu = hardwareMap.get(IMU.class, "imu");

        // TODO: Change me!
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.UP;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);

        m_imu.initialize(new IMU.Parameters(orientationOnRobot));

//        m_flywheelController.setVelocitySetpoint(10.0);
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

    public void driveFieldOriented(double forward, double right, double rotate) {
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);
        theta = AngleUnit.normalizeRadians(theta - m_imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);
        drive(newForward, newRight, rotate);
    }
}
