package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Field;
import frc.robot.subsystems.AutoShootSolver;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TowerSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import util.math.Shot;
import util.math.Vector2;
import util.vision.VisionTarget;

public class AutoShoot extends CommandBase {
  private final TowerSubsystem s_Tower;
  private final IndexerSubsystem s_Indexer;
  private final TurretSubsystem s_Turret;
  private final ShooterSubsystem s_Shooter;
  private final LimelightSubsystem s_Limelight;
  private final AutoShootSolver s_AutoShootSolver;

  private final VisionTarget powerPortVisionTarget;

  public AutoShoot(
      IndexerSubsystem s_Indexer,
      TowerSubsystem s_Tower,
      TurretSubsystem s_Turret,
      ShooterSubsystem s_Shooter,
      LimelightSubsystem s_Limelight,
      AutoShootSolver s_AutoShootSolver) {
    this.s_Indexer = s_Indexer;
    this.s_Tower = s_Tower;
    this.s_Turret = s_Turret;
    this.s_Shooter = s_Shooter;
    this.s_Limelight = s_Limelight;
    this.s_AutoShootSolver = s_AutoShootSolver;

    addRequirements(s_Indexer, s_Tower, s_Turret, s_Shooter);

    powerPortVisionTarget =
        new VisionTarget(s_Limelight, Field.powerPortVisionTargetHeight, Field.powerPortHeight);
  }

  @Override
  public void initialize() {
    s_Limelight.toggleDriverMode(false);
  }

  @Override
  public void execute() {
    if (!s_Limelight.hasVisibleTarget()) return; // ? change maybe

    s_Turret.rotateBy(s_Limelight.targetXOffset());

    final Vector2 powerPortOffset = powerPortVisionTarget.getGoalDisplacement();
    SmartDashboard.putNumber("Power Port Perceived Distance", powerPortOffset.x);

    final Shot solutionValues = s_AutoShootSolver.solve(powerPortOffset);
    final double goalFlywheelRpm = solutionValues.rpm;
    final double goalLaunchAngle = solutionValues.launchAngle;

    final boolean solution = !Double.isNaN(goalFlywheelRpm);
    SmartDashboard.putBoolean("Projectile Motion Solution", solution);
    if (!solution) return; // ? change maybe

    s_Shooter.setTarget(goalFlywheelRpm, goalLaunchAngle);

    final boolean readyToShoot =
        s_Shooter.isFlywheelAtGoal() && s_Shooter.isHoodAtGoal() && s_Turret.isAtGoal();

    if (readyToShoot) {
      s_Tower.startForShooting();
      s_Indexer.start();
    } else {
      s_Tower.stop();
      s_Indexer.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    s_Shooter.standby();
    s_Turret.returnToHome();
    s_Tower.stop();
    s_Indexer.stop();

    s_Limelight.toggleDriverMode(true);

    SmartDashboard.putNumber("Power Port Perceived Distance", -1.0);
    SmartDashboard.putBoolean("Projectile Motion Solution", false);
  }
}
