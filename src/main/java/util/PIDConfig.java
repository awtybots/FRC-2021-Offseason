// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package util;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class PIDConfig extends SlotConfiguration implements Sendable {

  public double kP, kI, kD, kF;
  public double integralZone,
      allowableClosedloopError,
      maxIntegralAccumulator,
      closedLoopPeakOutput;
  public int closedLoopPeriod;

  public PIDConfig(double kP, double kI, double kD, double kF) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.kF = kF;
  }

  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("kP", () -> this.kP, (double p) -> this.kP = p);
    builder.addDoubleProperty("kI", () -> this.kI, (double i) -> this.kI = i);
    builder.addDoubleProperty("kD", () -> this.kD, (double d) -> this.kD = d);
    builder.addDoubleProperty("kF", () -> this.kF, (double f) -> this.kF = f);
  }
}
