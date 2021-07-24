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
    public static final boolean invertRightSide = true;
    public static final int rightFront = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final int rightBack = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final int leftFront = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final int leftBack = 0; // TODO fill these in with the correct CAN Bus IDs
  }

  public static class Tower {
    public static final boolean invertFrontMotor = false;
    public static final boolean invertBackMotor = false;
    public static final int frontMotor = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final int backMotor = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final double maxSpeedBack = 0.75; // TODO FIX
    public static final double maxSpeedFront = 0.75; // TODO FIX
  }

  public static class Indexer {
    public static final boolean inverted = false;
    public static final int indexerMotor = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final double maxSpeed = 0.35;
  }

  public static class Intake {
    public static final boolean invertLifter = false;
    public static final boolean invertRoller = false;
    public static final int liftMotor = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final int rollerMotor = 0; // TODO fill these in with the correct CAN Bus IDs
    public static final double rollerSpeed = 0;
  }
}
