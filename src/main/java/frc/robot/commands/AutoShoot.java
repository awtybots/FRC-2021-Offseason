package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.Constants.Field;
import frc.robot.Constants.Shooter;
import util.math.Vector2;
import util.vision.VisionTarget;

public class AutoShoot extends CommandBase {
  private final VisionTarget powerPortVisionTarget;

  public AutoShoot() {
    addRequirements(s_Indexer, s_Tower, s_Turret, s_Shooter, s_Limelight);

    powerPortVisionTarget =
        new VisionTarget(s_Limelight, Field.powerPortVisionTargetHeight, Field.powerPortHeight);
  }

  @Override
  public void initialize() {
    s_Limelight.toggleForPowerPort(true);
  }

  @Override
  public void execute() {
    // * Fail if can't see goal *//
    if (!s_Limelight.hasTarget()) return;

    s_Turret.rotateBy(s_Limelight.targetXOffset());

    Vector2 powerPortOffset = powerPortVisionTarget.getGoalDisplacement();
    SmartDashboard.putNumber("Power Port Perceived Distance", powerPortOffset.x);

    Pair<Double, Double> solutionValues = s_AutoShootSolver.solve(powerPortOffset);
    double goalLaunchVelocity = solutionValues.getFirst();
    double goalLaunchAngle = solutionValues.getSecond();

    boolean hasSolution = !Double.isNaN(goalLaunchVelocity);
    SmartDashboard.putBoolean("Projectile Motion Solution", hasSolution);
    if (!hasSolution) return;

    double goalFlywheelRpm = Shooter.launchVelocityToFlywheelRPM(goalLaunchVelocity);
    s_Shooter.setFlywheelRPM(goalFlywheelRpm);
    s_Shooter.setLaunchAngle(goalLaunchAngle);

    boolean readyToShoot = s_Shooter.atTarget() && s_Turret.atGoal();

    if (readyToShoot) {
      s_Tower.startForShooting();
      s_Indexer.startForShooting();
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

    s_Limelight.toggleForPowerPort(false);

    SmartDashboard.putNumber("Power Port Perceived Distance", -1.0);
    SmartDashboard.putBoolean("Projectile Motion Solution", false);
  }
}
