package util.vision;

public interface Camera {
  public double getMountHeight();

  public double getMountAngle();

  public boolean hasVisibleTarget();

  public double targetXOffset();

  public double targetYOffset();
}
