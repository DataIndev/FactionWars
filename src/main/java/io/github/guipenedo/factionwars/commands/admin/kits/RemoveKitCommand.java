/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class RemoveKitCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.remove") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
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
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw removekit <kit id>");
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     Kit kit = Kit.getKit(paramArrayOfString[0]);
/*    */     
/* 28 */     if (kit == null) {
/* 29 */       paramCommandSender.sendMessage("§ckit not found! List with /fw listkits");
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     FactionWars.getKitsConfig().getConfig().set("kits." + kit.getId(), null);
/*    */     
/* 35 */     for (int i = Integer.parseInt(kit.getId()) + 1; i < Kit.getKits().size() + 1; i++) {
/* 36 */       FactionWars.getKitsConfig().getConfig().set("kits." + (i - 1), FactionWars.getKitsConfig().getConfig().getConfigurationSection("kits." + i));
/* 37 */       FactionWars.getKitsConfig().getConfig().set("kits." + i, null);
/*    */     } 
/* 39 */     FactionWars.getKitsConfig().saveConfig();
/*    */     
/* 41 */     paramCommandSender.sendMessage("§6Removed kit " + kit.getName());
/*    */     
/* 43 */     FactionWars.get().loadKits();
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\RemoveKitCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */