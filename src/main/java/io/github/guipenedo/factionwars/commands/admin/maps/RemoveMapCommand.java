/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class RemoveMapCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.remove") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 12 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 13 */       return false;
/*    */     } 
/*    */     
/* 16 */     if (!(paramCommandSender instanceof org.bukkit.entity.Player)) {
/* 17 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 18 */       return false;
/*    */     } 
/*    */     
/* 21 */     if (paramArrayOfString.length != 1) {
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw removemap <map id>");
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     WarMap warMap = WarMap.getMap(paramArrayOfString[0]);
/*    */     
/* 28 */     if (warMap == null) {
/* 29 */       paramCommandSender.sendMessage("§cMap not found! List with /fw listmaps");
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     FactionWars.getMapsConfig().getConfig().set("maps." + warMap.getId(), null);
/*    */     
/* 35 */     for (int i = Integer.parseInt(warMap.getId()) + 1; i < WarMap.getMaps().size() + 1; i++) {
/* 36 */       FactionWars.getMapsConfig().getConfig().set("maps." + (i - 1), FactionWars.getMapsConfig().getConfig().getConfigurationSection("maps." + i));
/* 37 */       FactionWars.getMapsConfig().getConfig().set("maps." + i, null);
/*    */     } 
/* 39 */     FactionWars.getMapsConfig().saveConfig();
/*    */     
/* 41 */     paramCommandSender.sendMessage("§6Removed map " + warMap.getName());
/*    */     
/* 43 */     FactionWars.get().loadMaps();
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\RemoveMapCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */