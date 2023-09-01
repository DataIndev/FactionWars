/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsListCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsListCommand() {
/* 21 */     this.aliases.add("list");
/* 22 */     this.aliases.add("ls");
/* 23 */     this.aliases.add("l");
/* 24 */     this.helpText = Util.getMessage("commands.list");
/*    */     
/* 26 */     this.needsFaction = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 31 */     if (FactionData.getFaction(paramString) == null) {
/* 32 */       paramPlayer.sendMessage(Util.getMessage("error.no-faction"));
/*    */       
/*    */       return;
/*    */     } 
/* 36 */     ArrayList arrayList = PlayerSelection.getToFaction(paramString);
/* 37 */     if (arrayList.size() == 0) {
/* 38 */       paramPlayer.sendMessage(Util.getMessage("error.no-invites"));
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     for (PlayerSelection playerSelection : arrayList) {
/* 43 */       if (!playerSelection.isConfirmed())
/* 44 */         continue;  HashMap<Object, Object> hashMap = new HashMap<>();
/* 45 */       hashMap.put("from", FactionWars.getHandler().getTeamName(playerSelection.getFrom()));
/* 46 */       hashMap.put("gamemode", playerSelection.getGamemode().getName());
/* 47 */       paramPlayer.sendMessage(Util.getMessage("invite.from", hashMap));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsListCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */