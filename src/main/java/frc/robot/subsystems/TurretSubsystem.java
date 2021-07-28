// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.Turret;

public class TurretSubsystem extends SubsystemBase {

  private TalonSRX motor;

  private final double sensorRatio = 1.0 / 4096.0 * Turret.gearRatio * 360.0;
  private double currentAngle = Turret.homeAngle;
  private double goalAngle = currentAngle;
  private boolean atGoal = true;

  public TurretSubsystem() {
    motor = new TalonSRX(Turret.motorID);
    motor.configFactoryDefault();
    motor.setNeutralMode(NeutralMode.Brake);

    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    motor.setSelectedSensorPosition(currentAngle / sensorRatio);
    motor.configSelectedFeedbackCoefficient(sensorRatio); // ? does it multiply the sensor pos too
    motor.configClosedloopRamp(0.1);
    motor.config_kP(0, 0.01); // TODO
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);
    motor.config_kF(0, 0);
  }

  @Override
  public void periodic() {
    currentAngle = motor.getSelectedSensorPosition() * sensorRatio;
    atGoal = Math.abs(goalAngle - currentAngle) < Turret.angleAcceptableError;

    SmartDashboard.putNumber("Turret Goal Angle", goalAngle);
    SmartDashboard.putNumber("Turret Current Angle", currentAngle);
    SmartDashboard.putBoolean("Turret At Goal", atGoal);
  }

  // PUBLIC METHODS

  public void returnToHome() {
    rotateTo(Turret.homeAngle);
  }

  /**
   * Rotate the turret by a certain angle.
   *
   * @param angleDelta relative angle to rotate by, positive is clockwise
   */
  public void rotateBy(double angleDelta) {
    rotateTo(currentAngle + angleDelta);
  }

  /**
   * Rotate the turret to a certain angle.
   *
   * @param angle absolute angle to rotate to, where 180 is straight ahead, 90 is left, 270 is right
   */
  public void rotateTo(double angle) {
    goalAngle = angle % 360.0;
    if (goalAngle < 0.0) goalAngle += 360.0;
    goalAngle = MathUtil.clamp(goalAngle, Turret.minAngle, Turret.maxAngle);

    motor.set(ControlMode.Position, goalAngle / sensorRatio);
  }

  public boolean isAtGoal() {
    return atGoal;
  }
}
