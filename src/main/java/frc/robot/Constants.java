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
  public static class Drivetrain {
    public static final boolean invertRightSide = false;
    public static final int rightBack = 1;
    public static final int rightFront = 2;
    public static final int leftBack = 4;
    public static final int leftFront = 3;
  }

  public static class Tower {
    public static final boolean invertFrontMotor = false;
    public static final boolean invertBackMotor = false;
    public static final int frontMotor = 5;
    public static final int backMotor = 6;
    public static final double maxSpeedBack = 0.45; // TODO change to realistic number (0.0 to 1.0)
    public static final double maxSpeedFront = 0.45; // TODO change to realistic number (0.0 to 1.0)
  }

  public static class Indexer {
    public static final boolean inverted = false;
    public static final int indexerRight = 9;
    public static final int indexerLeft = 10;
    public static final double maxSpeed = 0.35; // TODO change to realistic number (0.0 to 1.0)
  }

  public static class Intake {
    public static final boolean invertLifter = false;
    public static final boolean invertRoller = false;
    public static final int liftMotor = 7;
    public static final int rollerMotor = 8;
    public static final double rollerSpeed = 0.3; // TODO change to realistic number (0.0 to 1.0)
  }

  public static class Shooter {
    public static final int flywheelLeftID = 11;
    public static final int flywheelRightID = 12;

    public static final double flywheelGearRatio = 1.0; // ! TODO Change to accurate ratio
    public static final double flywheelSensorRatio = 1.0 / 2048.0 * 10.0 / flywheelGearRatio * 60.0;
    public static final double flywheelMaxRpm = 3000.0; // TODO change to higher number once safe
    public static final double flywheelRpmAcceptableError = 50.0;
  }
}
