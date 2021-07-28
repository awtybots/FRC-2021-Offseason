// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonSRX rollerMotor;
  private final TalonSRX armMotor;

  public IntakeSubsystem() {
    armMotor = new TalonSRX(Intake.armMotorID);
    armMotor.configFactoryDefault();
    armMotor.setNeutralMode(NeutralMode.Brake);
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    armMotor.setSelectedSensorPosition(0);

    rollerMotor = new TalonSRX(Intake.rollerMotorID);
    rollerMotor.configFactoryDefault();

    armMotor.set(ControlMode.PercentOutput, Intake.armStallPercentOutput);
  }

  public void toggleRoller(boolean on) {
    rollerMotor.set(ControlMode.PercentOutput, on ? Intake.rollerPercentOutput : 0);
  }
}
