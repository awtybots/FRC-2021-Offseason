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

public class ShooterSubsystem extends SubsystemBase {

  /* --- TODO ---
   * Tuning
   * - set PID + goal for both
   * - tune shots into interpolatable map
   */
  private TalonFX flywheel = new TalonFX(Shooter.flywheelMotorID);
  private TalonSRX hood = new TalonSRX(Shooter.hoodMotorID);

  public ShooterSubsystem() {
    flywheel.configFactoryDefault();
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.config_kP(0, 0.0); // TODO
    flywheel.config_kI(0, 0.0);
    flywheel.config_kD(0, 0.0);
    flywheel.config_kF(0, 0.0);

    hood.configFactoryDefault();
    hood.setNeutralMode(NeutralMode.Coast);
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hood.setSelectedSensorPosition(0 /* angleToHoodPosition(maxLaunchAngle) */);
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
    double revs = (90 - launchAngle) / 360.0; // hood angle to revs
    revs *= Shooter.hoodGearRatio;
    return revs * 4096.0; // 4096 ticks per rev on CTRE Mag Encoder
  }

  private void setFlywheelRPM(double rpm) {
    if (0 > rpm || rpm > Shooter.flywheelMaxRPM) return;
    flywheel.set(ControlMode.Velocity, rpmToFlywheelVelocity(rpm));
  }

  private void setHoodLaunchAngle(double angle) {
    if (Shooter.minHoodLaunchAngle > angle || Shooter.maxHoodLaunchAngle < angle) return;
    hood.set(ControlMode.Position, angleToHoodPosition(angle));
  }

  @Override
  public void periodic() {
    switch (States.shooterState) {
      case tracking:
        int[] target = getTargetState();
        setFlywheelRPM(target[0]);
        setHoodLaunchAngle(target[1]);
        break;
      case notCalibrated:
        // TODO add goal RPM, goal angle to dashboard for testing
        // TODO add PID values for tuning
        break;
      case standby:
        flywheel.set(ControlMode.PercentOutput, 0);
        hood.set(ControlMode.Position, Shooter.maxHoodLaunchAngle);
        break;
      case disabled:
      default:
        flywheel.set(ControlMode.PercentOutput, 0);
        hood.set(ControlMode.PercentOutput, 0);
        break;
    }
  }

  private int[] getTargetState() {
    return new int[] {3000, 70}; // TODO actually get desired RPM and angle
  }
}
