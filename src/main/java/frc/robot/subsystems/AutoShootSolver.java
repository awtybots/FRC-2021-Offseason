package frc.robot.subsystems;

import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.Constants.Field;
import frc.robot.Constants.Shooter;
import util.math.Interpolatable;
import util.math.InterpolationMap;
import util.math.ProjectileMotionSimulation;
import util.math.ProjectileMotionSimulation.CommonProjectiles.Sphere;
import util.math.Vector2;

public interface AutoShootSolver {
  /**
   * Solve for the launch velocity (m/s) and launch angle (deg) from a given horizontal and vertical
   * displacement from the target.
   *
   * @param targetOffset 2D Displacement Vector from goal
   * @return Pair<launch_velocity,launch_angle_deg>
   */
  public Pair<Double, Double> solve(Vector2 targetOffset);

  public class AutoShootProjectileMotionSolver implements AutoShootSolver {
    private final ProjectileMotionSimulation projectileMotionSimulation =
        new ProjectileMotionSimulation(
            Field.ballMass, Sphere.frontalArea(Field.ballRadius), Sphere.dragCoefficient);

    public AutoShootProjectileMotionSolver() {}

    @Override
    public Pair<Double, Double> solve(Vector2 targetOffset) {
      double goalLaunchAngle = Math.toDegrees(Math.atan2(targetOffset.x, targetOffset.y + 1.0));
      projectileMotionSimulation.setLaunchAngle(goalLaunchAngle);

      double goalLaunchVelocity = projectileMotionSimulation.getOptimalLaunchVelocity(targetOffset);

      return new Pair<Double, Double>(goalLaunchVelocity, goalLaunchAngle);
    }
  }

  public class AutoShootInterpolationSolver implements AutoShootSolver {
    private final InterpolationMap<Pair<Double, Double>> interpolationMap =
        new InterpolationMap<>();

    /**
     * @param shotMap an array of double arrays with the layout of <code>
     *     [distance, rpm, launchAngle]</code>
     */
    public AutoShootInterpolationSolver(double[][] shotMap) {
      for (double[] entry : Shooter.autoShootInterpolationMap) {
        interpolationMap.addKeyframe(
            entry[0], Interpolatable.interpolatableDoublePair(entry[1], entry[2]));
      }
    }

    @Override
    public Pair<Double, Double> solve(Vector2 targetOffset) {
      return interpolationMap.get(targetOffset.x);
    }
  }
}
