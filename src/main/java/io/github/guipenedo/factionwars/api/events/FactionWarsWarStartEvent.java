/*    */ package io.github.guipenedo.factionwars.api.events;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactionWarsWarStartEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 18 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private boolean cancelled = false;
/*    */   
/*    */   private final PlayerSelection s1;
/*    */   private final PlayerSelection s2;
/*    */   private final WarMap map;
/*    */   
/*    */   public WarMap getMap() {
/* 27 */     return this.map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Player> getTeam2Players() {
/* 34 */     return this.s2.getAllPlayersSelected();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Player> getTeam1Players() {
/* 41 */     return this.s1.getAllPlayersSelected();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTeam1() {
/* 48 */     return this.s1.getFrom();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTeam2() {
/* 55 */     return this.s2.getFrom();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FactionWarsWarStartEvent(PlayerSelection paramPlayerSelection1, PlayerSelection paramPlayerSelection2, WarMap paramWarMap) {
/* 61 */     this.s1 = paramPlayerSelection1;
/* 62 */     this.s2 = paramPlayerSelection2;
/* 63 */     this.map = paramWarMap;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 67 */     return this.cancelled;
/*    */   }
/*    */   public void setCancelled(boolean paramBoolean) {
/* 70 */     this.cancelled = paramBoolean;
/*    */   }
/*    */   @NotNull
/*    */   public HandlerList getHandlers() {
/* 74 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 78 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\events\FactionWarsWarStartEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */