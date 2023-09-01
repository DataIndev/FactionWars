/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class WarsTopCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsTopCommand() {
/* 16 */     this.aliases.add("top");
/* 17 */     this.aliases.add("leaderboard");
/*    */     
/* 19 */     this.helpText = Util.getMessage("commands.top");
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 24 */     if (!paramPlayer.hasPermission("factionwars.player.*") && !paramPlayer.hasPermission("factionwars.player.top")) {
/* 25 */       paramPlayer.sendMessage(Util.getMessage("error.no-permission"));
/*    */       
/*    */       return;
/*    */     } 
/* 29 */     paramPlayer.sendMessage(Util.getMessage("command.top.header"));
/* 30 */     List<FactionData> list = FactionData.getTop(5);
/* 31 */     paramPlayer.sendMessage(Util.getMessage("command.top.header2"));
/* 32 */     if (list.size() == 0)
/* 33 */       paramPlayer.sendMessage(Util.getMessage("command.top.nonefound")); 
/* 34 */     for (byte b = 0; b < list.size(); b++) {
/* 35 */       FactionData factionData = list.get(b);
/* 36 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 37 */       hashMap.put("rank", Integer.valueOf(b + 1));
/* 38 */       hashMap.put("name", factionData.getName());
/* 39 */       hashMap.put("wins", Integer.valueOf(factionData.getWins()));
/* 40 */       hashMap.put("losses", Integer.valueOf(factionData.getLosts()));
/* 41 */       hashMap.put("kills", Integer.valueOf(factionData.getKills()));
/* 42 */       hashMap.put("deaths", Integer.valueOf(factionData.getDeaths()));
/* 43 */       FactionWars.debug("top f: " + factionData.getFactionId());
/* 44 */       paramPlayer.sendMessage(Util.getMessage("command.top.faction", hashMap));
/*    */     } 
/* 46 */     paramPlayer.sendMessage(Util.getMessage("command.top.footer"));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsTopCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */