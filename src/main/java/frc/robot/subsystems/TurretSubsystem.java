// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.Turret;

public class TurretSubsystem extends SubsystemBase {

  private TalonSRX turret;
  private double setpoint;

  public TurretSubsystem() {
    turret = new TalonSRX(Turret.motorID);
    turret.configFactoryDefault();

    turret.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    turret.setSelectedSensorPosition(
        Turret.homeAngle / 360.0 / Turret.pulleyRatio * 2048.0); // TODO conversions math util
    turret.config_kP(0, 0); // TODO
    turret.config_kI(0, 0);
    turret.config_kD(0, 0);
    turret.config_kF(0, 0);
  }

  private double getCurrentAngle() {
    double encoderPos = turret.getSelectedSensorPosition();
    double currentAngle =
        (encoderPos / 2048.0) * Turret.pulleyRatio * 360.0; // TODO conversions math util
    return currentAngle;
  }

  @Override
  public void periodic() {
    // TODO
  }

  // PUBLIC METHODS

  public void returnToHome() {
    // TODO
  }

  /**
   * Rotate the turret by a certain angle.
   *
   * @param angleDelta relative angle to rotate by, positive is clockwise
   */
  public void rotateBy(double angleDelta) {
    rotateTo(getCurrentAngle() + angleDelta);
  }

  /**
   * Rotate the turret to a certain angle.
   *
   * @param angle absolute angle to rotate to, where 180 is straight ahead, 90 is left, 270 is right
   */
  public void rotateTo(double angle) {
    setpoint = angle % 360;
    if (setpoint < 0) setpoint += 360;
    setpoint = MathUtil.clamp(setpoint, Turret.minAngle, Turret.maxAngle);

    // TODO
  }

  public boolean isAtGoal() {
    double angleError = turret.getClosedLoopError() / 2048.0 * Turret.pulleyRatio * 360.0;
    return Math.abs(angleError) < Turret.angleAcceptableError;
  }
}
