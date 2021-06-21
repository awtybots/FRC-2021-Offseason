// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

  /* Driver Controls */
  private final int speedAxis = XboxController.Axis.kLeftY.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton intakeBalls =
      new JoystickButton(driver, XboxController.Axis.kRightTrigger.value);

  /* Operator Buttons */
  private final JoystickButton layupShot =
      new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton midrangeShot =
      new JoystickButton(operator, XboxController.Button.kX.value);
  private final JoystickButton longShot =
      new JoystickButton(operator, XboxController.Button.kB.value);

  private final JoystickButton unjam =
      new JoystickButton(operator, XboxController.Button.kBumperLeft.value);

  /* Subsystems*/
  private final DrivetrainSubsystem s_Drive = new DrivetrainSubsystem();
  private final IntakeSubsystem s_Intake = new IntakeSubsystem();
  private final IndexerSubsystem s_Indexer = new IndexerSubsystem();
  private final TowerSubsystem s_Tower = new TowerSubsystem();
  private final ShooterSubsystem s_Shooter = new ShooterSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    s_Drive.setDefaultCommand(new ArcadeDrive(s_Drive, driver, speedAxis, rotationAxis));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    intakeBalls.whenHeld(new IntakeBalls(s_Intake, s_Indexer, s_Tower));

    unjam.whenHeld(new Unjam(s_Indexer, s_Tower));
    layupShot.whenHeld(new ManualShoot(s_Shooter, 3700, 76));
    midrangeShot.whenHeld(new ManualShoot(s_Shooter, 4200, 58));
    longShot.whenHeld(new ManualShoot(s_Shooter, 5600, 50));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
