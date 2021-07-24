// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunTower;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TowerSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final XboxController driverController = new XboxController(0);

  private final JoystickButton runTowerUp =
      new JoystickButton(driverController, XboxController.Button.kA.value);
  private final JoystickButton runTowerDown =
      new JoystickButton(driverController, XboxController.Button.kB.value);

  private final JoystickButton runIntakeIn =
      new JoystickButton(driverController, XboxController.Button.kX.value);
  private final JoystickButton runIntakeOut =
      new JoystickButton(driverController, XboxController.Button.kY.value);
  private final JoystickButton liftIntake =
      new JoystickButton(
          driverController, XboxController.Button.kStart.value); // TODO Change these buttons
  private final JoystickButton lowerIntake =
      new JoystickButton(
          driverController, XboxController.Button.kBumperLeft.value); // TODO Change these buttons

  /// --- Subsystems --- ///
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final IndexerSubsystem indexerSubsystem = new IndexerSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final TowerSubsystem towerSubsystem = new TowerSubsystem();

  public RobotContainer() {
    drivetrainSubsystem.setDefaultCommand(new ArcadeDrive(drivetrainSubsystem, driverController));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    runTowerUp.whenHeld(new RunTower(towerSubsystem, 1, 1));
    runTowerDown.whenHeld(new RunTower(towerSubsystem, -1, -1));

    runIntakeIn.whenHeld(new RunIntake(intakeSubsystem, 1));
    runIntakeOut.whenHeld(new RunIntake(intakeSubsystem, 1));

    // liftIntake.whenHeld(new LiftIntake(true)); // TODO create these commands
    // lowerIntake.whenHeld(new LiftIntake(false)); // TODO create these commands
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
