package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.TowerSubsystem;

public class Unjam extends StartEndCommand {

  public Unjam(IndexerSubsystem s_Indexer, TowerSubsystem s_Tower) {
    super(
        () -> {
          s_Indexer.unjam();
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
