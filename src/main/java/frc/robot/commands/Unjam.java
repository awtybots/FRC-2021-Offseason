package frc.robot.commands;

import static frc.robot.RobotContainer.*;

import edu.wpi.first.wpilibj2.command.StartEndCommand;

public class Unjam extends StartEndCommand {

  public Unjam() {
    super(
        () -> {
          s_Indexer.startForUnjamming();
          s_Tower.startForUnjamming();
        },
        () -> {
          s_Indexer.stop();
          s_Tower.stop();
        },
        s_Indexer,
        s_Tower);
  }
}
