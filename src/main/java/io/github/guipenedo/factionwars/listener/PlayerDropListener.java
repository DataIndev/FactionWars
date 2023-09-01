/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerDropItemEvent;
/*    */ 
/*    */ public class PlayerDropListener implements Listener {
/*    */   @EventHandler
/*    */   public void onPlayerDrop(PlayerDropItemEvent paramPlayerDropItemEvent) {
/* 11 */     if (MatchManager.get().getPlayerMap(paramPlayerDropItemEvent.getPlayer()) != null)
/* 12 */       (MatchManager.get().getPlayerMap(paramPlayerDropItemEvent.getPlayer())).remove.add(paramPlayerDropItemEvent.getItemDrop()); 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerDropListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */