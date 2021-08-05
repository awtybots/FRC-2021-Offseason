// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class ArcadeDrive extends CommandBase {
  /** Creates a new DriveCommand. */
  private DrivetrainSubsystem drivetrainSubsystem;

  private XboxController controller;

  public ArcadeDrive(DrivetrainSubsystem sDrivetrainSubsystem, XboxController driveController) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(sDrivetrainSubsystem);
    drivetrainSubsystem = sDrivetrainSubsystem;
    controller = driveController;
  }

  // Called when the command is initially scheduled.
  // @Override
  // public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = controller.getY(Hand.kLeft);
    double rotation = controller.getX(Hand.kRight);
    drivetrainSubsystem.drive(speed, rotation);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
