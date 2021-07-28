// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import static frc.robot.RobotContainer.*;

public class IntakeBalls extends StartEndCommand {

  public IntakeBalls() { // TODO arm position
    super(() -> {
      s_Intake.toggleRoller(true);
    }, () -> {
      s_Intake.toggleRoller(false);
    }, s_Intake);
  }
}
