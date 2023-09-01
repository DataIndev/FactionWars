/*    */ package io.github.guipenedo.factionwars.commands.admin;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import io.github.guipenedo.factionwars.stats.DatabaseHelper;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ class StatsCommand {
/* 14 */   private final List<String> allowed = Arrays.asList(new String[] { "kills", "deaths", "wins", "losts" });
/*    */   
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 17 */     if (!paramCommandSender.hasPermission("factionwars.admin.stats") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 18 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 19 */       return false;
/*    */     } 
/*    */     
/* 22 */     if (paramArrayOfString.length != 3 || !this.allowed.contains(paramArrayOfString[1])) {
/* 23 */       paramCommandSender.sendMessage("§cUsage: /fw stats <team> <kills/deaths/wins/losts> <offset>");
/* 24 */       return false;
/*    */     } 
/*    */     
/* 27 */     String str = FactionWars.getHandler().getTeamIdByName(paramArrayOfString[0]);
/*    */     
/* 29 */     if (str == null) {
/* 30 */       paramCommandSender.sendMessage("§cInvalid faction(s)");
/* 31 */       return false;
/*    */     } 
/*    */     
/* 34 */     if (!NumberUtils.isNumber(paramArrayOfString[2])) {
/* 35 */       paramCommandSender.sendMessage("§cOffset must be an integer");
/* 36 */       return false;
/*    */     } 
/*    */     
/* 39 */     final FactionData factionData = FactionData.getFaction(str);
/* 40 */     if (factionData != null)
/* 41 */     { int i = Integer.parseInt(paramArrayOfString[2]);
/* 42 */       if ("deaths".equals(paramArrayOfString[1])) {
/* 43 */         factionData.setDeaths(Math.max(0, factionData.getDeaths() + i));
/* 44 */       } else if ("kills".equals(paramArrayOfString[1])) {
/* 45 */         factionData.setKills(Math.max(0, factionData.getKills() + i));
/* 46 */       } else if ("wins".equals(paramArrayOfString[1])) {
/* 47 */         factionData.setWins(Math.max(0, factionData.getWins() + i));
/* 48 */       } else if ("losts".equals(paramArrayOfString[1])) {
/* 49 */         factionData.setLosts(Math.max(0, factionData.getLosts() + i));
/* 50 */       }  (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 53 */             DatabaseHelper.get().saveStats(factionData);
/*    */           }
/* 55 */         }).runTaskAsynchronously((Plugin)FactionWars.get());
/* 56 */       paramCommandSender.sendMessage("§cUpdated " + paramArrayOfString[1] + " by " + i); }
/* 57 */     else { paramCommandSender.sendMessage("§cAn error occurred updating stats."); }
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\StatsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */