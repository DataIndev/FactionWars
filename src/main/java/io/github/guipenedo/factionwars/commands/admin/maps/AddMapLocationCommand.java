/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MapSetupManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AddMapLocationCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.setlocation") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 12 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 13 */       return false;
/*    */     } 
/*    */     
/* 16 */     if (!(paramCommandSender instanceof Player)) {
/* 17 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 18 */       return false;
/*    */     } 
/*    */     
/* 21 */     if (paramArrayOfString.length == 0) {
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw addlocation <map id> <location>");
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
/* 33 */     if (warMap.getManager() == null) {
/* 34 */       MapSetupManager.get().setup((Player)paramCommandSender, warMap);
/* 35 */       return false;
/*    */     } 
/*    */     
/* 38 */     if (paramArrayOfString.length != 2 || !MapSetupManager.get().isLocationValid(warMap, paramArrayOfString[1])) {
/* 39 */       paramCommandSender.sendMessage("§cYou've entered an invalid location for this map!");
/* 40 */       paramCommandSender.sendMessage("§cType §4/fw mapsetup " + warMap.getId() + " §cfor setup instructions");
/* 41 */       return false;
/*    */     } 
/*    */     
/* 44 */     warMap.addLocation(paramArrayOfString[1], ((Player)paramCommandSender).getLocation());
/* 45 */     warMap.saveLocations();
/* 46 */     warMap.getManager().updateLocations(warMap);
/* 47 */     paramCommandSender.sendMessage("§6Location §e" + paramArrayOfString[1] + " §6for map §a" + warMap.getName() + "§6 added!");
/* 48 */     MapSetupManager.get().setup((Player)paramCommandSender, warMap);
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\AddMapLocationCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */