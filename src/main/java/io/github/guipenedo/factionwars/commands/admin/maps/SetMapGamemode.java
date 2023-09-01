/*    */ package io.github.guipenedo.factionwars.commands.admin.maps;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.MapSetupManager;
/*    */ import io.github.guipenedo.factionwars.models.Gamemode;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class SetMapGamemode
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 15 */     if (!paramCommandSender.hasPermission("factionwars.admin.map.gamemode") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 16 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 17 */       return false;
/*    */     } 
/*    */     
/* 20 */     if (!(paramCommandSender instanceof Player)) {
/* 21 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 22 */       return false;
/*    */     } 
/*    */     
/* 25 */     if (paramArrayOfString.length < 2) {
/* 26 */       paramCommandSender.sendMessage("§cUsage: /fw setgamemode <map id> <gamemode>");
/* 27 */       return false;
/*    */     } 
/*    */     
/* 30 */     String str = paramArrayOfString[0];
/* 31 */     WarMap warMap = WarMap.getMap(str);
/* 32 */     if (warMap == null) {
/* 33 */       paramCommandSender.sendMessage("§cMap with id " + str + " not found.");
/* 34 */       return false;
/*    */     } 
/*    */     
/* 37 */     paramArrayOfString = Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length);
/* 38 */     StringBuilder stringBuilder = new StringBuilder();
/* 39 */     for (String str1 : paramArrayOfString)
/* 40 */       stringBuilder.append(str1).append(" "); 
/* 41 */     stringBuilder = new StringBuilder(stringBuilder.toString().trim());
/*    */     
/* 43 */     Gamemode gamemode = Gamemode.get(stringBuilder.toString());
/* 44 */     if (gamemode == null) {
/* 45 */       paramCommandSender.sendMessage("§cGamemode " + stringBuilder + " not found.");
/* 46 */       return false;
/*    */     } 
/*    */     
/* 49 */     warMap.setGamemode(gamemode);
/* 50 */     FactionWars.getMapsConfig().getConfig().set("maps." + str + ".gamemode", gamemode.getName());
/* 51 */     FactionWars.getMapsConfig().saveConfig();
/*    */     
/* 53 */     paramCommandSender.sendMessage("§6Gamemode for map §e" + warMap.getName() + " §6set to §a" + gamemode.getName());
/*    */     
/* 55 */     MapSetupManager.get().setup((Player)paramCommandSender, warMap);
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\maps\SetMapGamemode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */