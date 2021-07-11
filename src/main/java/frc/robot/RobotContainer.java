// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Shooter;
import frc.robot.Constants.Turret;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import util.ShotCalculator;
import util.ShotInterpolator;
import util.ShotTuner;
import util.controls.Controller;
import util.vision.Limelight;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Controller driver = new Controller(0);
  private final Controller operator = new Controller(1);

  /* Driver Buttons */
  private final Button intakeBalls = driver.triggerRight;

  /* Operator Buttons */
  private final JoystickButton layupShot = operator.buttonA;
  private final JoystickButton midrangeShot = operator.buttonX;
  private final JoystickButton longShot = operator.buttonB;

  private final JoystickButton unjam = operator.bumperLeft;

  private final Limelight s_Limelight =
      new Limelight(
          Constants.Vision.limelightHeight,
          Constants.Vision.limelightAngle,
          Constants.Vision.goalHeight);
  private final ShotCalculator s_ShotGuesser =
      Shooter.unTuned ? new ShotTuner() : new ShotInterpolator(Shooter.tunedShots);

  /* Subsystems*/
  private final DrivetrainSubsystem s_Drive = new DrivetrainSubsystem();
  private final IntakeSubsystem s_Intake = new IntakeSubsystem();
  private final IndexerSubsystem s_Indexer = new IndexerSubsystem();
  private final TowerSubsystem s_Tower = new TowerSubsystem();
  private final TurretSubsystem s_Turret = new TurretSubsystem();
  private final ShooterSubsystem s_Shooter = new ShooterSubsystem(s_ShotGuesser, s_Limelight);

  private SendableChooser<Command> autonSelector = new SendableChooser<>();

  public RobotContainer() {
    s_Drive.setDefaultCommand(new ArcadeDrive(s_Drive, driver));
    configureButtonBindings();

    if (Turret.unTuned) SmartDashboard.putData("Turret PIDF", s_Turret);
    if (Shooter.unTuned) SmartDashboard.putData("Shooter PIDF", s_Shooter);

    /// ---- Autonomous Commands ---- ///
    autonSelector.setDefaultOption("Do Nothing", new InstantCommand());
  }

  private void configureButtonBindings() {
    /// ---- Driver Controls ---- ///
    intakeBalls.whenHeld(new IntakeBalls(s_Intake, s_Indexer, s_Tower));

    /// ---- Operator Controls ---- ///
    unjam.whenHeld(new Unjam(s_Indexer, s_Tower));
    layupShot.whenHeld(new ManualShoot(s_Shooter, 3700, 76));
    midrangeShot.whenHeld(new ManualShoot(s_Shooter, 4200, 58));
    longShot.whenHeld(new ManualShoot(s_Shooter, 5600, 50));
  }

  public Command getAutonomousCommand() {
    return autonSelector.getSelected();
  }
}
