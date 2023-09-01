/*    */ package io.github.guipenedo.factionwars.commands.admin.gamemodes;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.Gamemode;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ListGamemodesCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  9 */     if (!paramCommandSender.hasPermission("factionwars.admin.gamemode.list") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 10 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 11 */       return false;
/*    */     } 
/*    */     
/* 14 */     if (paramArrayOfString.length != 0) {
/* 15 */       paramCommandSender.sendMessage("§cUsage: /fw listgamemodes");
/* 16 */       return false;
/*    */     } 
/*    */     
/* 19 */     if (Gamemode.getGamemodes().size() == 0) {
/* 20 */       paramCommandSender.sendMessage("§cNo gamemodes found.");
/*    */     } else {
/* 22 */       paramCommandSender.sendMessage("§3Gamemode ID §7 - §aGamemode type");
/* 23 */       for (Gamemode gamemode : Gamemode.getGamemodes().values())
/* 24 */         paramCommandSender.sendMessage("§3" + gamemode.getName() + "§7 - §a" + gamemode.getGamemodeType()); 
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\gamemodes\ListGamemodesCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */