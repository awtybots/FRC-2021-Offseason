// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ManualShoot extends CommandBase {

  private final double flywheelRpm;
  private final ShooterSubsystem s_Shooter;

  public ManualShoot(ShooterSubsystem s_Shooter, double flywheelRpm) {
    this.flywheelRpm = flywheelRpm;
    this.s_Shooter = s_Shooter;

    addRequirements(s_Shooter);
  }

  @Override
  public void initialize() {
    s_Shooter.setFlywheelRpm(flywheelRpm);
    s_Shooter.periodic(); // updates the errors so the first execute() doesn't enable tower
  }

  @Override
  public void execute() {
    // TODO Complete
    // if (s_Shooter.isFlywheelAtGoal()) {
    //   s_Tower.startForShooting();
    //   s_Indexer.startForShooting();
    // }
  }

  @Override
  public void end(boolean interrupted) {
    s_Shooter.stop();
    // s_Tower.stop();
    // s_Indexer.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}