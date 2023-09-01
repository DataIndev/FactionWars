package io.github.guipenedo.factionwars.gamemodes;

import io.github.guipenedo.factionwars.models.WarMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.event.player.PlayerMoveEvent;

public interface GamemodeManager {
  void updateLocations(WarMap paramWarMap);
  
  void startMatch();
  
  void timeOut();
  
  void updateScoreboard();
  
  void kill(UUID paramUUID);
  
  void death(UUID paramUUID);
  
  void reset();
  
  boolean shouldRespawn(UUID paramUUID);
  
  void move(PlayerMoveEvent paramPlayerMoveEvent);
  
  Map<String, String> getLocations(WarMap paramWarMap);
}


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */