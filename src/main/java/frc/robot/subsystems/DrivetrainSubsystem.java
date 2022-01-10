// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {

  private WPI_TalonFX driveLeftFront;
  private WPI_TalonFX driveLeftBack;
  private WPI_TalonFX driveRightFront;
  private WPI_TalonFX driveRightBack;

  private DifferentialDrive diffDrive;

  public DrivetrainSubsystem() {
    driveLeftFront = new WPI_TalonFX(Drive.leftMotor1ID);
    driveLeftBack = new WPI_TalonFX(Drive.leftMotor2ID);
    driveRightFront = new WPI_TalonFX(Drive.rightMotor1ID);
    driveRightBack = new WPI_TalonFX(Drive.rightMotor2ID);

    driveLeftFront.setNeutralMode(NeutralMode.Brake);
    driveLeftBack.setNeutralMode(NeutralMode.Brake);
    driveLeftBack.setNeutralMode(NeutralMode.Brake);
    driveRightBack.setNeutralMode(NeutralMode.Brake);

    driveLeftFront.follow(driveLeftBack);
    driveRightFront.follow(driveRightBack);

    diffDrive = new DifferentialDrive(driveLeftBack, driveRightBack);

    stop();
  }

  public void drive(double speed, double rotation) {
    diffDrive.arcadeDrive(speed, rotation);
  }

  public void stop() {
    drive(0.0, 0.0);
  }
}
