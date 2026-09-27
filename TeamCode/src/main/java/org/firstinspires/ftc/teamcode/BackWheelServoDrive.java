package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Back Wheel Servo Drive", group = "Drive")
public class BackWheelServoDrive extends LinearOpMode {

    private CRServo leftBack;
    private CRServo rightBack;
    private static final double MAX_SPEED = 0.55;   // 0.0 to 1.0

    @Override
    public void runOpMode() {
        leftBack  = hardwareMap.get(CRServo.class, "leftBack");
        rightBack = hardwareMap.get(CRServo.class, "rightBack");

        // Swap these if the robot spins in place when you push forward
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addLine("Ready - press PLAY");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;

            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (max > 1.0) {
                leftPower  /= max;
                rightPower /= max;
            }

            // Right bumper = slow mode (half of MAX_SPEED)
            double speed = gamepad1.right_bumper ? MAX_SPEED * 0.5 : MAX_SPEED;
            leftPower  *= speed;
            rightPower *= speed;

            leftBack.setPower(Range.clip(leftPower, -1.0, 1.0));
            rightBack.setPower(Range.clip(rightPower, -1.0, 1.0));

            telemetry.addData("Left back", "%.2f", leftPower);
            telemetry.addData("Right back", "%.2f", rightPower);
            telemetry.update();
        }

        leftBack.setPower(0);
        rightBack.setPower(0);
    }
}