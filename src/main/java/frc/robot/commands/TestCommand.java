package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.RobotContainer.*;

public class TestCommand extends CommandBase {
  @Override
  public void initialize() {
    // s_Shooter.setHood(-0.2);
  }

  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    // s_Shooter.setHood(0.0);
  }
}
