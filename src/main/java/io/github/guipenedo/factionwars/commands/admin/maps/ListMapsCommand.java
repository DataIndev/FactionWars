/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ListMapsCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  9 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.list") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 10 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 11 */       return false;
/*    */     } 
/*    */     
/* 14 */     if (paramArrayOfString.length != 0) {
/* 15 */       paramCommandSender.sendMessage("§cUsage: /fw listmaps");
/* 16 */       return false;
/*    */     } 
/*    */     
/* 19 */     if (WarMap.getMaps().size() == 0) {
/* 20 */       paramCommandSender.sendMessage("§cNo maps found.");
/*    */     } else {
/* 22 */       paramCommandSender.sendMessage("§3Map ID §7 - §aMap Name §7- §3Gamemode");
/* 23 */       for (WarMap warMap : WarMap.getMaps().values())
/* 24 */         paramCommandSender.sendMessage("§3" + warMap.getId() + "§7 - §a" + warMap.getName() + "§7 - §3" + warMap.getGamemode().getName()); 
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\ListMapsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */