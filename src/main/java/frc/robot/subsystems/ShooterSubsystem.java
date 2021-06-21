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
import frc.robot.States;
import frc.robot.States.ShooterState;

public class ShooterSubsystem extends SubsystemBase {

  /* --- TODO ---
   * Tuning
   * - set PID + goal for both
   * - tune shots into interpolatable map
   */
  private TalonFX flywheel;
  private TalonSRX hood;
  private double[] setpoints;

  public ShooterSubsystem() {
    flywheel = new TalonFX(Shooter.flywheelMotorID);
    flywheel.configFactoryDefault();
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.config_kP(0, 0.0); // TODO
    flywheel.config_kI(0, 0.0);
    flywheel.config_kD(0, 0.0);
    flywheel.config_kF(0, 0.0);

    hood = new TalonSRX(Shooter.hoodMotorID);
    hood.configFactoryDefault();
    hood.setNeutralMode(NeutralMode.Brake);
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hood.setSelectedSensorPosition(angleToHoodPosition(Shooter.maxHoodLaunchAngle));
    hood.config_kP(0, 0.0); // TODO
    hood.config_kI(0, 0.0);
    hood.config_kD(0, 0.0);
    hood.config_kF(0, 0.0);
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
    if (rpm != 0) flywheel.set(ControlMode.Velocity, rpmToFlywheelVelocity(rpm));
    else flywheel.set(ControlMode.PercentOutput, 0);
  }

  private void setHoodLaunchAngle(double angle) {
    if (Shooter.minHoodLaunchAngle > angle || Shooter.maxHoodLaunchAngle < angle) return;
    hood.set(ControlMode.Position, angleToHoodPosition(angle));
  }

  @Override
  public void periodic() {
    switch (States.shooterState) {
      case targeting:
        setFlywheelRPM(setpoints[0]);
        setHoodLaunchAngle(setpoints[1]);
        break;
      case calibrating:
        // TODO add goal RPM, goal angle to dashboard for testing
        // TODO add PID values for tuning
        break;
      case standby:
        setFlywheelRPM(0);
        setHoodLaunchAngle(Shooter.maxHoodLaunchAngle);
        break;
      case disabled:
      default:
        flywheel.set(ControlMode.PercentOutput, 0);
        hood.set(ControlMode.PercentOutput, 0);
        break;
    }
  }

  // PUBLIC METHODS

  public void setTarget(double flywheelRpm, double hoodLaunchAngle) {
    setpoints = new double[] {flywheelRpm, hoodLaunchAngle};
    States.shooterState = ShooterState.targeting;
  }

  public boolean isHoodAtGoal() {
    double hoodLaunchAngleError = hood.getClosedLoopError() / 4096.0 / Shooter.hoodGearRatio * 360.0;
    return Math.abs(hoodLaunchAngleError) < Shooter.hoodLaunchAngleAcceptableError;
  }

  public boolean isFlywheelAtGoal() {
    double flywheelRpmError = flywheel.getClosedLoopError() / 2048.0 * 10.0 / Shooter.flywheelGearRatio * 60.0;
    return Math.abs(flywheelRpmError) < Shooter.flywheelRpmAcceptableError;
  }

  public void standby() {
    States.shooterState = ShooterState.standby;
  }

  public void disable() {
    States.shooterState = ShooterState.disabled;
  }
}
