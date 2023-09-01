/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SetKitItemCommand {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*    */     ItemStack itemStack;
/* 13 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.setitem") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 14 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 15 */       return false;
/*    */     } 
/*    */     
/* 18 */     if (!(paramCommandSender instanceof Player)) {
/* 19 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     if (paramArrayOfString.length != 1) {
/* 24 */       paramCommandSender.sendMessage("§cUsage: /fw setkititem <kit id>");
/* 25 */       return false;
/*    */     } 
/*    */     
/* 28 */     Kit kit = Kit.getKit(paramArrayOfString[0]);
/*    */     
/* 30 */     if (kit == null) {
/* 31 */       paramCommandSender.sendMessage("§ckit not found! List with /fw listkits");
/* 32 */       return false;
/*    */     } 
/*    */     
/* 35 */     Player player = (Player)paramCommandSender;
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (LegacyUtil.is1_13) { itemStack = player.getInventory().getItemInMainHand(); }
/* 40 */     else { itemStack = player.getItemInHand(); }
/*    */     
/* 42 */     FactionWars.getKitsConfig().getConfig().set("kits." + kit.getId() + ".item", itemStack);
/* 43 */     FactionWars.getKitsConfig().saveConfig();
/*    */     
/* 45 */     kit.setItem(itemStack);
/*    */     
/* 47 */     paramCommandSender.sendMessage("§6Display Item for kit §a" + kit.getName() + "§6 saved!");
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\SetKitItemCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */