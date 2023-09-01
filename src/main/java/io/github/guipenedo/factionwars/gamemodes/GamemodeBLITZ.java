/*     */ package io.github.guipenedo.factionwars.gamemodes;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.managers.ScoreboardManager;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ 
/*     */ public class GamemodeBLITZ
/*     */   implements GamemodeManager {
/*     */   private final String map;
/*  19 */   private int lives = 3;
/*     */   private boolean most_lives_wins = false;
/*  21 */   private Map<UUID, Integer> remaining = new HashMap<>();
/*     */   
/*     */   public GamemodeBLITZ(WarMap paramWarMap, ConfigurationSection paramConfigurationSection) {
/*  24 */     this.map = paramWarMap.getId();
/*  25 */     this.lives = paramConfigurationSection.getInt("gamemode-settings.lives", this.lives);
/*  26 */     this.most_lives_wins = paramConfigurationSection.getBoolean("gamemode-settings.most-lives-wins", this.most_lives_wins);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLocations(WarMap paramWarMap) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void startMatch() {
/*  36 */     this.remaining.clear();
/*  37 */     for (UUID uUID : WarMap.getMap(this.map).getPlayers())
/*  38 */       this.remaining.put(uUID, Integer.valueOf(this.lives)); 
/*     */   }
/*     */   
/*     */   private int getFactionLives(String paramString) {
/*  42 */     int i = 0;
/*  43 */     for (Player player : WarMap.getMap(this.map).getPlayerList()) {
/*  44 */       if (paramString.equals(FactionWars.getHandler().getPlayerTeam(player)))
/*  45 */         i += getLives(player.getUniqueId()); 
/*  46 */     }  return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void timeOut() {
/*  51 */     if (this.most_lives_wins)
/*  52 */     { WarMap warMap = WarMap.getMap(this.map);
/*  53 */       int i = getFactionLives(warMap.getF1()), j = getFactionLives(warMap.getF2());
/*  54 */       if (i > j) {
/*  55 */         MatchManager.get().won(warMap, warMap.getF1());
/*  56 */       } else if (j > i) {
/*  57 */         MatchManager.get().won(warMap, warMap.getF2());
/*     */       } else {
/*  59 */         MatchManager.get().tied(WarMap.getMap(this.map));
/*     */       }  }
/*  61 */     else { MatchManager.get().tied(WarMap.getMap(this.map)); }
/*     */   
/*     */   }
/*     */   public void updateScoreboard() {
/*  65 */     WarMap warMap = WarMap.getMap(this.map);
/*  66 */     int i = playersLeft(warMap.getF1()), j = playersLeft(warMap.getF2());
/*  67 */     for (Player player : warMap.getPlayerList()) {
/*  68 */       ScoreboardManager.get().display(player, warMap, new String[] { Util.getPlainMessage("gamemodes.blitz.scoreboard.players-left"), Util.getTeam1Color() + FactionWars.getHandler().getTeamName(warMap.getF1()), String.valueOf(i), Util.getTeam2Color() + FactionWars.getHandler().getTeamName(warMap.getF2()), String.valueOf(j), "", Util.getPlainMessage("gamemodes.blitz.scoreboard.your-lives"), getLives(player.getUniqueId()) + "/" + this.lives + " §d♥" });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill(UUID paramUUID) {}
/*     */   
/*     */   private int playersLeft(String paramString) {
/*  76 */     byte b = 0;
/*  77 */     WarMap warMap = WarMap.getMap(this.map);
/*  78 */     for (Player player : warMap.getPlayerList()) {
/*  79 */       if (getLives(player.getUniqueId()) > 0 && 
/*  80 */         paramString.equals(FactionWars.getHandler().getPlayerTeam(player)))
/*  81 */         b++; 
/*  82 */     }  return b;
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/*  86 */     WarMap warMap = WarMap.getMap(this.map);
/*  87 */     int i = playersLeft(warMap.getF1()), j = playersLeft(warMap.getF2());
/*  88 */     if (i == 0 && j > 0) {
/*  89 */       MatchManager.get().won(warMap, warMap.getF2());
/*  90 */     } else if (j == 0 && i > 0) {
/*  91 */       MatchManager.get().won(warMap, warMap.getF1());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void death(UUID paramUUID) {
/*  96 */     if (getLives(paramUUID) == 0)
/*  97 */       return;  this.remaining.put(paramUUID, Integer.valueOf(getLives(paramUUID) - 1));
/*     */     
/*  99 */     Player player = FactionWars.get().getServer().getPlayer(paramUUID);
/* 100 */     HashMap hashMap = Util.getVars(new Object[] { "left", Integer.valueOf(getLives(paramUUID)), "lives", Integer.valueOf(this.lives), "player", player.getName() });
/* 101 */     player.sendMessage(Util.getMessage("gamemodes.blitz.lost-life", hashMap));
/* 102 */     WarMap.getMap(this.map).message((getLives(player.getUniqueId()) > 0) ? Util.getMessage("gamemodes.blitz.broadcast", hashMap) : Util.getMessage("gamemodes.blitz.no-lives", hashMap));
/*     */     
/* 104 */     checkEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRespawn(UUID paramUUID) {
/* 114 */     return (getLives(paramUUID) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(PlayerMoveEvent paramPlayerMoveEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getLocations(WarMap paramWarMap) {
/* 124 */     return Collections.emptyMap();
/*     */   }
/*     */   
/*     */   private int getLives(UUID paramUUID) {
/* 128 */     return ((Integer)this.remaining.getOrDefault(paramUUID, Integer.valueOf(0))).intValue();
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeBLITZ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */