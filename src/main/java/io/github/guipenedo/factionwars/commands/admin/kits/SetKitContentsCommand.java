/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SetKitContentsCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 12 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.set") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 13 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 14 */       return false;
/*    */     } 
/*    */     
/* 17 */     if (!(paramCommandSender instanceof Player)) {
/* 18 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 19 */       return false;
/*    */     } 
/*    */     
/* 22 */     if (paramArrayOfString.length != 1) {
/* 23 */       paramCommandSender.sendMessage("§cUsage: /fw setkit <kit id>");
/* 24 */       return false;
/*    */     } 
/*    */     
/* 27 */     Kit kit = Kit.getKit(paramArrayOfString[0]);
/*    */     
/* 29 */     if (kit == null) {
/* 30 */       paramCommandSender.sendMessage("§ckit not found! List with /fw listkits");
/* 31 */       return false;
/*    */     } 
/*    */     
/* 34 */     Player player = (Player)paramCommandSender;
/*    */     
/* 36 */     Util.get().saveInventory(player.getInventory(), FactionWars.getKitsConfig().getConfig(), "kits." + kit.getId() + ".inv");
/* 37 */     FactionWars.getKitsConfig().saveConfig();
/*    */     
/* 39 */     kit.setArmor(player.getInventory().getArmorContents());
/* 40 */     kit.setItems(player.getInventory().getContents());
/* 41 */     kit.setPotionEffects(player.getActivePotionEffects());
/*    */     
/* 43 */     paramCommandSender.sendMessage("§6Contents for kit §a" + kit.getName() + "§6 saved!");
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\SetKitContentsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */