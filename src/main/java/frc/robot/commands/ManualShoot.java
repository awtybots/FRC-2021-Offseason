// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.RobotContainer.*;

public class ManualShoot extends CommandBase {

  private double flywheelRpm;
  private double hoodLaunchAngle;

  public ManualShoot(double flywheelRpm, double hoodLaunchAngle) {
    this.flywheelRpm = flywheelRpm;
    this.hoodLaunchAngle = hoodLaunchAngle;

    addRequirements(s_Shooter, s_Tower, s_Indexer);
  }

  @Override
  public void initialize() {
    s_Shooter.setFlywheelRpm(flywheelRpm);
    s_Shooter.setHoodLaunchAngle(hoodLaunchAngle);
    s_Shooter.periodic(); // updates the errors so the first execute() doesn't enable tower
  }

  @Override
  public void execute() {
    if (s_Shooter.isFlywheelAtGoal()) {
      s_Tower.startForShooting();
      s_Indexer.startForShooting();
    }
  }

  @Override
  public void end(boolean interrupted) {
    s_Shooter.standby();
    s_Tower.stop();
    s_Indexer.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
