// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new IndexerSubsystem. */
  private WPI_TalonSRX indexerMotor;

  public IndexerSubsystem() {
    indexerMotor = new WPI_TalonSRX(Constants.Indexer.indexerMotor);
    if (Constants.Indexer.inverted) indexerMotor.setInverted(true);
  }

  public void run(double speed) {
    indexerMotor.set(ControlMode.PercentOutput, speed * Constants.Indexer.maxSpeed);
  }
}
