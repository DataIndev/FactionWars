/*    */ package io.github.guipenedo.factionwars.commands.admin.kits;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class EditKitContentsCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 13 */     if (!paramCommandSender.hasPermission("factionwars.admin.kit.edit") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
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
/* 24 */       paramCommandSender.sendMessage("§cUsage: /fw editkit <kit id>");
/* 25 */       return false;
/*    */     } 
/*    */     
/* 28 */     Kit kit = Kit.getKit(paramArrayOfString[0]);
/*    */     
/* 30 */     if (kit == null) {
/* 31 */       paramCommandSender.sendMessage("§cKit not found! List with /fw listkits");
/* 32 */       return false;
/*    */     } 
/*    */     
/* 35 */     Player player = (Player)paramCommandSender;
/*    */     
/* 37 */     player.getInventory().setContents(Arrays.<ItemStack>copyOfRange(kit.getItems(), 0, (player.getInventory().getContents()).length));
/*    */     
/* 39 */     player.getInventory().setArmorContents(kit.getArmor());
/* 40 */     for (PotionEffect potionEffect : kit.getPotionEffects()) {
/* 41 */       player.addPotionEffect(potionEffect);
/*    */     }
/* 43 */     player.updateInventory();
/*    */     
/* 45 */     paramCommandSender.sendMessage("§6Now editing §a" + kit.getName() + "§6. Type §c/fw setkit " + kit.getId() + " §6when you finish.");
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\kits\EditKitContentsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */