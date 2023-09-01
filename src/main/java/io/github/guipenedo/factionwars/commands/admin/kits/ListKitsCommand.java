/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ListKitsCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  9 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.list") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 10 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 11 */       return false;
/*    */     } 
/*    */     
/* 14 */     if (paramArrayOfString.length != 0) {
/* 15 */       paramCommandSender.sendMessage("§cUsage: /fw listkits");
/* 16 */       return false;
/*    */     } 
/*    */     
/* 19 */     if (Kit.getKits().size() == 0) {
/* 20 */       paramCommandSender.sendMessage("§cNo kits found.");
/*    */     } else {
/* 22 */       paramCommandSender.sendMessage("§3Kit ID §7 - §aKit Name");
/* 23 */       for (Kit kit : Kit.getKits())
/* 24 */         paramCommandSender.sendMessage("§3" + kit.getId() + "§7 - §a" + kit.getName()); 
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\ListKitsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */