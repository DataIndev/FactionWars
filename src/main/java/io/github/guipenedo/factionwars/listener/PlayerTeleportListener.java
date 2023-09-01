/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.PlayerData;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ 
/*    */ public class PlayerTeleportListener
/*    */   implements Listener {
/*    */   @EventHandler(priority = EventPriority.MONITOR)
/*    */   public void onPlayerTeleport(PlayerTeleportEvent paramPlayerTeleportEvent) {
/* 13 */     if (PlayerData.getPlayerData(paramPlayerTeleportEvent.getPlayer().getUniqueId()) != null && paramPlayerTeleportEvent.isCancelled())
/* 14 */       paramPlayerTeleportEvent.setCancelled(false); 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerTeleportListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */