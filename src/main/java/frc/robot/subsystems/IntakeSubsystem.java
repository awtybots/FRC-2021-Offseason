// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  private WPI_TalonSRX rollerMotor;
  private WPI_TalonSRX liftMotor;

  public IntakeSubsystem() {
    rollerMotor = new WPI_TalonSRX(Constants.Intake.rollerMotor);
    liftMotor = new WPI_TalonSRX(Constants.Intake.liftMotor);
    if (Constants.Intake.invertRoller) rollerMotor.setInverted(true);
    if (Constants.Intake.invertLifter) liftMotor.setInverted(true);
  }

  public void run(double speed) {
    rollerMotor.set(ControlMode.PercentOutput, speed * Constants.Intake.rollerSpeed);
  }

  public void lift(boolean up) {
    // TODO i'm not sure how to do this please help
  }

  @Override
  public void periodic() {}
}
