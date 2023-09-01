/*    */ package io.github.guipenedo.factionwars.api.events;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactionWarsWarEndEvent
/*    */   extends Event
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final WarMap map;
/*    */   private final String winner;
/*    */   
/*    */   public WarMap getMap() {
/* 19 */     return this.map;
/*    */   }
/*    */   private final String loser;
/*    */   private final boolean tied;
/*    */   
/*    */   public String getWinner() {
/* 25 */     return this.winner;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLoser() {
/* 30 */     return this.loser;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTied() {
/* 35 */     return this.tied;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FactionWarsWarEndEvent(WarMap paramWarMap, String paramString1, String paramString2, boolean paramBoolean) {
/* 42 */     this.winner = paramString1;
/* 43 */     this.loser = paramString2;
/* 44 */     this.tied = paramBoolean;
/* 45 */     this.map = paramWarMap;
/*    */   }
/*    */   
/*    */   public FactionWarsWarEndEvent(WarMap paramWarMap, String paramString1, String paramString2) {
/* 49 */     this(paramWarMap, paramString1, paramString2, false);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public HandlerList getHandlers() {
/* 54 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 58 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\events\FactionWarsWarEndEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */