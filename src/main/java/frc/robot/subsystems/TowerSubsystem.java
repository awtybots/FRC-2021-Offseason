// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Tower;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.States;
import frc.robot.States.TowerState;

public class TowerSubsystem extends SubsystemBase {

  private final TalonSRX motor;

  public TowerSubsystem() {
    motor = new TalonSRX(Tower.motorID);
    motor.configFactoryDefault();
    stop();
  }

  public void intake() {
    States.towerState = TowerState.intaking;
    motor.set(ControlMode.PercentOutput, Tower.intakingSpeed);
  }

  public void shoot() {
    States.towerState = TowerState.shooting;
    motor.set(ControlMode.PercentOutput, Tower.shootingSpeed);
  }

  public void unjam() {
    States.towerState = TowerState.unjamming;
    motor.set(ControlMode.PercentOutput, Tower.unjammingSpeed);
  }

  public void stop() {
    States.towerState = TowerState.idle;
    motor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    switch (States.towerState) {
      case intaking:
        // TODO check for limit switch and stop if full
        break;

      default:
        break;
    }
  }
}
