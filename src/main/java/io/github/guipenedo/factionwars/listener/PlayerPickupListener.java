/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*    */ 
/*    */ public class PlayerPickupListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onPlayerDrop(PlayerPickupItemEvent paramPlayerPickupItemEvent) {
/* 12 */     if (MatchManager.get().getPlayerMap(paramPlayerPickupItemEvent.getPlayer()) != null)
/* 13 */       (MatchManager.get().getPlayerMap(paramPlayerPickupItemEvent.getPlayer())).remove.remove(paramPlayerPickupItemEvent.getItem()); 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerPickupListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */