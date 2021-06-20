// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Tower;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TowerSubsystem extends SubsystemBase {

  private final TalonSRX motor = new TalonSRX(Tower.motorID);

  public TowerSubsystem() {
    motor.configFactoryDefault();
    toggle(false);
  }

  public void toggle(boolean on) {
    motor.set(ControlMode.PercentOutput, on ? Tower.speed : 0);
  }

  public void reverse() {
    motor.set(ControlMode.PercentOutput, -Tower.speed);
  }
}
