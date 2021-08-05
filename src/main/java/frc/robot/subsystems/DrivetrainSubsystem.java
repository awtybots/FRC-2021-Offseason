// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
  private WPI_TalonFX frontRight;
  private WPI_TalonFX frontLeft;
  private WPI_TalonFX backRight;
  private WPI_TalonFX backLeft;

  private DifferentialDrive robotDrive;

  public DrivetrainSubsystem() {
    frontRight = new WPI_TalonFX(Constants.Drivetrain.rightFront);
    frontLeft = new WPI_TalonFX(Constants.Drivetrain.leftFront);
    backRight = new WPI_TalonFX(Constants.Drivetrain.rightBack);
    backLeft = new WPI_TalonFX(Constants.Drivetrain.leftBack);

    backRight.follow(frontRight);
    backLeft.follow(frontLeft);

    robotDrive = new DifferentialDrive(frontLeft, frontRight);
    stop();
  }

  public void drive(double speed, double rotation) {
    robotDrive.arcadeDrive(speed, rotation);
  }

  public void stop() {
    drive(0, 0);
  }

  @Override
  public void periodic() {}
}
