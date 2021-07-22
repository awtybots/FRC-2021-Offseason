// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TowerSubsystem;

public class RunTowerCommand extends CommandBase {
  /** Creates a new RunTowerCommand :) . */
  private TowerSubsystem towerSubsystem;

  private double frontSpeed;
  private double backSpeed;

  public RunTowerCommand(TowerSubsystem towerSubsystem, double frontSpeed, double backSpeed) {
    this.towerSubsystem = towerSubsystem;
    this.frontSpeed = frontSpeed;
    this.backSpeed = backSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(towerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // set speed and pass it
    towerSubsystem.run(frontSpeed, backSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  // @Override
  // public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    towerSubsystem.run(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
