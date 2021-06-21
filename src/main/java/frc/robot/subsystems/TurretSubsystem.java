// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Turret;
import frc.robot.States;

public class TurretSubsystem extends SubsystemBase {

  private TalonSRX turret;

  public TurretSubsystem() {
    turret = new TalonSRX(Turret.motorID); // TODO
    turret.configFactoryDefault();
    turret.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    turret.setSelectedSensorPosition(0);
    turret.config_kP(0, 0); // TODO
    turret.config_kI(0, 0);
    turret.config_kD(0, 0);
    turret.config_kF(0, 0);
  }

  public void returnToCenter() {
    moveToAbsolute(0);
  }

  public void moveToRelative(int angleDelta) {
    double encoderPos = turret.getSelectedSensorPosition();
    double currentAngle =
        (encoderPos / 2048.0) * Turret.pulleyRatio * 360.0; // TODO move math to diff place
    moveToAbsolute((int) currentAngle + angleDelta);
  }

  private void moveToAbsolute(int angle) { // angle = 200
    // 0 --> straight ahead; -45 --> left 45deg; 45 --> right 45deg
    angle %= 360;
    int setpoint = angle;
    if (angle < Turret.minAngle || angle > Turret.maxAngle) {
      if (angle < -180) angle += 360; // becomes positive
      else if (angle > 180) angle -= 360;

      if ((Turret.minAngle - angle) == (Turret.maxAngle - angle)) setpoint = Turret.maxAngle;
      else if ((Turret.minAngle - angle) > (Turret.maxAngle - angle)) setpoint = Turret.maxAngle;
      else setpoint = Turret.minAngle;
    }
    turret.set(ControlMode.Position, setpoint);
  }

  @Override
  public void periodic() {
    switch (States.turretState) {
      case standby:
        returnToCenter();
        break;
      case disabled:
      default:
        turret.set(ControlMode.PercentOutput, 0);
        break;
    }
  }
}
