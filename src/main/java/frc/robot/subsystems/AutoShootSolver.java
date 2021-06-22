package frc.robot.subsystems;

import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.Constants.Field;
import util.math.Interpolatable;
import util.math.InterpolationMap;
import util.math.ProjectileMotionSimulation;
import util.math.Vector2;
import util.math.ProjectileMotionSimulation.CommonProjectiles.Sphere;

public interface AutoShootSolver {
  public Pair<Double, Double> solve(Vector2 powerPortOffset);

  public class AutoShootProjectileMotionSolver implements AutoShootSolver {

    private final ProjectileMotionSimulation projectileMotionSimulation = new ProjectileMotionSimulation(Field.ballMass,
        Sphere.frontalArea(Field.ballRadius), Sphere.dragCoefficient);

    public AutoShootProjectileMotionSolver() {
    }

    @Override
    public Pair<Double, Double> solve(Vector2 powerPortOffset) {
      double goalLaunchAngle = Math.toDegrees(Math.atan2(powerPortOffset.x, powerPortOffset.y + 1.0));
      projectileMotionSimulation.setLaunchAngle(goalLaunchAngle);

      double goalLaunchVelocity = projectileMotionSimulation.getOptimalLaunchVelocity(powerPortOffset);

      return new Pair<Double, Double>(goalLaunchVelocity, goalLaunchAngle);
    }
  }

  public class AutoShootInterpolationSolver implements AutoShootSolver {

    private final InterpolationMap<Pair<Double, Double>> interpolationMap = new InterpolationMap<>();

    public AutoShootInterpolationSolver() {
      interpolationMap.addKeyframe(0.5, Interpolatable.interpolatableDoublePair(1.0, 1.0));
    }

    @Override
    public Pair<Double, Double> solve(Vector2 powerPortOffset) {
      return interpolationMap.get(powerPortOffset.x);
    }

  }
}
