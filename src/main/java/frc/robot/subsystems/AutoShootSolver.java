package frc.robot.subsystems;

import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.Constants.Field;
import frc.robot.Constants.Shooter;
import util.math.Interpolatable;
import util.math.InterpolatableMap;
import util.math.ProjectileMotionSimulation;
import util.math.ProjectileMotionSimulation.CommonProjectiles.Sphere;
import util.math.Vector2;

public interface AutoShootSolver {
  public Pair<Double, Double> solve(Vector2 powerPortOffset);

  public class AutoShootProjectileMotionSolver implements AutoShootSolver {

    private final ProjectileMotionSimulation projectileMotionSimulation =
        new ProjectileMotionSimulation(
            Field.ballMass, Sphere.frontalArea(Field.ballRadius), Sphere.dragCoefficient);

    public AutoShootProjectileMotionSolver() {}

    @Override
    public Pair<Double, Double> solve(Vector2 powerPortOffset) {
      double goalLaunchAngle =
          Math.toDegrees(Math.atan2(powerPortOffset.x, powerPortOffset.y + 1.0));
      projectileMotionSimulation.setLaunchAngle(goalLaunchAngle);

      double goalLaunchVelocity =
          projectileMotionSimulation.getOptimalLaunchVelocity(powerPortOffset);

      return new Pair<Double, Double>(goalLaunchVelocity, goalLaunchAngle);
    }
  }

  public class AutoShootInterpolationSolver implements AutoShootSolver {

    private final InterpolatableMap<Pair<Double, Double>> shotsMap =
        new InterpolatableMap<>();

    public AutoShootInterpolationSolver() {
      for (double[] entry : Shooter.autoShootInterpolationMap) {
        shotsMap.addKeyframe(
            entry[0], Interpolatable.interpolatableDoublePair(entry[1], entry[2]));
      }
    }

    @Override
    public Pair<Double, Double> solve(Vector2 powerPortOffset) {
      return shotsMap.get(powerPortOffset.x);
    }
  }
}
