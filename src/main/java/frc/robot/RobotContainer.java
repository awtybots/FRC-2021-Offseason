// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ToggleIntake;
import frc.robot.commands.ToggleTower;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TowerSubsystem;

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
  private final JoystickButton toggleIntake = new JoystickButton(driver, XboxController.Axis.kRightTrigger.value);
  
  /* Operator Buttons */
  private final JoystickButton layupShot = new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton midrangeShot = new JoystickButton(operator, XboxController.Button.kX.value);
  private final JoystickButton longShot = new JoystickButton(operator, XboxController.Button.kB.value);
  private final JoystickButton toggleTower = new JoystickButton(operator, XboxController.Button.kBumperRight.value);
  private final JoystickButton toggleIndexer = new JoystickButton(operator, XboxController.Axis.kLeftTrigger.value);
  private final JoystickButton reverseTower = new JoystickButton(operator, XboxController.Button.kBumperRight.value);
  
  
  
  /* Subsystems*/
  private final DrivetrainSubsystem m_drive = new DrivetrainSubsystem();
  private final IntakeSubsystem s_Intake = new IntakeSubsystem();
  private final TowerSubsystem s_Tower = new TowerSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_drive.setDefaultCommand(new DriveCommand(m_drive, driver, speedAxis, rotationAxis));

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    toggleIntake.whenHeld(new ToggleIntake(s_Intake));
    toggleTower.whenHeld(new ToggleTower(s_Tower, false));
    reverseTower.whenHeld(new ToggleTower(s_Tower, true));
    
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
