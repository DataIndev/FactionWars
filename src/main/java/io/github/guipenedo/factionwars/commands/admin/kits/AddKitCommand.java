/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class AddKitCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.add") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 12 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 13 */       return false;
/*    */     } 
/*    */     
/* 16 */     if (!(paramCommandSender instanceof org.bukkit.entity.Player)) {
/* 17 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 18 */       return false;
/*    */     } 
/*    */     
/* 21 */     if (paramArrayOfString.length < 1) {
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw addkit <kit name>");
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     StringBuilder stringBuilder = new StringBuilder();
/* 27 */     for (String str : paramArrayOfString)
/* 28 */       stringBuilder.append(str).append(" "); 
/* 29 */     stringBuilder = new StringBuilder(stringBuilder.toString().trim());
/*    */ 
/*    */     
/* 32 */     int i = 0;
/* 33 */     if (FactionWars.getKitsConfig().getConfig().contains("kits.1"))
/* 34 */       i = FactionWars.getKitsConfig().getConfig().getConfigurationSection("kits").getKeys(false).size(); 
/* 35 */     i++;
/*    */     
/* 37 */     FactionWars.getKitsConfig().getConfig().set("kits." + i + ".name", stringBuilder.toString());
/* 38 */     FactionWars.getKitsConfig().saveConfig();
/*    */     
/* 40 */     paramCommandSender.sendMessage("§6Kit §e" + stringBuilder + " §6added with ID §a" + i);
/* 41 */     paramCommandSender.sendMessage("§6Set it's contents using §c/fw setkit " + i);
/*    */     
/* 43 */     Kit.getKits().add(new Kit(String.valueOf(i)));
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\AddKitCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */