/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class SetKitPriceCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*    */     double d;
/* 11 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.price") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 12 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 13 */       return false;
/*    */     } 
/*    */     
/* 16 */     if (!(paramCommandSender instanceof org.bukkit.entity.Player)) {
/* 17 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 18 */       return false;
/*    */     } 
/*    */     
/* 21 */     if (paramArrayOfString.length != 2) {
/* 22 */       paramCommandSender.sendMessage("§cUsage: /fw setkitprice <kit id> <price>");
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
/*    */     
/*    */     try {
/* 35 */       d = Double.parseDouble(paramArrayOfString[1]);
/* 36 */     } catch (NumberFormatException numberFormatException) {
/* 37 */       paramCommandSender.sendMessage("§cUnable to set price " + paramArrayOfString[1] + " for kit " + kit.getName());
/* 38 */       return false;
/*    */     } 
/*    */     
/* 41 */     if (d < 0.0D) {
/* 42 */       paramCommandSender.sendMessage("§cUnable to set price " + paramArrayOfString[1] + " for kit " + kit.getName());
/* 43 */       return false;
/*    */     } 
/*    */     
/* 46 */     kit.setPrice(d);
/* 47 */     FactionWars.getKitsConfig().getConfig().set("kits." + kit.getId() + ".price", Double.valueOf(d));
/* 48 */     FactionWars.getKitsConfig().saveConfig();
/*    */     
/* 50 */     paramCommandSender.sendMessage("§6Price for kit §a" + kit.getName() + "§6 saved!");
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\SetKitPriceCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */