package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class SubsystemTest extends OpMode {
    final static double FLYWHEEL_TICKS_PER_REVOLUTION = 28;
    DcMotorEx m_flywheel;
    DcMotorEx m_intake;


    @Override
    public void init() {
        m_flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        m_intake = hardwareMap.get(DcMotorEx.class, "intake");

        m_flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
        m_intake.setDirection(DcMotorSimple.Direction.REVERSE);

        m_flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m_intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        m_flywheel.setPower(1.0);
        m_intake.setPower(1.0);
    }

    @Override
    public void loop() {
    }
}
