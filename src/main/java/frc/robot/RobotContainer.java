// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.Shooter;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import util.controls.Controller;

public class RobotContainer {
  private final Controller controller1 = new Controller(0);
  private final Controller controller2 = new Controller(1);

  // subsystems
  public static final DrivetrainSubsystem s_Drivetrain = new DrivetrainSubsystem();
  public static final IntakeSubsystem s_Intake = new IntakeSubsystem();
  public static final IndexerSubsystem s_Indexer = new IndexerSubsystem();
  public static final TowerSubsystem s_Tower = new TowerSubsystem();
  public static final ShooterSubsystem s_Shooter = new ShooterSubsystem();
  public static final TurretSubsystem s_Turret = new TurretSubsystem();
  public static final LimelightSubsystem s_Limelight = new LimelightSubsystem();
  public static final AutoShootSolver s_AutoShootSolver =
      new AutoShootSolver.AutoShootInterpolationSolver(Shooter.autoShootInterpolationMap);

  private SendableChooser<Command> autonSelector = new SendableChooser<>();

  public RobotContainer() {}

  public void autonSetup() {
    autonSelector.setDefaultOption("Do Nothing", new InstantCommand());
    // TODO ...
  }

  public Command getAutonomousCommand() {
    return autonSelector.getSelected();
  }

  public void bindIO() {
    s_Drivetrain.setDefaultCommand(new TeleopDrive(controller1));

    // driver
    controller1.bumperRight.whenHeld(new LowerAndRunIntake());
    controller1.bumperLeft.whenHeld(new RaiseIntake());

    // operator
    controller2.buttonBack.whenHeld(new TestCommand());
    controller2.buttonStart.whenHeld(new AutoAim());

    controller2.bumperLeft.whenHeld(new Unjam());
    controller2.bumperRight.whenHeld(new RunIndexerTower());

    controller2.buttonA.whenHeld(new ManualShoot(3700, 76));
    controller2.buttonX.whenHeld(new ManualShoot(4200, 66));
    controller2.buttonB.whenHeld(new ManualShoot(5400, 56));
    controller2.buttonY.whenHeld(new AutoShoot());
  }
}
