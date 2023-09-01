/*    */ package io.github.guipenedo.factionwars.gamemodes;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.managers.ScoreboardManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class GamemodeTDM implements GamemodeManager {
/*    */   private final String map;
/*    */   private int score1;
/*    */   private int score2;
/*    */   
/*    */   public GamemodeTDM(WarMap paramWarMap, ConfigurationSection paramConfigurationSection) {
/* 20 */     this.map = paramWarMap.getId();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLocations(WarMap paramWarMap) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void startMatch() {
/* 30 */     this.score1 = 0;
/* 31 */     this.score2 = 0;
/* 32 */     updateScoreboard();
/*    */   }
/*    */   
/*    */   public void updateScoreboard() {
/* 36 */     WarMap warMap = WarMap.getMap(this.map);
/* 37 */     ScoreboardManager.get().display(warMap.getPlayerList(), warMap, new String[] { "§6" + ((this.score1 > this.score2) ? "★ " : "") + Util.getTeam1Color() + FactionWars.getHandler().getTeamName(warMap.getF1()), 
/* 38 */           String.valueOf(this.score1), "§6" + ((this.score2 > this.score1) ? "★ " : "") + Util.getTeam2Color() + FactionWars.getHandler().getTeamName(warMap.getF2()), String.valueOf(this.score2) });
/*    */   }
/*    */ 
/*    */   
/*    */   public void timeOut() {
/* 43 */     WarMap warMap = WarMap.getMap(this.map);
/* 44 */     if (this.score1 > this.score2) {
/* 45 */       MatchManager.get().won(warMap, warMap.getF1());
/* 46 */     } else if (this.score1 < this.score2) {
/* 47 */       MatchManager.get().won(warMap, warMap.getF2());
/*    */     } else {
/* 49 */       MatchManager.get().tied(warMap);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void kill(UUID paramUUID) {
/* 54 */     if (WarMap.getMap(this.map).getF1().equals(FactionWars.getHandler().getPlayerTeam(FactionWars.get().getServer().getPlayer(paramUUID)))) {
/* 55 */       this.score1++;
/*    */     } else {
/* 57 */       this.score2++;
/* 58 */     }  updateScoreboard();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void death(UUID paramUUID) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRespawn(UUID paramUUID) {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void move(PlayerMoveEvent paramPlayerMoveEvent) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, String> getLocations(WarMap paramWarMap) {
/* 82 */     return Collections.emptyMap();
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeTDM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */