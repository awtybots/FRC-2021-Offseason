package util;

public interface ShotCalculator {
  default double[] calculate(double distanceMeters) {
    return new double[] {calculateRPM(distanceMeters), calculateLaunchAngle(distanceMeters)};
  }

  double calculateLaunchAngle(double distanceMeters);

  double calculateRPM(double distanceMeters);
}
