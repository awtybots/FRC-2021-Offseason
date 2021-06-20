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

  public static final class Drive {
    public static final int leftMotor1ID = 13;
    public static final int leftMotor2ID = 14;
    public static final int rightMotor1ID = 11;
    public static final int rightMotor2ID = 12;
  }

  public static final class Intake {
    public static final double speed = 0.6;
    public static final int motorID = 5;
    public static final int pistonDown = 5;
    public static final int pistonUp = 4;
  }

  public static final class Tower {
    public static final double speed = 0.8;
    public static final int motorID = 0; // TODO
  }

  public static final class Flywheel {
    public static final double gearRatio = 1.0; // TODO
    public static final int motorID = 1; // TODO
    public static final double readyToShootRPMThreshold = 50.0;
  }
}
