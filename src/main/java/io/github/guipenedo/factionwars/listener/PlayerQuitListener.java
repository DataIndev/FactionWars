/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.HologramManager;
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.models.PlayerData;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class PlayerQuitListener implements Listener {
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
/* 17 */     PlayerData.getOptedOut().remove(paramPlayerQuitEvent.getPlayer().getUniqueId());
/*    */ 
/*    */     
/* 20 */     PlayerSelection playerSelection = PlayerSelection.getBySelector(paramPlayerQuitEvent.getPlayer().getUniqueId());
/* 21 */     if (playerSelection != null && !playerSelection.isConfirmed()) {
/* 22 */       PlayerSelection.getPlayerSelections().remove(playerSelection);
/*    */     }
/*    */     
/* 25 */     WarMap warMap = MatchManager.get().getPlayerMap(paramPlayerQuitEvent.getPlayer());
/* 26 */     if (warMap == null) {
/*    */       return;
/*    */     }
/* 29 */     MatchManager.get().playerLeave(paramPlayerQuitEvent.getPlayer(), true);
/* 30 */     warMap.getPlayers().remove(paramPlayerQuitEvent.getPlayer().getUniqueId());
/* 31 */     MatchManager.get().checkEnd(warMap);
/* 32 */     final String factionId = FactionWars.getHandler().getPlayerTeam(paramPlayerQuitEvent.getPlayer());
/* 33 */     if (HologramManager.get().isEnabled()) {
/* 34 */       HologramManager.get().cleanUp(paramPlayerQuitEvent.getPlayer());
/* 35 */       (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 38 */             if (factionId != null && FactionWars.getHandler().getOnlinePlayers(factionId).size() == 0)
/* 39 */               HologramManager.get().delete(factionId); 
/*    */           }
/* 41 */         }).runTaskLater((Plugin)FactionWars.get(), 1000L);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerQuitListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */