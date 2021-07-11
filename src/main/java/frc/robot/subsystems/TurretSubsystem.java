// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.Turret;
import frc.robot.States;
import frc.robot.States.TurretState;
import util.Convert;

public class TurretSubsystem extends SubsystemBase {

  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("kP", null, (double set) -> turret.config_kP(0, set));
    builder.addDoubleProperty("kI", null, (double set) -> turret.config_kI(0, set));
    builder.addDoubleProperty("kD", null, (double set) -> turret.config_kD(0, set));
    builder.addDoubleProperty("kF", null, (double set) -> turret.config_kF(0, set));
  }

  private TalonSRX turret;
  private double setpoint;

  public TurretSubsystem() {
    turret = new TalonSRX(Turret.motorID);
    turret.configFactoryDefault();
    turret.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    turret.setSelectedSensorPosition(
        Convert.canCoderToDegrees(Turret.homeAngle, Turret.pulleyRatio));
    turret.config_kP(0, 0); // TODO
    turret.config_kI(0, 0);
    turret.config_kD(0, 0);
    turret.config_kF(0, 0);
  }

  private double getCurrentAngle() {
    double currentEncoderPos = turret.getSelectedSensorPosition();
    return Convert.canCoderToDegrees(currentEncoderPos, Turret.pulleyRatio);
  }

  @Override
  public void periodic() {
    switch (States.turretState) {
      case home:
        setpoint = Turret.homeAngle; // home state is just targeting home
      case targeting:
        turret.set(ControlMode.Position, Convert.degreesToCanCoder(setpoint, Turret.pulleyRatio));
        break;
      case calibrating:
        // TODO SmartDashboard PID calibrating suite
        break;
      case disabled:
      default:
        turret.set(ControlMode.PercentOutput, 0);
        break;
    }
  }

  // PUBLIC METHODS

  public void returnToHome() {
    States.turretState = TurretState.home;
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

    States.turretState = TurretState.targeting;
  }

  public void disable() {
    States.turretState = TurretState.disabled;
  }
}
