// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

  public static final class Drive {
    public static final int leftMotor1ID = 11;
    public static final int leftMotor2ID = 12;
    public static final int rightMotor1ID = 13;
    public static final int rightMotor2ID = 14;
  }

  public static final class Intake {
    public static final int armMotorID = 1;
    public static final int rollerMotorID = 2;

    public static final double armGearRatio = 24.0 / 72.0;
    public static final double armAngleUp = -10; // degrees, straight up is 0
    public static final double armAngleDown = 105; // degrees
    public static final double armAngleAcceptableError = 5; // degrees;
    public static final double armMoveTimeout = 3.0; // seconds

    public static final double armStallPercentOutput = 0.0;
    public static final double armDownPercentOutput = -0.25;
    public static final double armUpPercentOutput = 0.25;

    public static final double rollerPercentOutput = 0.8;
  }

  public static final class Tower {
    public static final int frontMotorID = 5;
    public static final int backMotorID = 7;

    public static final double shootingPercentOutput = 0.7;
    public static final double unjammingPercentOutput = -0.4;
  }

  public static final class Indexer {
    public static final int motorID = 3;

    public static final double intakingPercentOutput = 0.25;
    public static final double shootingPercentOutput = 0.8;
    public static final double unjammingPercentOutput = -0.5;
  }

  public static final class Shooter {
    public static final int flywheelMotorID = 15;
    public static final double flywheelGearRatio = 12.0 / 36.0 * 58.0 / 18.0;
    public static final double flywheelMaxRpm = 5400.0;
    public static final double flywheelRpmAcceptableError = 50.0;
    public static final double flywheelRadius = 0.051; // meters

    public static final int hoodMotorID = 6;
    public static final double hoodGearRatio = 30.0 / 72.0 * 0.5;
    public static final double minHoodLaunchAngle = 56.0;
    public static final double maxHoodLaunchAngle = 76.0;
    public static final double hoodLaunchAngleAcceptableError = 1.0; // TODO

    public static final double[][] autoShootInterpolationMap =
        new double[][] {
          // { distance (m), flywheel rpm, launch angle }
          {0.5, 3700.0, 76.0}, // TODO placeholder values
          {1.0, 3900.0, 74.0},
          {1.5, 4200.0, 71.0},
          {2.0, 4500.0, 68.0},
          {2.5, 5000.0, 62.0},
        };
  }

  public static final class Turret {
    public static final int motorID = 4;

    public static final double homeAngle = 180.0;
    public static final double minAngle = 135.0;
    public static final double maxAngle = 359.99;
    public static final double angleAcceptableError = 2.0; // TODO

    public static final double gearRatio = 1.0 / 10.8; // TODO
    public static final double maxSpeed = 0.5;
  }

  public static final class Limelight {
    public static final double mountingHeight = 0.531; // TODO
    public static final double mountingAngle = 23; // TODO

    public static final int powerPortPipeline = 0;
  }

  public static final class Field {
    public static final double powerPortHeight = 2.496; // meters
    public static final double powerPortVisionTargetHeight = 2.496 - 0.216; // meters

    public static final double ballMass = 0.142; // kg
    public static final double ballRadius = 0.178; // meters
  }
}
