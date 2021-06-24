// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package util.math;

public class Shot {
  public final double distanceMeters, rpm, launchAngle;

  public Shot(double rpm, double launchAngle) {
    this(0, rpm, launchAngle);
  }

  public Shot(double distanceMeters, double rpm, double launchAngle) {
    this.distanceMeters = distanceMeters;
    this.rpm = rpm;
    this.launchAngle = launchAngle;
  }
}
