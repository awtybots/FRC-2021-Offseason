package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.Constants.Field;
import frc.robot.Constants.Shooter;
import util.math.Vector2;
import util.vision.VisionTarget;
import util.vision.Limelight.LEDMode;

import static frc.robot.RobotContainer.*;

public class AutoShoot extends CommandBase {
  private final VisionTarget powerPortVisionTarget;

  public AutoShoot() {
    addRequirements(s_Indexer, s_Tower, s_Turret, s_Shooter);

    powerPortVisionTarget =
        new VisionTarget(s_Limelight, Field.powerPortVisionTargetHeight, Field.powerPortHeight);
  }

  @Override
  public void initialize() {
    s_Limelight.toggleDriverMode(false);
    s_Limelight.toggleLED(LEDMode.PipelineDefault);
  }

  @Override
  public void execute() {
    if (!s_Limelight.hasVisibleTarget()) return;

    s_Turret.rotateBy(s_Limelight.targetXOffset());

    Vector2 powerPortOffset = powerPortVisionTarget.getGoalDisplacement();
    SmartDashboard.putNumber("Power Port Perceived Distance", powerPortOffset.x);

    Pair<Double, Double> solutionValues = s_AutoShootSolver.solve(powerPortOffset);
    double goalLaunchVelocity = solutionValues.getFirst();
    double goalLaunchAngle = solutionValues.getSecond();

    boolean solution = !Double.isNaN(goalLaunchVelocity);
    SmartDashboard.putBoolean("Projectile Motion Solution", solution);
    if (!solution) return;

    double goalFlywheelRpm =
        goalLaunchVelocity / (Shooter.flywheelRadius * 2.0 * Math.PI) * 60.0 * 2.0;
    s_Shooter.setTarget(goalFlywheelRpm, goalLaunchAngle);

    boolean readyToShoot =
        s_Shooter.isFlywheelAtGoal() && s_Shooter.isHoodAtGoal() && s_Turret.isAtGoal();

    if (readyToShoot) {
      s_Tower.start();
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
    s_Limelight.toggleLED(LEDMode.Off);

    SmartDashboard.putNumber("Power Port Perceived Distance", -1.0);
    SmartDashboard.putBoolean("Projectile Motion Solution", false);
  }
}
