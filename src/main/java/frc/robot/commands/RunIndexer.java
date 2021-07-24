// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;

public class RunIndexer extends CommandBase {
  /** Creates a new RunIndexer. */
  private IndexerSubsystem indexerSubsystem;

  private double indexerSpeed;

  public RunIndexer(IndexerSubsystem indexerSubsystem, double indexerSpeed) {
    this.indexerSubsystem = indexerSubsystem;
    this.indexerSpeed = indexerSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    indexerSubsystem.run(indexerSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexerSubsystem.run(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
