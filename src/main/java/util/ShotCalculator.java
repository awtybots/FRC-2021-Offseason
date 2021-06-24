package util;

public interface ShotCalculator {
  double[] calculate(double distanceMeters);

  double calculateLaunchAngle(double distanceMeters);

  double calculateRPM(double distanceMeters);
}
