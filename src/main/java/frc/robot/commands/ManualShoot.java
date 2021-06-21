// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ManualShoot extends StartEndCommand {
  public ManualShoot(ShooterSubsystem s_Shooter, int flywheelRpm, int hoodLaunchAngle) {
    super(
        () -> s_Shooter.setTarget(flywheelRpm, hoodLaunchAngle),
        () -> s_Shooter.standby(),
        s_Shooter);
  }
}
