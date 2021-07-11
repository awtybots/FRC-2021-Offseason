// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package util;

public class Convert {

  /**
   * @param ticks CANCoder Encoder Ticks
   * @param gearRatio Gear Ratio between CANCoder and Mechanism
   * @return Degrees of Rotation of Mechanism
   */
  public static double canCoderToDegrees(double ticks, double gearRatio) {
    return ticks * (360.0 / (gearRatio * 4096.0));
  }

  /**
   * @param degrees Degrees of rotation of Mechanism
   * @param gearRatio Gear Ratio between CANCoder and Mechanism
   * @return Falcon Encoder Ticks
   */
  public static double degreesToCanCoder(double degrees, double gearRatio) {
    return degrees / (360.0 / (gearRatio * 4096.0));
  }

  /**
   * @param ticks Falcon Encoder Ticks
   * @param gearRatio Gear Ratio between Falcon and Mechanism
   * @return Degrees of Rotation of Mechanism
   */
  public static double falconToDegrees(double ticks, double gearRatio) {
    return ticks * (360.0 / (gearRatio * 2048.0));
  }

  /**
   * @param degrees Degrees of rotation of Mechanism
   * @param gearRatio Gear Ratio between Falcon and Mechanism
   * @return Falcon Encoder Ticks
   */
  public static double degreesToFalcon(double degrees, double gearRatio) {
    return degrees / (360.0 / (gearRatio * 2048.0));
  }

  /**
   * @param velocity Falcon Velocity (ticks per 100ms)
   * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
   * @return RPM of Mechanism
   */
  public static double falconVelocityToRPM(double velocity, double gearRatio) {
    double motorRPM = velocity * (600.0 / 2048.0);
    double mechRPM = motorRPM / gearRatio;
    return mechRPM;
  }

  /**
   * @param RPM RPM of mechanism
   * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
   * @return Falcon Velocity (ticks per 100ms) of Mechanism
   */
  public static double RPMToFalconVelocity(double RPM, double gearRatio) {
    double motorRPM = RPM * gearRatio;
    double sensorCounts = motorRPM * (2048.0 / 600.0);
    return sensorCounts;
  }
}
