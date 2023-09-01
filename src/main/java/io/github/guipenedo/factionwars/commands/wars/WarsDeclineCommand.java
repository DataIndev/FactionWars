/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.ConditionsChecker;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsDeclineCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsDeclineCommand() {
/* 21 */     this.aliases.add("decline");
/* 22 */     this.aliases.add("deny");
/* 23 */     this.aliases.add("refuse");
/* 24 */     this.aliases.add("reject");
/* 25 */     this.aliases.add("r");
/* 26 */     this.aliases.add("d");
/*    */     
/* 28 */     this.requiredArgs.add("team");
/*    */     
/* 30 */     this.needsFaction = true;
/* 31 */     this.helpText = Util.getMessage("commands.decline");
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 36 */     if (!ConditionsChecker.check(paramPlayer, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.HAS_DECLINE_PERMISSION, ConditionsChecker.Condition.IS_LEADER })) {
/*    */       return;
/*    */     }
/* 39 */     String str = Util.factionByString(paramVarArgs[0]);
/*    */     
/* 41 */     if (str == null) {
/* 42 */       paramPlayer.sendMessage(Util.getMessage("error.faction-not-found"));
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     PlayerSelection playerSelection = PlayerSelection.getByFactions(str, paramString);
/*    */     
/* 48 */     if (playerSelection == null || !playerSelection.isConfirmed()) {
/* 49 */       paramPlayer.sendMessage(Util.getMessage("error.not-challenged"));
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     if (PlayerSelection.getByFactions(paramString, str) != null) {
/* 54 */       paramPlayer.sendMessage(Util.getMessage("error.already-accepted"));
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     HashMap hashMap = Util.getVars(new Object[] { "targetfaction", FactionWars.getHandler().getTeamName(str), "senderfaction", FactionWars.getHandler().getTeamName(paramString) });
/* 59 */     FactionWars.getHandler().sendMessage(paramString, Util.getMessage("refuse.self", hashMap));
/* 60 */     FactionWars.getHandler().sendMessage(str, Util.getMessage("refuse.target", hashMap));
/*    */     
/* 62 */     PlayerSelection.getPlayerSelections().remove(playerSelection);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsDeclineCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */