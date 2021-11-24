/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.drive.opmode;

import android.transition.Slide;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    /* Public OpMode members. */

    public DcMotorEx lift = null;
    public DcMotorEx slide = null;
    public static double LIFT_POWER = 0.5;
    public static int INIT_POZ = 0;
    public static int LEVEL_POZ = 618; //-5000
    int poz;



    public LiftModes RobotLift = LiftModes.INIT;
    public LiftModes RobotSlide = LiftModes.INIT;

    public enum LiftModes {
        INIT,
        LEVEL,
        SCORE,
        FREE,
    }

    public Lift() {

    }

    /* local OpMode members. */
    HardwareMap hwMap = null;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // Define and Initialize Motors
        lift = hwMap.get(DcMotorEx.class, "Arm");
        slide = hwMap.get(DcMotorEx.class,"Slider");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        lift.setVelocityPIDFCoefficients(18.79, 4.79,2.879, 8.79);
        lift.setPositionPIDFCoefficients(5.0);
        slide.setVelocityPIDFCoefficients(18.79, 4.79,2.879, 8.79);
        slide.setPositionPIDFCoefficients(5.0);



    }

    public void update(int scorePosition, double freePower) {
        switch (RobotLift){
            case LEVEL:{
                slide.setTargetPosition(-1500);
                slide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                slide.setPower(LIFT_POWER);
                poz = slide.getCurrentPosition();
                if(poz <= -1000 && poz >= -1900) {
                    lift.setTargetPosition(LEVEL_POZ);
                    lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    lift.setPower(LIFT_POWER);
                }

                break;
            }
            case INIT:{
                lift.setTargetPosition(INIT_POZ);
                lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                lift.setPower(LIFT_POWER);
                poz = lift.getCurrentPosition();
                if(poz >= -20 && poz <= 20) {
                    slide.setTargetPosition(INIT_POZ);
                    slide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    slide.setPower(LIFT_POWER);
                }
                break;
            }
            case SCORE:{
                lift.setTargetPosition(scorePosition);
                lift.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                lift.setPower(LIFT_POWER);
                break;
            }
            case FREE:{
                lift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                lift.setPower(freePower);
                break;

            }

        }

    }

    public void switchToINIT(){
        RobotLift = LiftModes.INIT;
    }
    public void switchToLEVEL(){
        RobotLift = LiftModes.LEVEL;
    }
    public void switchToSCORE(){ RobotLift = LiftModes.SCORE; }
    public void switchToFREE(){ RobotLift = LiftModes.FREE; }

    public int getLiftEncoder(){
        return lift.getCurrentPosition();
    }

    public boolean isINIT(){
        if(RobotLift == LiftModes.INIT){
            return true;
        }else{
            return false;
        }
    }

    public boolean isLEVEL(){
        if(RobotLift == LiftModes.LEVEL){
            return true;
        }else{
            return false;
        }
    }

    public boolean isSCORE(){
        if(RobotLift == LiftModes.SCORE){
            return true;
        }else{
            return false;
        }
    }

    public boolean isFREE(){
        if(RobotLift == LiftModes.FREE){
            return true;
        }else{
            return false;
        }
    }


    
    
    
    

}

