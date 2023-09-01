/*    */ package io.github.guipenedo.factionwars.commands.admin.gamemodes;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import io.github.guipenedo.factionwars.models.Gamemode;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SetGamemodeItemCommand {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*    */     ItemStack itemStack;
/* 13 */     if (!paramCommandSender.hasPermission("factionwars.admin.gamemode.setitem") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 14 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 15 */       return false;
/*    */     } 
/*    */     
/* 18 */     if (!(paramCommandSender instanceof Player)) {
/* 19 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     if (paramArrayOfString.length < 1) {
/* 24 */       paramCommandSender.sendMessage("§cUsage: /fw setgmitem <gamemode>");
/* 25 */       return false;
/*    */     } 
/*    */     
/* 28 */     StringBuilder stringBuilder = new StringBuilder();
/* 29 */     for (String str : paramArrayOfString)
/* 30 */       stringBuilder.append(str).append(" "); 
/* 31 */     stringBuilder = new StringBuilder(stringBuilder.toString().trim());
/* 32 */     Gamemode gamemode = Gamemode.get(stringBuilder.toString());
/*    */     
/* 34 */     if (gamemode == null) {
/* 35 */       paramCommandSender.sendMessage("§cGamemode not found! List with /fw listgamemodes");
/* 36 */       return false;
/*    */     } 
/*    */     
/* 39 */     Player player = (Player)paramCommandSender;
/*    */ 
/*    */     
/* 42 */     if (LegacyUtil.is1_13) { itemStack = player.getInventory().getItemInMainHand(); }
/* 43 */     else { itemStack = player.getItemInHand(); }
/*    */     
/* 45 */     FactionWars.getGamemodesConfig().getConfig().set("gamemodes." + gamemode.getName() + ".item", itemStack);
/* 46 */     FactionWars.getGamemodesConfig().saveConfig();
/*    */     
/* 48 */     gamemode.setItem(itemStack);
/* 49 */     paramCommandSender.sendMessage("§6Display Item for gamemode §a" + gamemode.getName() + "§6 saved!");
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\gamemodes\SetGamemodeItemCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */