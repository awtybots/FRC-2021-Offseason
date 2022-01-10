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
import frc.robot.Constants.Settings;
import frc.robot.Constants.Shooter;

public class ShooterSubsystem extends SubsystemBase {

  private final TalonFX flywheelMotor;
  private final TalonSRX hoodMotor;

  private double currentRpm = 0.0;
  private double goalRpm = 0.0;
  private boolean flywheelAtGoal = true;

  private double currentLaunchAngle = Shooter.maxHoodLaunchAngle;
  private double goalLaunchAngle = currentLaunchAngle;
  private boolean hoodAtGoal = true;

  public ShooterSubsystem() {
    flywheelMotor = new TalonFX(Shooter.flywheelMotorID);
    hoodMotor = new TalonSRX(Shooter.hoodMotorID);

    configureMotors(hoodMotor, flywheelMotor);

    SmartDashboard.putNumber("Flywheel Manual RPM", 0.0);
    SmartDashboard.putNumber("Hood Manual Angle", currentLaunchAngle);
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
    hood.setSelectedSensorPosition(Shooter.launchAngleToHoodEncoder(currentLaunchAngle));
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
    currentRpm = flywheelMotor.getSelectedSensorVelocity();
    flywheelAtGoal = withinMargin(currentRpm, goalRpm, Shooter.flywheelRpmAcceptableError);

    SmartDashboard.putNumber("Flywheel Current RPM", currentRpm);
    SmartDashboard.putNumber("Flywheel Goal RPM", goalRpm);
    SmartDashboard.putBoolean("Flywheel At Goal", flywheelAtGoal);
    SmartDashboard.putNumber("Flywheel Motor Output", flywheelMotor.getMotorOutputPercent());

    currentLaunchAngle = Shooter.hoodEncoderToLaunchAngle(hoodMotor.getSelectedSensorPosition());
    hoodAtGoal =
        withinMargin(currentLaunchAngle, goalLaunchAngle, Shooter.hoodLaunchAngleAcceptableError);

    SmartDashboard.putNumber("Hood Current Angle", currentLaunchAngle);
    SmartDashboard.putNumber("Hood Goal Angle", goalLaunchAngle);
    SmartDashboard.putBoolean("Hood At Goal", hoodAtGoal);
    SmartDashboard.putNumber("Hood Motor Output", hoodMotor.getMotorOutputPercent());

    if (Settings.testMode) {
      setFlywheelRpm(SmartDashboard.getNumber("Flywheel Manual RPM", 0.0));
      setHoodLaunchAngle(SmartDashboard.getNumber("Hood Manual Angle", currentLaunchAngle));
    }
  }

  // PUBLIC METHODS

  public void setFlywheelRpm(double rpm) {
    if (rpm < 0.0 || Double.isNaN(rpm)) return;
    if (rpm > Shooter.flywheelMaxRpm) rpm = Shooter.flywheelMaxRpm;

    if (rpm != 0.0) flywheelMotor.set(ControlMode.Velocity, rpm);
    else flywheelMotor.set(ControlMode.PercentOutput, 0);

    goalRpm = rpm;
  }

  public void setHoodLaunchAngle(double angle) {
    if (angle < Shooter.minHoodLaunchAngle) angle = Shooter.minHoodLaunchAngle;
    else if (angle > Shooter.maxHoodLaunchAngle) angle = Shooter.maxHoodLaunchAngle;

    hoodMotor.set(ControlMode.Position, Shooter.launchAngleToHoodEncoder(angle));
    goalLaunchAngle = angle;
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
