package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utils.FeedForwardController;
import org.firstinspires.ftc.teamcode.utils.SmartMotorController;

@TeleOp
public class MainTeleop extends OpMode {
    final static double ARM_DOWN_ANGLE = 1.0;
    final static double ARM_UP_ANGLE = 0.0;

    DcMotor m_frontLeftDrive;
    DcMotor m_frontRightDrive;
    DcMotor m_backLeftDrive;
    DcMotor m_backRightDrive;

    DcMotor m_flywheel;
    SmartMotorController m_flywheelController;

    Servo m_arm;
    IMU m_imu;


    Telemetry m_telemetry;

    @Override
    public void init() {
        // Telemetry Setup (FTC Dashboard)
        FtcDashboard dashboard = FtcDashboard.getInstance();
        m_telemetry = new MultipleTelemetry(m_telemetry, dashboard.getTelemetry());

        m_flywheelController =
                new SmartMotorController(
                        new FeedForwardController(0.0, 0.0, 0.0)
                );

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

        m_telemetry.update();
    }

    public void initHardware() {
        m_frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        m_frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        m_backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        m_backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        m_flywheel = hardwareMap.get(DcMotor.class, "flywheel");

        m_backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        m_backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        m_frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        m_flywheel.setDirection(DcMotorSimple.Direction.FORWARD);

        m_arm = hardwareMap.get(Servo.class, "arm");
        m_arm.setDirection(Servo.Direction.FORWARD);

        m_imu = hardwareMap.get(IMU.class, "imu");

        // TODO: Change me!
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);

        m_imu.initialize(new IMU.Parameters(orientationOnRobot));
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
