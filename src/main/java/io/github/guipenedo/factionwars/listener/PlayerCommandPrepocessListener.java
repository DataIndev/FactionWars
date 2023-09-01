/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import java.util.List;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*    */ 
/*    */ public class PlayerCommandPrepocessListener
/*    */   implements Listener {
/*    */   public static List<String> enabledCommands;
/*    */   
/*    */   @EventHandler
/*    */   public void onCommandPrepocess(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent) {
/* 15 */     if (MatchManager.get().getPlayerMap(paramPlayerCommandPreprocessEvent.getPlayer()) == null)
/*    */       return; 
/* 17 */     if (enabledCommands != null) {
/* 18 */       for (String str : enabledCommands) {
/* 19 */         if (paramPlayerCommandPreprocessEvent.getMessage().startsWith((!str.isEmpty() && str.charAt(0) == '/') ? str : ('/' + str)))
/*    */           return; 
/* 21 */       }  paramPlayerCommandPreprocessEvent.setCancelled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerCommandPrepocessListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */