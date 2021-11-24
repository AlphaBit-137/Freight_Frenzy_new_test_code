package org.firstinspires.ftc.teamcode.drive.motors;

import com.qualcomm.robotcore.hardware.configuration.DistributorInfo;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.ExpansionHubPIDFPositionParams;
import com.qualcomm.robotcore.hardware.configuration.annotations.ExpansionHubPIDFVelocityParams;
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType;

import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

@MotorType(ticksPerRev=28, gearing=1, maxRPM=6000, orientation=Rotation.CCW)
@DeviceProperties(xmlTag="goBILDA5202SeriesMotor_6000RPM", name="GoBILDA 5202 series 6000RPM", builtIn = false)
@DistributorInfo(distributor="goBILDA_distributor", model="goBILDA-5202-6000PM", url="https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-3-7-1-ratio-1620-rpm-3-3-5v-encoder/")
@ExpansionHubPIDFVelocityParams(P=2.0, I=0.5, F=11.1)
@ExpansionHubPIDFPositionParams(P=5.0)
public interface GoBILDA5205Series_6000RPM {}
