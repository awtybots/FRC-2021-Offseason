// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Drive;

public class DrivetrainSubsystem extends SubsystemBase {
  
  private WPI_TalonFX driveLeftFront;
  private WPI_TalonFX driveLeftBack;
  private WPI_TalonFX driveRightFront;
  private WPI_TalonFX driveRightBack;

  private DifferentialDrive m_robotDrive;

  /** Creates a new DrivetrainSubsystem. */
  public DrivetrainSubsystem() {

    driveLeftFront = new WPI_TalonFX(Drive.leftMotor1ID);
    driveLeftBack = new WPI_TalonFX(Drive.leftMotor2ID);
    driveRightFront = new WPI_TalonFX(Drive.rightMotor1ID);
    driveRightBack = new WPI_TalonFX(Drive.rightMotor2ID);

    driveLeftFront.follow(driveLeftBack);
    driveRightFront.follow(driveRightBack);    

    m_robotDrive = new DifferentialDrive(driveLeftFront, driveRightFront);

  }

  public void drive(double speed, double rotation) {
    m_robotDrive.arcadeDrive(speed, rotation);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
