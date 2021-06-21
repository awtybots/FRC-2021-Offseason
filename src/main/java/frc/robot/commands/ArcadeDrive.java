// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {

  private DrivetrainSubsystem s_Drivetrain;
  private Joystick controller;
  private int speedAxis;
  private int rotationAxis;

  public ArcadeDrive(
      DrivetrainSubsystem s_Drivetrain, Joystick controller, int speedAxis, int rotationAxis) {
    this.s_Drivetrain = s_Drivetrain;
    addRequirements(s_Drivetrain);

    this.controller = controller;
    this.speedAxis = speedAxis;
    this.rotationAxis = rotationAxis;
  }

  @Override
  public void execute() {
    double speed = controller.getRawAxis(speedAxis);
    double rotation = controller.getRawAxis(rotationAxis);

    s_Drivetrain.drive(speed, rotation);
  }
}
