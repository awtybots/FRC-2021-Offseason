package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import util.vision.Limelight;

public class LimelightSubsystem extends Limelight {
  public LimelightSubsystem() {
    super(Constants.Limelight.mountingHeight, Constants.Limelight.mountingAngle);
  }

  @Override
  public void periodic() {
    if(Robot.instance.isDisabled()) {
      this.toggleLED(Limelight.LEDMode.Off);
      this.toggleDriverMode(true);
    }
  }

  public void toggleForPowerPort(boolean on) {
    if(on) {
      toggleDriverMode(false);
      setPipeline(Constants.Limelight.powerPortPipeline);
      toggleLED(LEDMode.PipelineDefault);
    } else {
      toggleDriverMode(true);
      toggleLED(LEDMode.Off);
    }
  }
}
