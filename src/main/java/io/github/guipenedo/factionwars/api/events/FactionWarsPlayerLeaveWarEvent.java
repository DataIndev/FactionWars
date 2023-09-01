/*    */ package io.github.guipenedo.factionwars.api.events;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactionWarsPlayerLeaveWarEvent
/*    */   extends Event
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final WarMap map;
/*    */   private final Player p;
/*    */   
/*    */   public WarMap getMap() {
/* 20 */     return this.map;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 26 */     return this.p;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FactionWarsPlayerLeaveWarEvent(Player paramPlayer, WarMap paramWarMap) {
/* 32 */     this.p = paramPlayer;
/* 33 */     this.map = paramWarMap;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public HandlerList getHandlers() {
/* 38 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 42 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\events\FactionWarsPlayerLeaveWarEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */