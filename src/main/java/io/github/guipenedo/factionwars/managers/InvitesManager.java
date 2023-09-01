/*    */ package io.github.guipenedo.factionwars.managers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class InvitesManager {
/*    */   private static InvitesManager instance;
/*    */   
/*    */   public static InvitesManager get() {
/* 15 */     if (instance == null)
/* 16 */       instance = new InvitesManager(); 
/* 17 */     return instance;
/*    */   }
/*    */   
/*    */   public void confirmInvite(final String senderFaction, final String faction) {
/* 21 */     PlayerSelection playerSelection = PlayerSelection.getByFactions(senderFaction, faction);
/* 22 */     if (playerSelection == null) {
/*    */       return;
/*    */     }
/* 25 */     final HashMap<Object, Object> variables = new HashMap<>();
/* 26 */     hashMap.put("senderfaction", FactionWars.getHandler().getTeamName(senderFaction));
/* 27 */     hashMap.put("targetfaction", FactionWars.getHandler().getTeamName(faction));
/* 28 */     hashMap.put("gamemode", playerSelection.getGamemode().getName());
/* 29 */     hashMap.put("bet", playerSelection.getBet());
/* 30 */     FactionWars.getHandler().sendMessage(senderFaction, Util.getMessage("invite.self", hashMap));
/* 31 */     FactionWars.getHandler().sendMessage(faction, Util.getMessage("invite.target", hashMap));
/*    */     
/* 33 */     for (Player player : Util.getLeaders(faction)) {
/* 34 */       player.sendMessage(Util.getMessage("invite.target-leader-accept", hashMap));
/* 35 */       player.sendMessage(Util.getMessage("invite.target-leader-expire", hashMap));
/*    */     } 
/*    */     
/* 38 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run()
/*    */         {
/* 42 */           PlayerSelection playerSelection = PlayerSelection.getByFactions(senderFaction, faction);
/*    */           
/* 44 */           if (playerSelection != null && playerSelection.isConfirmed() && PlayerSelection.getByFactions(faction, senderFaction) == null) {
/* 45 */             PlayerSelection.getPlayerSelections().remove(playerSelection);
/*    */             
/* 47 */             FactionWars.getHandler().sendMessage(senderFaction, Util.getMessage("invite.expire-self", variables));
/* 48 */             FactionWars.getHandler().sendMessage(faction, Util.getMessage("invite.expire-target", variables));
/*    */           } 
/*    */         }
/* 51 */       }).runTaskLater((Plugin)FactionWars.get(), (FactionWars.getMainConfig().getConfig().getInt("invite-expire-time") * 20));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\InvitesManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */