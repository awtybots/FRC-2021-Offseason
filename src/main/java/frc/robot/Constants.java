// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

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
    public static final int motorID = 5;
    public static final double speed = 0.6;
    public static final int pistonDown = 5;
    public static final int pistonUp = 4;
  }

  public static final class Tower {
    public static final int motorID = 0; // TODO

    public static final double intakingSpeed = 0.3;
    public static final double shootingSpeed = 0.8;
    public static final double unjammingSpeed = -0.8;
  }

  public static final class Indexer {
    public static final int leftMotorID = 0; // TODO
    public static final int rightMotorID = 0;
    public static final double leftSpeed = 0.0;
    public static final double rightSpeed = 0.0;
  }

  public static final class Shooter {
    public static final boolean unTuned = true;
    public static final int flywheelMotorID = 0; // TODO
    public static final double flywheelGearRatio = 1.0; // TODO
    public static final int readyToShootRPMThreshold = 50;
    public static final int flywheelMaxRPM = 6400;

    public static final double[][] tunedShots =
        new double[][] {
          {0, 3700, 76},
          {1, 4000, 60},
          {2, 4500, 60},
          {3, 4900, 60},
        };

    public static final int hoodMotorID = 0; // TODO
    public static final double hoodGearRatio = 1.0; // TODO
    public static final int minHoodLaunchAngle = 56;
    public static final int maxHoodLaunchAngle = 76;
  }

  public static final class Turret {
    public static final boolean unTuned = true;
    public static final int motorID = 0;

    public static final double homeAngle = 180;
    public static final double minAngle = 135;
    public static final double maxAngle = 225;

    public static final double pulleyRatio = 1.0;
    public static final double maxSpeed = 0.5;
  }

  public static final class Vision {
    public static final double goalHeight = Units.inchesToMeters(81.0 + (17.0 / 2.0));
    ;
    public static final double limelightHeight = Units.inchesToMeters(36.0);
    public static final double limelightAngle = 0.0; // TODO
  }
}
