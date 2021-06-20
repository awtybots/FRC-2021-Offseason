// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.TowerSubsystem;

public class ToggleTower extends StartEndCommand {

  public ToggleTower(TowerSubsystem tower, boolean reverse) {
    super(
      () -> {if (reverse) tower.reverse(); else tower.toggle(true);},
      () -> tower.toggle(false),
      tower
    );
  }

}