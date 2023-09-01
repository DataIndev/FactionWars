/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class PlayerMoveListener implements Listener {
/*    */   @EventHandler(ignoreCancelled = true)
/*    */   public void onPlayerMove(PlayerMoveEvent paramPlayerMoveEvent) {
/* 12 */     WarMap warMap = MatchManager.get().getPlayerMap(paramPlayerMoveEvent.getPlayer());
/* 13 */     if (warMap == null) {
/*    */       return;
/*    */     }
/* 16 */     if (warMap.getMatchState() == WarMap.MatchState.WAITINGSTART) {
/* 17 */       if (paramPlayerMoveEvent.getTo().getX() != paramPlayerMoveEvent.getFrom().getX() || paramPlayerMoveEvent.getTo().getZ() != paramPlayerMoveEvent.getFrom().getZ())
/* 18 */         paramPlayerMoveEvent.setTo(paramPlayerMoveEvent.getFrom()); 
/* 19 */     } else if (warMap.getMatchState() == WarMap.MatchState.PLAYING) {
/* 20 */       warMap.getManager().move(paramPlayerMoveEvent);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerMoveListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */