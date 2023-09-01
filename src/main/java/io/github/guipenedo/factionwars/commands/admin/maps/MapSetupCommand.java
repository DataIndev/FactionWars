/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.managers.MapSetupManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class MapSetupCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.setup") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 12 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 13 */       return false;
/*    */     } 
/*    */     
/* 16 */     if (!(paramCommandSender instanceof Player)) {
/* 17 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 18 */       return false;
/*    */     } 
/*    */     
/* 21 */     if (paramArrayOfString.length < 1) {
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw mapsetup <map id>");
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     WarMap warMap = WarMap.getMap(paramArrayOfString[0]);
/* 27 */     if (warMap == null) {
/* 28 */       paramCommandSender.sendMessage("§cNo map with that id was found.");
/* 29 */       return false;
/*    */     } 
/*    */     
/* 32 */     MapSetupManager.get().setup((Player)paramCommandSender, warMap);
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\MapSetupCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */