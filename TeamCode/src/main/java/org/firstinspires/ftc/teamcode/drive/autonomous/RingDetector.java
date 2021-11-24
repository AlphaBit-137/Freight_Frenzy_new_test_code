package org.firstinspires.ftc.teamcode.drive.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp (name = "Duck/ElementsDetector", group = "Tests")
public class RingDetector extends LinearOpMode {

    OpenCvCamera webcam;

    // CONSTANTS

    final int X_LEFT = 120;
    final int X_RIGHT = 150;
    final int Y_UP = 150;
    final int Y_MIDDLE = 165;
    final int Y_DOWN = 172;



    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.openCameraDevice();

        // Camera Init
        webcam.openCameraDevice();

        // Loading pipeline
        RingPipeline_TEST visionPipeline = new RingPipeline_TEST();
        webcam.setPipeline(visionPipeline);

        // Start streaming the pipeline
        webcam.startStreaming(320,240,OpenCvCameraRotation.UPRIGHT);

        waitForStart();


        while (opModeIsActive())
        {
            // Get data from the pipeline and output it to the telemetry. This are the variables you are going to work with.
            telemetry.addData("Left1:",visionPipeline.Left1); // Will return 0 if there is 1 ring, otherwise 1
            telemetry.addData("Middle",visionPipeline.Middle); // Will return 0 if there is 4 rings, otherwise 1
            telemetry.addData("Middle",visionPipeline.Right2);
            telemetry.update();
        }
    }

    // Pipeline class
    class RingPipeline_TEST extends OpenCvPipeline {


        // Working Mat variables
        Mat YCrCb = new Mat(); // This will store the whole YCrCb channel
        Mat Cb = new Mat(); // This will store the Cb Channel (part from YCrCb)
        Mat tholdMat = new Mat(); // This will store the threshold

        // Drawing variables
        Scalar YELLOW = new Scalar(255, 255, 0); // RGB values for gray.
        Scalar GREEN = new Scalar(0, 255, 0); // RGB values for green.

        // Variables that will store the results of our pipeline
        public int Left1;
        public int Middle;
        public int Right2;
        // Space which we will annalise data
        public Point BigSquare1 = new Point(275, 215);
        public Point BigSquare2 = new Point(100, 117);

        public Point SmallSquare1 = new Point(275, 270);
        public Point SmallSquare2 = new Point(100, 215);

        @Override
        public Mat processFrame(Mat input) {

            // Img processing
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_BGR2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
            Imgproc.threshold(Cb, tholdMat, 91, 255, Imgproc.THRESH_BINARY_INV);

            // Drawing Points
            int BigSquarePointX = (int) ((BigSquare1.x + BigSquare2.x) / 2);
            int BigSquarePointY = (int) ((BigSquare1.y + SmallSquare1.y) / 2);

            int SmallSquarePointX = (int) ((SmallSquare1.x + SmallSquare2.x) / 2);
            int SmallSquarePointY = (int) ((SmallSquare1.y + SmallSquare2.y) / 2);

            double[] bigSquarePointValues = tholdMat.get(BigSquarePointY, BigSquarePointX);
            double[] smallSquarePointValues = tholdMat.get(SmallSquarePointY, SmallSquarePointX);

            //astea pt. acum sunt puse la squaruri la bungheala, nu am folosit inca gripu sa vedem unde le punem
            Left1 = (int) bigSquarePointValues[0];
            Middle = (int) smallSquarePointValues[0];
            Right2 =(int) bigSquarePointValues[0];


            //puse in comentariu just in case, cus we go erori cu ele
/*
            // Big Square
            Imgproc.rectangle(
                    input,
                    BigSquare1,
                    BigSquare2,
                    YELLOW,
                    1
            );

            // Small Square
            Imgproc.rectangle(
                    input,
                    SmallSquare1,
                    SmallSquare2,
                    YELLOW,
                    1
            );

            // Big Square Point
            Imgproc.circle(
                    input,
                    new Point(BigSquarePointX, BigSquarePointY),
                    2,
                    YELLOW,
                    1
            );

            // Small Square Point
            Imgproc.circle(
                    input,
                    new Point(SmallSquarePointX, SmallSquarePointY),
                    2,
                    YELLOW,
                    1
            );

            // Change colors if the pipeline detected something

            if (ring1 == 0 && ring4 == 0) {
                Imgproc.rectangle(
                        input,
                        BigSquare1,
                        BigSquare2,
                        GREEN,
                        1
                );
                Imgproc.circle(
                        input,
                        new Point(BigSquarePointX, BigSquarePointY),
                        2,
                        GREEN,
                        1
                );
            }
            if (ring1 == 0) {
                Imgproc.rectangle(
                        input,
                        SmallSquare1,
                        SmallSquare2,
                        GREEN,
                        1
                );
                Imgproc.circle(
                        input,
                        new Point(SmallSquarePointX, SmallSquarePointY),
                        2,
                        GREEN,
                        1
                );
            }
*/
            return input;
        }
    }


}