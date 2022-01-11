// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;

public class ShooterSubsystem extends SubsystemBase {

  private final TalonSRX hood;
  private final TalonFX flywheel;

  private double targetRPM = 0;
  private double targetLaunchAngle = Shooter.maxHoodLaunchAngle;
  private boolean atRPM = true, atLaunchAngle = true;

  public ShooterSubsystem() {
    hood = new TalonSRX(Shooter.hoodMotorID);
    flywheel = new TalonFX(Shooter.flywheelMotorID);

    configureMotors(hood, flywheel);
  }

  private void configureMotors(TalonSRX hood, TalonFX flywheel) {
    flywheel.configFactoryDefault();
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configVoltageCompSaturation(13.0);

    flywheel.configSelectedFeedbackCoefficient(Shooter.flywheelSensorRatio);
    flywheel.config_kP(0, 0.5);
    flywheel.config_kI(0, 0.0);
    flywheel.config_kD(0, 0.0);
    flywheel.config_kF(0, 0.18);

    hood.configFactoryDefault();
    hood.setNeutralMode(NeutralMode.Brake);
    hood.configClosedloopRamp(0.1);

    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hood.setSelectedSensorPosition(Shooter.launchAngleToHoodEncoder(targetLaunchAngle));
    hood.configAllowableClosedloopError(
        0, Shooter.launchAngleToHoodEncoder(Shooter.hoodLaunchAngleAcceptableError / 2.0));
    hood.config_kP(0, 1.0);
    hood.config_kI(0, 0.01);
    hood.configMaxIntegralAccumulator(0, 4096);
    hood.config_kD(0, 0.0);
    hood.config_kF(0, 0.0);
  }

  private boolean withinMargin(double value, double target, double margin) {
    return Math.abs(target - value) < margin;
  }

  @Override
  public void periodic() {
    double currentLaunchAngle = Shooter.hoodEncoderToLaunchAngle(hood.getSelectedSensorPosition());
    double currentRPM = flywheel.getSelectedSensorVelocity();

    atRPM = withinMargin(currentRPM, targetRPM, Shooter.flywheelRpmAcceptableError);
    atLaunchAngle =
        withinMargin(currentLaunchAngle, targetLaunchAngle, Shooter.hoodLaunchAngleAcceptableError);
  }

  public void setFlywheelRPM(double rpm) {
    if (rpm < 0.0 || Double.isNaN(rpm)) return;
    if (rpm > Shooter.flywheelMaxRpm) rpm = Shooter.flywheelMaxRpm;

    if (rpm != 0.0) flywheel.set(ControlMode.Velocity, rpm);
    else flywheel.set(ControlMode.PercentOutput, 0);

    targetRPM = rpm;
  }

  public void setLaunchVelocity(double metersPerSec) {
    double rpm = Shooter.launchVelocityToFlywheelRPM(metersPerSec);
    setFlywheelRPM(rpm);
  }

  public void setLaunchAngle(double launchAngle) {
    if (launchAngle < Shooter.minHoodLaunchAngle) launchAngle = Shooter.minHoodLaunchAngle;
    else if (launchAngle > Shooter.maxHoodLaunchAngle) launchAngle = Shooter.maxHoodLaunchAngle;

    hood.set(ControlMode.Position, Shooter.launchAngleToHoodEncoder(launchAngle));
    targetLaunchAngle = launchAngle;
  }

  public void standby() {
    disable();
  }

  public void disable() {
    setFlywheelRPM(0);
    setLaunchAngle(Shooter.maxHoodLaunchAngle);
  }

  public boolean atTarget() {
    return atLaunchAngle && atRPM;
  }
}
