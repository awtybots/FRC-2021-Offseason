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

  /* --- TODO ---
   * Tuning
   * - set PID + goal for both
   * - tune shots into interpolatable map
   */
  private TalonFX flywheelMotor;
  private TalonSRX hoodMotor;
  private double[] setpoints;

  public ShooterSubsystem() {
    flywheelMotor = new TalonFX(Shooter.flywheelMotorID);
    flywheelMotor.configFactoryDefault();
    flywheelMotor.setNeutralMode(NeutralMode.Coast);
    flywheelMotor.config_kP(0, 0.0); // TODO
    flywheelMotor.config_kI(0, 0.0);
    flywheelMotor.config_kD(0, 0.0);
    flywheelMotor.config_kF(0, 0.0);

    hoodMotor = new TalonSRX(Shooter.hoodMotorID);
    hoodMotor.configFactoryDefault();
    hoodMotor.setNeutralMode(NeutralMode.Brake);
    hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hoodMotor.setSelectedSensorPosition(angleToHoodPosition(Shooter.maxHoodLaunchAngle));
    hoodMotor.config_kP(0, 0.0); // TODO
    hoodMotor.config_kI(0, 0.0);
    hoodMotor.config_kD(0, 0.0);
    hoodMotor.config_kF(0, 0.0);
  }

  private static double rpmToFlywheelVelocity(double rpm) { // flywheel RPM --> motor ticks/100ms
    rpm *= Shooter.flywheelGearRatio;
    double revsPer100ms = rpm / 60.0 / 10.0;
    return revsPer100ms * 2048.0; // 2048 ticks per rev on falcon encoder
  }

  private static double angleToHoodPosition(double launchAngle) {
    double revs = launchAngle / 360.0; // hood angle to revs
    revs *= Shooter.hoodGearRatio;
    return revs * 4096.0; // 4096 ticks per rev on CTRE Mag Encoder
  }

  private void setFlywheelRPM(double rpm) {
    if (0 > rpm || rpm > Shooter.flywheelMaxRpm) return;
    if (rpm != 0) flywheelMotor.set(ControlMode.Velocity, rpmToFlywheelVelocity(rpm));
    else flywheelMotor.set(ControlMode.PercentOutput, 0);
  }

  private void setHoodLaunchAngle(double angle) {
    if (Shooter.minHoodLaunchAngle > angle || Shooter.maxHoodLaunchAngle < angle) return;
    hoodMotor.set(ControlMode.Position, angleToHoodPosition(angle));
  }

  @Override
  public void periodic() {
    // TODO
  }

  // PUBLIC METHODS

  public void setTarget(double flywheelRpm, double hoodLaunchAngle) {
    setpoints = new double[] {flywheelRpm, hoodLaunchAngle};

    flywheelMotor.set(ControlMode.PercentOutput, 0.5); // TODO
  }

  public boolean isHoodAtGoal() {
    double hoodLaunchAngleError =
        hoodMotor.getClosedLoopError() / 4096.0 / Shooter.hoodGearRatio * 360.0;
    return Math.abs(hoodLaunchAngleError) < Shooter.hoodLaunchAngleAcceptableError;
  }

  public boolean isFlywheelAtGoal() {
    double flywheelRpmError =
        flywheelMotor.getClosedLoopError() / 2048.0 * 10.0 / Shooter.flywheelGearRatio * 60.0;
    return Math.abs(flywheelRpmError) < Shooter.flywheelRpmAcceptableError;
  }

  public void standby() {
    flywheelMotor.set(ControlMode.PercentOutput, 0.0);
  }
}
