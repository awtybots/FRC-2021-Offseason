// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import util.controls.Controller;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {

  private DrivetrainSubsystem s_Drivetrain;
  private Controller controller;

  public ArcadeDrive(DrivetrainSubsystem s_Drivetrain, Controller controller) {
    this.controller = controller;
    this.s_Drivetrain = s_Drivetrain;
    addRequirements(s_Drivetrain);
  }

  @Override
  public void execute() {
    double speed = controller.getY(Hand.kLeft);
    double rotation = controller.getX(Hand.kRight);

    s_Drivetrain.drive(speed, rotation);
  }
}
