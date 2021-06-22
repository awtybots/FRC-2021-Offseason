package util.vision;

import util.math.Vector2;

public class VisionTarget {
  private final Camera camera;
  private final double visionTargetY;
  private final double goalY;

  public VisionTarget(Camera camera, double visionTargetHeight, double goalHeight) {
    this.camera = camera;
    this.visionTargetY = visionTargetHeight;
    this.goalY = goalHeight;
  }

  /**
   * Get the relative displacement vector from the camera to the goal. The x axis is horizontal
   * displacement and the y axis is vertical displacement. Values are in meters.
   *
   * @return The displacement vector, or null if no vision target is detected.
   */
  public Vector2 getGoalDisplacement() {
    if (!camera.hasVisibleTarget()) return null;

    double angleY = camera.targetYOffset();

    double opposite = visionTargetY - camera.getMountHeight();
    double tangent = Math.tan(Math.toRadians(camera.getMountAngle() + angleY));
    double adjacent = opposite / tangent;

    return new Vector2(adjacent, goalY - camera.getMountHeight());
  }
}
