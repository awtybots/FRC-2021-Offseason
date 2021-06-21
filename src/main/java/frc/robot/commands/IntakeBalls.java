// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class IntakeBalls extends StartEndCommand {

  public IntakeBalls(IntakeSubsystem s_Intake, IndexerSubsystem s_Indexer, TowerSubsystem s_Tower) {
    super(() -> {
      s_Intake.toggle(true, true);
      s_Indexer.start();
      s_Tower.startForIntaking();
    }, () -> {
      s_Intake.toggle(false, false);
      s_Indexer.stop();
      s_Tower.stop();
    },
    s_Intake,
    s_Indexer,
    s_Tower);
  }
}
