// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TowerSubsystem extends SubsystemBase {

  private WPI_TalonSRX frontMotor;
  private WPI_TalonSRX backMotor;

  /** Creates a new Tower. */
  public TowerSubsystem() {
    frontMotor = new WPI_TalonSRX(Constants.Tower.frontMotor);
    backMotor = new WPI_TalonSRX(Constants.Tower.backMotor);
  }

  public void run(double frontSpeed, double backSpeed) {
    frontMotor.set(ControlMode.PercentOutput, frontSpeed * Constants.Tower.maxSpeedFront);
    backMotor.set(ControlMode.PercentOutput, backSpeed * Constants.Tower.maxSpeedBack);
  }
}
