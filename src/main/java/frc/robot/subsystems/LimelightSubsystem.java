package frc.robot.subsystems;

import frc.robot.Constants;
import util.vision.Limelight;

public class LimelightSubsystem extends Limelight {
  public LimelightSubsystem(double mountingHeight, double mountingAngle) {
    super(Constants.Limelight.mountingHeight, Constants.Limelight.mountingAngle);

    toggleDriverMode(true);
  }
}
