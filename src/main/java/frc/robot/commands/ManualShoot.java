// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.States;
import frc.robot.States.ShooterState;
import frc.robot.subsystems.ShooterSubsystem;

public class ManualShoot extends StartEndCommand {
  public ManualShoot(ShooterSubsystem s_Shooter, int rpm, int launchAngle) {
    super(
        () -> {
          States.shooterState = ShooterState.tracking;
          s_Shooter.setFlywheelRPM(rpm);
          s_Shooter.setHoodLaunchAngle(launchAngle);
        },
        () -> States.shooterState = ShooterState.disabled,
        s_Shooter);
  }
}
