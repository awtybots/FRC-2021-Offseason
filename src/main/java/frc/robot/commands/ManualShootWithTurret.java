// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualShootWithTurret extends CommandBase {

  private double flywheelRpm;
  private double hoodLaunchAngle;

  public ManualShootWithTurret(double flywheelRpm, double hoodLaunchAngle) {
    this.flywheelRpm = flywheelRpm;
    this.hoodLaunchAngle = hoodLaunchAngle;

    addRequirements(s_Shooter, s_Turret, s_Indexer, s_Tower, s_Limelight);
  }

  @Override
  public void initialize() {
    s_Shooter.setFlywheelRPM(flywheelRpm);
    s_Shooter.setLaunchAngle(hoodLaunchAngle);
    s_Shooter.periodic(); // updates the errors so the first execute() doesn't enable tower

    s_Limelight.toggleForPowerPort(true);
  }

  @Override
  public void execute() {
    if (s_Limelight.hasTarget()) s_Turret.rotateBy(s_Limelight.targetXOffset());

    if (s_Shooter.atTarget() && s_Turret.atGoal()) {
      s_Tower.startForShooting();
      s_Indexer.startForShooting();
    }
  }

  @Override
  public void end(boolean interrupted) {
    s_Shooter.standby();
    s_Tower.stop();
    s_Indexer.stop();
    s_Turret.returnToHome();

    s_Limelight.toggleForPowerPort(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
