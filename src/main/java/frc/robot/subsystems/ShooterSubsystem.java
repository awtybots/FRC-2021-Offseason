// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;

public class ShooterSubsystem extends SubsystemBase {

  private WPI_TalonFX leftFlywheelMotor;
  private WPI_TalonFX rightFlywheelMotor;

  private double flywheelGoalRpm = 0.0;
  private boolean flywheelAtGoal;
  private double flywheelCurrentRpm;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    leftFlywheelMotor = new WPI_TalonFX(Shooter.flywheelLeftID);
    rightFlywheelMotor = new WPI_TalonFX(Shooter.flywheelRightID);

    rightFlywheelMotor.configFactoryDefault();
    rightFlywheelMotor.setNeutralMode(NeutralMode.Coast);
    rightFlywheelMotor.configSelectedFeedbackCoefficient(Shooter.flywheelSensorRatio);
    rightFlywheelMotor.configVoltageCompSaturation(13.0);
    rightFlywheelMotor.config_kP(0, 0.0); // TODO
    rightFlywheelMotor.config_kI(0, 0.0);
    rightFlywheelMotor.config_kD(0, 0.0);
    rightFlywheelMotor.config_kF(0, 0.0);

    leftFlywheelMotor.configFactoryDefault();
    leftFlywheelMotor.setInverted(InvertType.OpposeMaster);
    leftFlywheelMotor.follow(rightFlywheelMotor);
    leftFlywheelMotor.setNeutralMode(NeutralMode.Coast);
    leftFlywheelMotor.configSelectedFeedbackCoefficient(Shooter.flywheelSensorRatio);
  }

  public void stop() {
    setFlywheelRpm(0);
  }
  public void setFlywheelRpm(double rpm) {
    if (rpm < 0.0 || rpm > Shooter.flywheelMaxRpm) return;

    if (rpm != 0.0) {
      rightFlywheelMotor.set(ControlMode.Velocity, rpm);
    } else {
      rightFlywheelMotor.set(ControlMode.PercentOutput, 0);
    }
    flywheelGoalRpm = rpm;
  }

  public boolean isFlywheelAtGoal() {
    return flywheelAtGoal;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    flywheelCurrentRpm = rightFlywheelMotor.getSelectedSensorVelocity();
    flywheelAtGoal =
        Math.abs(flywheelCurrentRpm - flywheelGoalRpm) < Shooter.flywheelRpmAcceptableError;

    SmartDashboard.putNumber("Flywheel Current RPM", flywheelCurrentRpm);
    SmartDashboard.putNumber("Flywheel Goal RPM", flywheelGoalRpm);
    SmartDashboard.putBoolean("Flywheel At Goal", flywheelAtGoal);
  }
}
