/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.menus.KitSelector;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class PlayerInteractListener
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onClick(PlayerInteractEvent paramPlayerInteractEvent) {
/* 17 */     WarMap warMap = MatchManager.get().getPlayerMap(paramPlayerInteractEvent.getPlayer());
/* 18 */     if ((paramPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || paramPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) && paramPlayerInteractEvent.hasItem() && paramPlayerInteractEvent.getItem().getType() == Material.ENDER_PEARL) {
/* 19 */       FactionWars.debug("enderpearl");
/* 20 */       if (warMap != null) {
/* 21 */         FactionWars.debug("map not null");
/* 22 */         if (warMap.getMatchState() == WarMap.MatchState.WAITINGTP) {
/* 23 */           FactionWars.debug("waitingtp");
/* 24 */           paramPlayerInteractEvent.setCancelled(true);
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/* 29 */     if (paramPlayerInteractEvent.hasItem() && paramPlayerInteractEvent.getMaterial() == Material.STICK && paramPlayerInteractEvent.getItem().hasItemMeta() && paramPlayerInteractEvent.getItem().getItemMeta().hasDisplayName() && paramPlayerInteractEvent.getItem().getItemMeta().getDisplayName().equals("§a§lKit Selector") && 
/* 30 */       warMap != null && warMap.getMatchState() == WarMap.MatchState.WAITINGSTART) {
/* 31 */       KitSelector kitSelector = new KitSelector(paramPlayerInteractEvent.getPlayer());
/* 32 */       kitSelector.open();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerInteractListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */