// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Tower;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TowerSubsystem extends SubsystemBase {

  private final TalonSRX frontMotor, backMotor;

  public TowerSubsystem() {
    frontMotor = new TalonSRX(Tower.frontMotorID);
    frontMotor.configFactoryDefault();

    backMotor = new TalonSRX(Tower.backMotorID);
    backMotor.configFactoryDefault();
    backMotor.setInverted(true);

    stop();
  }

  private void run(double x) {
    frontMotor.set(ControlMode.PercentOutput, x);
    backMotor.set(ControlMode.PercentOutput, x);
  }

  public void start() {
    run(Tower.shootingPercentOutput);
  }

  public void unjam() {
    run(Tower.unjammingPercentOutput);
  }

  public void stop() {
    run(0.0);
  }
}
