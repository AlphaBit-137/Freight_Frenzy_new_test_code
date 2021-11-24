package org.firstinspires.ftc.teamcode.drive.opmode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.Set;

@TeleOp(name = "Legacy_opmode_UltimateGoal", group = "Linear Opmode")
public class Legacy_opmode_UltimateGoal extends LinearOpMode {

    //Daclararea motoarelor
    public DcMotor BackLeftMotor = null;
    public DcMotor FrontRightMotor = null;
    public DcMotor FrontLeftMotor = null;
    public DcMotor BackRightMotor = null;
    public DcMotor SliderMotor = null;
    public DcMotor Arm = null;
    private ElapsedTime runtime = new ElapsedTime();


    //Declararea claselor


    Modes SpeedModes = Modes.FAST;

    enum Modes {
        FAST,
        SLOW,
        MEDIUM,
    }

    @Override
    public void runOpMode() throws InterruptedException {


        BackLeftMotor = hardwareMap.get(DcMotor.class, "Back_Left");
        FrontRightMotor = hardwareMap.get(DcMotor.class, "Front_Right");
        FrontLeftMotor = hardwareMap.get(DcMotor.class, "Front_Left");
        BackRightMotor = hardwareMap.get(DcMotor.class, "Back_Right");
        SliderMotor = hardwareMap.get(DcMotor.class, "Slider");
        Arm = hardwareMap.get(DcMotor.class,"Arm");

        BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        BackRightMotor.setDirection(DcMotor.Direction.FORWARD);
        SliderMotor.setDirection(DcMotor.Direction.FORWARD);
        Arm.setDirection(DcMotor.Direction.FORWARD);

        BackLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        FrontLeftMotor.setPower(0);
        BackRightMotor.setPower(0);
        SliderMotor.setPower(0);
        Arm.setPower(0);

        BackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SliderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        runtime.reset();
        waitForStart();

        // Atata timp cat OpMode-ul este activ va rula pana la oprire urmatorul cod

        while (opModeIsActive()) {

            //Initiallizam variabilele
            double Front, Turn, Sum, Diff, Side, Drive1, Drive2, Drive3, Drive4;

            //Primirea datelor de la joystick-uri
            Front = gamepad1.left_stick_y;
            Turn = gamepad1.right_stick_x;
            Side = gamepad1.left_stick_x;

            if(Front != 0){
                FrontLeftMotor.setPower(Front);
                FrontRightMotor.setPower(Front);
                BackLeftMotor.setPower(Front);
                BackRightMotor.setPower(Front);


            }
            else if(Turn != 0){

                FrontLeftMotor.setPower(Turn);
                FrontRightMotor.setPower(-Turn);
                BackLeftMotor.setPower(Turn);
                BackRightMotor.setPower(-Turn);
            }
            else if(Side != 0){
                FrontLeftMotor.setPower(Side);
                FrontRightMotor.setPower(-Side);
                BackLeftMotor.setPower(-Side);
                BackRightMotor.setPower(Side);


            }
            if(gamepad1.left_bumper && !gamepad1.right_bumper){

                SliderMotor.setPower(0.6);

            }
            else if(gamepad1.right_bumper && !gamepad1.left_bumper){
                SliderMotor.setPower(-0.6);
            }
            else{
                SliderMotor.setPower(0);
            }
            if(gamepad1.left_trigger!=0){
                Arm.setPower(0.5);
            }else if(gamepad1.left_trigger==0 && gamepad1.right_trigger == 0){
                Arm.setPower(0);
            }else if(gamepad1.right_trigger!=0){
                Arm.setPower(-0.5);
            }
//Actionarea in functie de semnalul primit
            if (gamepad1.dpad_up)
                FrontRightMotor.setPower(1.0);
            else
                FrontRightMotor.setPower(0);

            if (gamepad1.dpad_right)
                FrontLeftMotor.setPower(1.0);
            else
                FrontLeftMotor.setPower(0);

            if (gamepad1.dpad_down)
                BackLeftMotor.setPower(1.0);
            else
                BackLeftMotor.setPower(0);

            if (gamepad1.dpad_left)
                BackRightMotor.setPower(1.0);
            else
                BackRightMotor.setPower(0);

            //Calcularea puterii redate motoarelor
            Sum = Range.clip(Front + Side, -1.0, 1.0);
            Diff = Range.clip(Front - Side, -1.0, 1.0);

            Drive1 = Range.clip(Sum - 2 * Turn, -1.0, 1.0);
            Drive2 = Range.clip(Sum + 2 * Turn, -1.0, 1.0);
            Drive3 = Range.clip(Diff - 2 * Turn, -1.0, 1.0);
            Drive4 = Range.clip(Diff + 2 * Turn, -1.0, 1.0);

           // MS(Drive1, Drive2, Drive3, Drive4);

            if (gamepad1.a)
                SpeedModes = Modes.MEDIUM;
            if (gamepad1.b)
                SpeedModes = Modes.SLOW;
            if (gamepad1.y)
                SpeedModes = Modes.FAST;


            telemetry.addData("Informatie:", "Atentie! Programul a fost sting.");



        }

    }

    /*void MS(double x1, double x2, double x3, double x4) {
        BackLeftMotor.setPower(x1);
        FrontRightMotor.setPower(x2);
        FrontLeftMotor.setPower(x3);
        BackRightMotor.setPower(x4);
    }*/
}