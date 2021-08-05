// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.StartEndCommand;

public class RunIndexerTower extends StartEndCommand {

  public RunIndexerTower() {
    super(
        () -> {
          s_Indexer.startForShooting();
          s_Tower.startForShooting();
        },
        () -> {
          s_Indexer.stop();
          s_Tower.stop();
        },
        s_Indexer,
        s_Tower);
  }
}
