// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Indexer;

public class IndexerSubsystem extends SubsystemBase {

  private TalonSRX motor;

  public IndexerSubsystem() {
    motor = new TalonSRX(Indexer.motorID);
    motor.configFactoryDefault();

    stop();
  }

  private void run(double x) {
    motor.set(ControlMode.PercentOutput, x);
  }

  public void startForIntaking() {
    run(Indexer.intakingPercentOutput);
  }

  public void startForShooting() {
    run(Indexer.shootingPercentOutput);
  }

  public void startForUnjamming() {
    run(Indexer.unjammingPercentOutput);
  }

  public void stop() {
    run(0.0);
  }
}
