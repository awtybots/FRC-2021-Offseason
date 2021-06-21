// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class States {
  public static enum ShooterState {
    calibrating,
    disabled,
    standby,
    targeting,
  }

  public static enum TurretState {
    calibrating,
    disabled,
    home,
    targeting,
  }

  public static enum TowerState {
    idle,
    intaking,
    shooting,
    unjamming,
  }

  public static ShooterState shooterState = ShooterState.disabled;
  public static TurretState turretState = TurretState.disabled;
  public static TowerState towerState = TowerState.idle;
}
