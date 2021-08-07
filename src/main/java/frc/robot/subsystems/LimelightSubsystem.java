package frc.robot.subsystems;

import frc.robot.Constants;
import util.vision.Limelight;

public class LimelightSubsystem extends Limelight {
  public LimelightSubsystem() {
    super(Constants.Limelight.mountingHeight, Constants.Limelight.mountingAngle);
    toggleForPowerPort(false);
  }

  public void toggleForPowerPort(boolean on) {
    if (on) {
      setPipeline(Constants.Limelight.powerPortPipeline);
    } else {
      setPipeline(Constants.Limelight.driverPipeline);
    }
  }
}
