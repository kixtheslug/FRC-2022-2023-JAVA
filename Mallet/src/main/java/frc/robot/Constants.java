// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double SPEED = 0.25;
    public static final double kCountsPerRevolution = 4096.0;
    public static final double kWheelDiameterInch = 6; // 70 mm
    public static final double tSpeed = 0.20;

    public static final double encoders_550Motors = 42;

    public static final double encoder_cube = 20;
    public static final double encoder_cone = 30;

    public static final double maxPivot = encoders_550Motors/180;
    public static final double maxExtension = 2*encoders_550Motors; //Num revolutions
    public static final double maxClaw = 3*encoders_550Motors;

    public static final double minVolt = 0.5;
    public static final double maxVolt = 5.0;

    public static final double minPivot = 0;
    public static final double minExtension = 0;
    public static final double minClaw = 0;

    public static final double pivotStartPos = encoders_550Motors/90;
    
}
