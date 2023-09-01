/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.MapSetupManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AddMapCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 12 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.add") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 13 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 14 */       return false;
/*    */     } 
/*    */     
/* 17 */     if (!(paramCommandSender instanceof Player)) {
/* 18 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 19 */       return false;
/*    */     } 
/*    */     
/* 22 */     if (paramArrayOfString.length < 1) {
/* 23 */       paramCommandSender.sendMessage("§cUsage: /fw addmap <map name>");
/* 24 */       return false;
/*    */     } 
/*    */     
/* 27 */     StringBuilder stringBuilder = new StringBuilder();
/* 28 */     for (String str : paramArrayOfString)
/* 29 */       stringBuilder.append(str).append(" "); 
/* 30 */     stringBuilder = new StringBuilder(stringBuilder.toString().trim());
/*    */ 
/*    */     
/* 33 */     int i = 0;
/* 34 */     if (FactionWars.getMapsConfig().getConfig().contains("maps.1"))
/* 35 */       i = FactionWars.getMapsConfig().getConfig().getConfigurationSection("maps").getKeys(false).size(); 
/* 36 */     i++;
/*    */     
/* 38 */     FactionWars.getMapsConfig().getConfig().set("maps." + i + ".name", stringBuilder.toString());
/* 39 */     FactionWars.getMapsConfig().saveConfig();
/*    */     
/* 41 */     paramCommandSender.sendMessage("§6Map §e" + stringBuilder + " §6added with ID §a" + i);
/* 42 */     WarMap warMap = new WarMap(String.valueOf(i));
/* 43 */     WarMap.getMaps().put(String.valueOf(i), warMap);
/* 44 */     MapSetupManager.get().setup((Player)paramCommandSender, warMap);
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\AddMapCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */