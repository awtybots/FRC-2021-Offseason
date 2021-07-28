// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.RobotContainer;
import util.controls.Controller;

public class TeleopDrive extends CommandBase {

  private Controller controller;

  public TeleopDrive(Controller controller) {
    this.controller = controller;
    addRequirements(RobotContainer.s_Drivetrain);
  }

  @Override
  public void execute() {
    Pair<Double, Double> driveInputs = gtaDrive();
    RobotContainer.s_Drivetrain.drive(driveInputs.getFirst(), driveInputs.getSecond());
  }

  @SuppressWarnings("unused")
  private Pair<Double, Double> arcadeDrive() {
    double speed = controller.getY(Hand.kLeft);
    double rotation = controller.getX(Hand.kRight);

    return new Pair<Double, Double>(speed, rotation);
  }

  private Pair<Double, Double> gtaDrive() {
    double speed = controller.getTrigger(Hand.kRight) - controller.getTrigger(Hand.kLeft);
    double rotation = controller.getX(Hand.kLeft);

    return new Pair<Double, Double>(speed, rotation);
  }
}
