// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Settings;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonSRX rollerMotor;
  private final TalonSRX armMotor;

  private final double armSensorRatio = 1.0 / 4096.0 * Intake.armGearRatio * 360.0;

  private boolean armDown = false;
  private boolean armMoving = false;
  private double armGoalAngle = Intake.armAngleUp;

  private Timer armMoveTimer = new Timer();

  public IntakeSubsystem() {
    armMotor = new TalonSRX(Intake.armMotorID);
    armMotor.configFactoryDefault();
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    armMotor.setSensorPhase(true);
    armMotor.setSelectedSensorPosition(Intake.armAngleUp / armSensorRatio);
    armMotor.setNeutralMode(NeutralMode.Brake);
    setArmMotor(Intake.armStallPercentOutput);

    rollerMotor = new TalonSRX(Intake.rollerMotorID);
    rollerMotor.configFactoryDefault();

    armMoveTimer.start();

    SmartDashboard.putBoolean("Intake Arm Down Manual", false);
  }

  @Override
  public void periodic() {
    double armAngle = armMotor.getSelectedSensorPosition() * armSensorRatio;
    boolean armAngleAtGoal = Math.abs(armAngle - armGoalAngle) < Intake.armAngleAcceptableError;

    SmartDashboard.putNumber("Intake Arm Angle", armAngle);
    SmartDashboard.putBoolean("Intake Arm Moving", armMoving);
    SmartDashboard.putBoolean("Intake Arm At Goal", armAngleAtGoal);

    if (armMoving) {
      if (armMoveTimer.get() > Intake.armMoveTimeout || armAngleAtGoal) {
        armMoving = false;
        if (armDown) setArmMotor(0.0);
        else setArmMotor(Intake.armStallPercentOutput);
      }
    }

    if (Settings.testMode) toggleArm(SmartDashboard.getBoolean("Intake Arm Down Manual", armDown));
  }

  public void toggleArm(boolean down) {
    if (down == armDown) return;

    armDown = down;
    armMoveTimer.reset();
    armMoving = true;

    if (armDown) {
      setArmMotor(Intake.armDownPercentOutput);
      armGoalAngle = Intake.armAngleDown;
    } else {
      setArmMotor(Intake.armUpPercentOutput);
      armGoalAngle = Intake.armAngleUp;
    }
  }

  private void setArmMotor(double x) {
    SmartDashboard.putNumber("Intake Motor Output", x);
    armMotor.set(ControlMode.PercentOutput, x);
  }

  public void toggleRoller(boolean on) {
    rollerMotor.set(ControlMode.PercentOutput, on ? Intake.rollerPercentOutput : 0);
  }
}
