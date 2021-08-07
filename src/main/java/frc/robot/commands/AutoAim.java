package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoAim extends CommandBase {
  public AutoAim() {
    addRequirements(s_Turret, s_Limelight);
  }

  @Override
  public void initialize() {
    s_Limelight.toggleForPowerPort(true);
  }

  @Override
  public void execute() {
    if (!s_Limelight.hasVisibleTarget()) return;

    s_Turret.rotateBy(s_Limelight.targetXOffset());
  }

  @Override
  public void end(boolean interrupted) {
    s_Turret.returnToHome();
    s_Limelight.toggleForPowerPort(false);
  }
}
