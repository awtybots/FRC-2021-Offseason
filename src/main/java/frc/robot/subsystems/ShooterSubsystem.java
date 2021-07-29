// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;

public class ShooterSubsystem extends SubsystemBase {

  private final TalonFX flywheelMotor;
  private final TalonSRX hoodMotor;

  private final double flywheelSensorRatio = 1.0 / 2048.0 * 10.0 / Shooter.flywheelGearRatio * 60.0;
  private final double hoodSensorRatio = 1.0 / 4096.0 * Shooter.hoodGearRatio * 360.0;

  private double flywheelCurrentRpm = 0.0;
  private double flywheelGoalRpm = 0.0;
  private boolean flywheelAtGoal = true;

  private double hoodCurrentLaunchAngle = Shooter.maxHoodLaunchAngle;
  private double hoodGoalLaunchAngle = hoodCurrentLaunchAngle;
  private boolean hoodAtGoal = true;

  public ShooterSubsystem() {
    flywheelMotor = new TalonFX(Shooter.flywheelMotorID);
    flywheelMotor.configFactoryDefault();
    flywheelMotor.setNeutralMode(NeutralMode.Coast);
    flywheelMotor.configSelectedFeedbackCoefficient(flywheelSensorRatio);
    flywheelMotor.configVoltageCompSaturation(13.0);
    flywheelMotor.config_kP(0, 5.0); // TODO
    flywheelMotor.config_kI(0, 0.0);
    flywheelMotor.config_kD(0, 0.0);
    flywheelMotor.config_kF(0, 0.185);

    hoodMotor = new TalonSRX(Shooter.hoodMotorID);
    hoodMotor.configFactoryDefault();
    hoodMotor.setNeutralMode(NeutralMode.Brake);
    hoodMotor.configClosedloopRamp(0.1);
    hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hoodMotor.setSelectedSensorPosition(hoodCurrentLaunchAngle / hoodSensorRatio);
    hoodMotor.config_kP(0, 1.0); // TODO
    hoodMotor.config_kI(0, 0.0);
    hoodMotor.config_kD(0, 0.0);
    hoodMotor.config_kF(0, 0.0);

    // SmartDashboard.putNumber("Hood Manual Angle", hoodCurrentLaunchAngle);
  }

  @Override
  public void periodic() {
    flywheelCurrentRpm = flywheelMotor.getSelectedSensorVelocity();
    flywheelAtGoal = Math.abs(flywheelCurrentRpm - flywheelGoalRpm) < Shooter.flywheelRpmAcceptableError;

    SmartDashboard.putNumber("Flywheel Current RPM", flywheelCurrentRpm);
    SmartDashboard.putNumber("Flywheel Goal RPM", flywheelGoalRpm);
    SmartDashboard.putBoolean("Flywheel At Goal", flywheelAtGoal);

    hoodCurrentLaunchAngle = hoodMotor.getSelectedSensorPosition() * hoodSensorRatio;
    hoodAtGoal = Math.abs(hoodCurrentLaunchAngle - hoodGoalLaunchAngle) < Shooter.hoodLaunchAngleAcceptableError;
    
    SmartDashboard.putNumber("Hood Current Angle", hoodCurrentLaunchAngle);
    SmartDashboard.putNumber("Hood Goal Angle", hoodGoalLaunchAngle);
    SmartDashboard.putBoolean("Hood At Goal", hoodAtGoal);

    setHoodLaunchAngle(SmartDashboard.getNumber("Hood Manual Angle", hoodCurrentLaunchAngle)); // ! TODO remove
  }

  // PUBLIC METHODS

  public void setFlywheelRpm(double rpm) {
    if (rpm < 0.0 || rpm > Shooter.flywheelMaxRpm)
      return;

    if (rpm != 0.0)
      flywheelMotor.set(ControlMode.Velocity, rpm);
    else
      flywheelMotor.set(ControlMode.PercentOutput, 0);
    flywheelGoalRpm = rpm;
  }

  public void setHoodLaunchAngle(double angle) {
    if (angle < Shooter.minHoodLaunchAngle || Shooter.maxHoodLaunchAngle < angle)
      return;

    hoodMotor.set(ControlMode.Position, angle / hoodSensorRatio);
    hoodGoalLaunchAngle = angle;
  }

  public void standby() {
    setFlywheelRpm(0.0);
    setHoodLaunchAngle(Shooter.maxHoodLaunchAngle);
  }

  public boolean isHoodAtGoal() {
    return hoodAtGoal;
  }

  public boolean isFlywheelAtGoal() {
    return flywheelAtGoal;
  }
}
