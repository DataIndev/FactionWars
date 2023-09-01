/*    */ package io.github.guipenedo.factionwars.commands.admin;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ class ReloadCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  9 */     if (!paramCommandSender.hasPermission("factionwars.admin.reload") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 10 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 11 */       return false;
/*    */     } 
/*    */     
/* 14 */     FactionWars.get().onDisable();
/* 15 */     FactionWars.get().load();
/*    */     
/* 17 */     paramCommandSender.sendMessage("§aReloaded config, messages, kits, maps and database connection.");
/*    */     
/* 19 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\ReloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */