/*    */ package io.github.guipenedo.factionwars.commands.admin;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.models.Gamemode;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ class StartCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/*    */     WarMap warMap;
/* 19 */     if (!paramCommandSender.hasPermission("factionwars.admin.start") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 20 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 21 */       return false;
/*    */     } 
/*    */     
/* 24 */     if (paramArrayOfString.length < 2 || paramArrayOfString.length > 4) {
/* 25 */       paramCommandSender.sendMessage("§cUsage: /fw start <faction1> <faction2> [mapID] [bet value]");
/* 26 */       return false;
/*    */     } 
/*    */     
/* 29 */     String str1 = FactionWars.getHandler().getTeamIdByName(paramArrayOfString[0]);
/* 30 */     String str2 = FactionWars.getHandler().getTeamIdByName(paramArrayOfString[1]);
/*    */     
/* 32 */     if (str1 == null || str2 == null) {
/* 33 */       paramCommandSender.sendMessage("§cInvalid faction(s)");
/* 34 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (paramArrayOfString.length == 2 || WarMap.getMap(paramArrayOfString[2]) == null) {
/* 40 */       warMap = MatchManager.get().findFreeArena(Gamemode.getDefault());
/*    */     } else {
/* 42 */       warMap = WarMap.getMap(paramArrayOfString[2]);
/*    */     } 
/* 44 */     if (warMap == null || !warMap.isFree() || !warMap.isSetup()) {
/* 45 */       paramCommandSender.sendMessage("§cMap(s) unavailable!");
/* 46 */       return false;
/*    */     } 
/*    */     
/* 49 */     double d = 0.0D;
/* 50 */     if (paramArrayOfString.length == 4 && NumberUtils.isNumber(paramArrayOfString[3])) {
/* 51 */       d = Double.parseDouble(paramArrayOfString[3]);
/*    */     }
/* 53 */     if (FactionWars.getHandler().getRelationBetween(str1, str2) == TeamHandler.Relation.ALLY) {
/* 54 */       paramCommandSender.sendMessage("§cFactions are allies.");
/* 55 */       return false;
/*    */     } 
/*    */     
/* 58 */     if (Util.doesNotHaveEnoughPlayers(str1) || Util.doesNotHaveEnoughPlayers(str1)) {
/* 59 */       paramCommandSender.sendMessage("§cFaction(s) does not have enough players.");
/* 60 */       return false;
/*    */     } 
/*    */     
/* 63 */     PlayerSelection playerSelection1 = createSelectionForFaction(str1, str2);
/* 64 */     PlayerSelection playerSelection2 = createSelectionForFaction(str2, str1);
/* 65 */     playerSelection1.setBet(Double.valueOf(d));
/* 66 */     playerSelection2.setBet(Double.valueOf(d));
/* 67 */     MatchManager.get().start(playerSelection1, playerSelection2, warMap);
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   private PlayerSelection createSelectionForFaction(String paramString1, String paramString2) {
/* 72 */     ArrayList<Player> arrayList = Util.getOnlineOptedin(paramString1);
/* 73 */     int i = Util.getLimit(paramString1, paramString2) + 1;
/* 74 */     PlayerSelection playerSelection = new PlayerSelection(null, i, paramString1, paramString2);
/* 75 */     byte b = 0; while (true) { if ((((b < i) ? 1 : 0) & ((b < arrayList.size()) ? 1 : 0)) != 0) {
/* 76 */         Player player = arrayList.get(b);
/* 77 */         playerSelection.getPlayersSelected().add(player.getUniqueId()); b++; continue;
/*    */       }  break; }
/* 79 */      playerSelection.setConfirmed(true);
/* 80 */     PlayerSelection.getPlayerSelections().add(playerSelection);
/* 81 */     return playerSelection;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\StartCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */