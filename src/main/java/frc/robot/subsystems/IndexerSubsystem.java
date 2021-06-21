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
    leftMotor = new TalonSRX(Indexer.leftMotorID);
    rightMotor = new TalonSRX(Indexer.rightMotorID);
    rightMotor.setInverted(true);

    stop();
  }

  private void run(double x) {
    leftMotor.set(ControlMode.PercentOutput, Indexer.leftSpeed * x);
    rightMotor.set(ControlMode.PercentOutput, Indexer.rightSpeed * x);
  }

  public void start() {
    run(1.0);
  }

  public void unjam() {
    run(-1.0);
  }

  public void stop() {
    run(0.0);
  }
}
