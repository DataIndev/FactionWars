/*    */ package io.github.guipenedo.factionwars.commands.admin;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ class VersionCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  9 */     if (!paramCommandSender.hasPermission("factionwars.admin.version") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 10 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 11 */       return false;
/*    */     } 
/*    */     
/* 14 */     paramCommandSender.sendMessage("§6FactionWars§a v" + FactionWars.get().getDescription().getVersion());
/* 15 */     paramCommandSender.sendMessage("§6For§a " + FactionWars.getHandler().getName());
/*    */     
/* 17 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\VersionCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */