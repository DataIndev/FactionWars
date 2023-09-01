/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsStatsCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsStatsCommand() {
/* 19 */     this.aliases.add("stats");
/* 20 */     this.aliases.add("s");
/*    */     
/* 22 */     this.helpText = Util.getMessage("commands.stats");
/* 23 */     this.optionalArgs.put("faction", "yours");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 29 */     if (!paramPlayer.hasPermission("factionwars.player.*") && !paramPlayer.hasPermission("factionwars.player.stats")) {
/* 30 */       paramPlayer.sendMessage(Util.getMessage("error.no-permission"));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 35 */     String str = paramString;
/* 36 */     if (paramVarArgs.length != 0) {
/* 37 */       str = Util.factionByString(paramVarArgs[0]);
/*    */     }
/* 39 */     if (str == null) {
/* 40 */       paramPlayer.sendMessage("§cFaction not found.");
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     FactionData factionData = FactionData.getFaction(str);
/*    */     
/* 46 */     if (factionData != null) {
/* 47 */       FactionWars.debug("Faction id: " + factionData.getFactionId());
/* 48 */       if (factionData.isInitialized()) {
/* 49 */         HashMap<Object, Object> hashMap = new HashMap<>();
/* 50 */         hashMap.put("name", factionData.getName());
/* 51 */         hashMap.put("rank", Integer.valueOf(factionData.getRank()));
/* 52 */         hashMap.put("wins", Integer.valueOf(factionData.getWins()));
/* 53 */         hashMap.put("losses", Integer.valueOf(factionData.getLosts()));
/* 54 */         hashMap.put("kills", Integer.valueOf(factionData.getKills()));
/* 55 */         hashMap.put("deaths", Integer.valueOf(factionData.getDeaths()));
/*    */         
/* 57 */         paramPlayer.sendMessage(Util.getMessage("command.stats.header"));
/* 58 */         paramPlayer.sendMessage(Util.getMessage("command.stats.line1", hashMap));
/* 59 */         paramPlayer.sendMessage(Util.getMessage("command.stats.line2", hashMap));
/* 60 */         paramPlayer.sendMessage(Util.getMessage("command.stats.line3", hashMap));
/* 61 */         paramPlayer.sendMessage(Util.getMessage("command.stats.line4", hashMap));
/* 62 */         paramPlayer.sendMessage(Util.getMessage("command.stats.footer"));
/*    */       } else {
/* 64 */         paramPlayer.sendMessage(Util.getMessage("command.stats.still-fetching"));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsStatsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */