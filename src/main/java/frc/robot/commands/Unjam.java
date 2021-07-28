package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import static frc.robot.RobotContainer.*;

public class Unjam extends StartEndCommand {

  public Unjam() {
    super(() -> {
      s_Indexer.startForUnjamming();
      s_Tower.startForUnjamming();
    }, () -> {
      s_Indexer.stop();
      s_Tower.stop();
    }, s_Indexer, s_Tower);
  }
}
