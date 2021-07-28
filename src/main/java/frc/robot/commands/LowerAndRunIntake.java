// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import static frc.robot.RobotContainer.*;

public class LowerAndRunIntake extends StartEndCommand {

  public LowerAndRunIntake() {
    super(() -> {
      s_Intake.toggleArm(true);
      s_Intake.toggleRoller(true);
      s_Indexer.startForIntaking();
    }, () -> {
      s_Intake.toggleRoller(false);
      s_Indexer.stop();
    }, s_Intake, s_Indexer);
  }
}
