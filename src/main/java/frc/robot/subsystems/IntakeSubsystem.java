// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonSRX motor = new TalonSRX(Intake.motorID);
  private final DoubleSolenoid pistons = new DoubleSolenoid(Intake.pistonDown, Intake.pistonUp);

  public IntakeSubsystem() {
    motor.configFactoryDefault();
    toggle(false, false);
  }

  public void toggle(boolean down, boolean runMotor) {
    pistons.set(down ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    motor.set(ControlMode.PercentOutput, runMotor ? Intake.speed : 0);
  }
}
