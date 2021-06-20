// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class DriveCommand extends CommandBase {

  private DrivetrainSubsystem m_robotDrive;
  private Joystick controller;
  private int speedAxis;
  private int rotationAxis;

  public DriveCommand(
      DrivetrainSubsystem m_robotDrive, Joystick controller, int speedAxis, int rotationAxis) {
    this.m_robotDrive = m_robotDrive;
    addRequirements(m_robotDrive);

    this.controller = controller;
    this.speedAxis = speedAxis;
    this.rotationAxis = rotationAxis;
  }

  @Override
  public void execute() {
    double speed = controller.getRawAxis(speedAxis);
    double rotation = controller.getRawAxis(rotationAxis);

    m_robotDrive.drive(speed, rotation);
  }
}
