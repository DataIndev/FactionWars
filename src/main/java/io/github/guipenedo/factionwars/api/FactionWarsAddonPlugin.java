package io.github.guipenedo.factionwars.api;

import io.github.guipenedo.factionwars.gamemodes.GamemodeManager;
import io.github.guipenedo.factionwars.models.WarMap;
import org.bukkit.configuration.ConfigurationSection;

public interface FactionWarsAddonPlugin {
  TeamHandler getTeamHandler();
  
  GamemodeManager getGamemodeManager(String paramString, WarMap paramWarMap, ConfigurationSection paramConfigurationSection);
}


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\FactionWarsAddonPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */