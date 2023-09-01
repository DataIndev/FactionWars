/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.ConditionsChecker;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsAcceptCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsAcceptCommand() {
/* 21 */     this.aliases.add("accept");
/* 22 */     this.aliases.add("answer");
/* 23 */     this.aliases.add("a");
/*    */     
/* 25 */     this.helpText = Util.getMessage("commands.accept");
/* 26 */     this.needsFaction = true;
/*    */     
/* 28 */     this.requiredArgs.add("faction");
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 33 */     if (!ConditionsChecker.check(paramPlayer, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.HAS_ACCEPT_PERMISSION, ConditionsChecker.Condition.IS_LEADER, ConditionsChecker.Condition.HAS_ENOUGH_PLAYERS, ConditionsChecker.Condition.IS_NOT_PLAYING })) {
/*    */       return;
/*    */     }
/* 36 */     String str = Util.factionByString(paramVarArgs[0]);
/* 37 */     if (str == null) {
/* 38 */       paramPlayer.sendMessage(Util.getMessage("error.faction-not-found"));
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     if (Util.targetCantWar(str)) {
/* 43 */       paramPlayer.sendMessage(Util.getMessage("error.leader-offline"));
/*    */       
/*    */       return;
/*    */     } 
/* 47 */     if (FactionWars.getHandler().getRelationBetween(paramString, str) == TeamHandler.Relation.ALLY) {
/* 48 */       paramPlayer.sendMessage(Util.getMessage("error.allies"));
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     PlayerSelection playerSelection1 = PlayerSelection.getByFactions(str, paramString);
/*    */     
/* 54 */     if (playerSelection1 == null || !playerSelection1.isConfirmed()) {
/* 55 */       paramPlayer.sendMessage(Util.getMessage("error.not-challenged", Util.getVars(new Object[] { "faction", paramVarArgs[0] })));
/*    */       
/*    */       return;
/*    */     } 
/* 59 */     if (Util.doesNotHaveEnoughPlayers(str)) {
/* 60 */       paramPlayer.sendMessage(Util.getMessage("error.target-no-player"));
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     if (MatchManager.get().isFactionBusy(paramString)) {
/* 65 */       paramPlayer.sendMessage(Util.getMessage("error.already-playing"));
/*    */       
/*    */       return;
/*    */     } 
/* 69 */     if (playerSelection1.getBet().doubleValue() > FactionWars.getHandler().getBalance(paramString)) {
/* 70 */       paramPlayer.sendMessage(Util.getMessage("error.no-money"));
/*    */       
/*    */       return;
/*    */     } 
/* 74 */     FactionData factionData = FactionData.getFaction(paramString);
/* 75 */     if (factionData != null && factionData.isCooldown()) {
/* 76 */       paramPlayer.sendMessage(Util.getMessage("error.cooldown"));
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     int i = Util.getLimit(paramString, str);
/* 81 */     PlayerSelection playerSelection2 = new PlayerSelection(playerSelection1.getGamemode(), paramPlayer.getUniqueId(), i, paramString, str, playerSelection1.getBet());
/* 82 */     playerSelection2.setLocked(true);
/* 83 */     PlayerSelection.getPlayerSelections().add(playerSelection2);
/* 84 */     Util.openNext(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsAcceptCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */