package frc.robot.subsystems;

import frc.robot.Constants.Field;
import frc.robot.Constants.Shooter;
import util.math.Interpolatable;
import util.math.InterpolatableMap;
import util.math.ProjectileMotionSimulation;
import util.math.ProjectileMotionSimulation.CommonProjectiles.Sphere;
import util.math.Shot;
import util.math.Vector2;

public interface AutoShootSolver {
  public Shot solve(Vector2 powerPortOffset);

  public class AutoShootProjectileMotionSolver implements AutoShootSolver {

    private final ProjectileMotionSimulation projectileMotionSimulation =
        new ProjectileMotionSimulation(
            Field.ballMass, Sphere.frontalArea(Field.ballRadius), Sphere.dragCoefficient);

    public AutoShootProjectileMotionSolver() {}

    @Override
    public Shot solve(Vector2 powerPortOffset) {
      final double goalLaunchAngle =
          Math.toDegrees(Math.atan2(powerPortOffset.x, powerPortOffset.y + 1.0));
      projectileMotionSimulation.setLaunchAngle(goalLaunchAngle);

      final double goalLaunchVelocity =
          projectileMotionSimulation.getOptimalLaunchVelocity(powerPortOffset);

      final double goalRPM =
          goalLaunchVelocity / (Shooter.flywheelRadius * 2.0 * Math.PI) * 60.0 * 2.0;

      return new Shot(powerPortOffset.x, goalRPM, goalLaunchAngle);
    }
  }

  public class AutoShootInterpolationSolver implements AutoShootSolver {

    private final InterpolatableMap<Shot> shotsMap = new InterpolatableMap<>();

    public AutoShootInterpolationSolver() {
      for (Shot entry : Shooter.autoShootInterpolationMap) {
        shotsMap.addKeyframe(entry.distanceMeters, Interpolatable.interpolatableShot(entry));
      }
    }

    @Override
    public Shot solve(Vector2 powerPortOffset) {
      return shotsMap.get(powerPortOffset.x);
    }
  }
}
