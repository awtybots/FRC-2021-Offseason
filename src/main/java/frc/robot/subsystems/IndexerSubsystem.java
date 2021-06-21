// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Indexer;

public class IndexerSubsystem extends SubsystemBase {

  private TalonSRX leftMotor;
  private TalonSRX rightMotor;

  public IndexerSubsystem() {
    leftMotor = new TalonSRX(0);
    rightMotor = new TalonSRX(0);
  }

  public void run() {
    leftMotor.set(ControlMode.PercentOutput, Indexer.leftSpeed);
    rightMotor.set(ControlMode.PercentOutput, Indexer.rightSpeed);
  }

  public void stop() {
    leftMotor.set(ControlMode.PercentOutput, 0);
    rightMotor.set(ControlMode.PercentOutput, 0);
  }
}
