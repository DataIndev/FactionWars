/*     */ package io.github.guipenedo.factionwars.commands.wars;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*     */ import io.github.guipenedo.factionwars.helpers.ConditionsChecker;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WarsInviteCommand
/*     */   extends WarsSubCommand
/*     */ {
/*     */   public WarsInviteCommand() {
/*  23 */     this.aliases.add("challenge");
/*  24 */     this.aliases.add("c");
/*  25 */     this.aliases.add("invite");
/*  26 */     this.aliases.add("inv");
/*  27 */     this.aliases.add("i");
/*     */     
/*  29 */     this.requiredArgs.add("faction");
/*  30 */     this.optionalArgs.put("bet", "open GUI");
/*     */     
/*  32 */     this.needsFaction = true;
/*  33 */     this.helpText = Util.getMessage("commands.invite");
/*     */   }
/*     */ 
/*     */   
/*     */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/*  38 */     if (!ConditionsChecker.check(paramPlayer, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.IS_LEADER, ConditionsChecker.Condition.HAS_ENOUGH_PLAYERS, ConditionsChecker.Condition.HAS_INVITE_PERMISSION })) {
/*     */       return;
/*     */     }
/*  41 */     String str = Util.factionByString(paramVarArgs[0]);
/*     */     
/*  43 */     if (str == null) {
/*  44 */       paramPlayer.sendMessage(Util.getMessage("error.faction-not-found"));
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     if (str.equals(paramString)) {
/*  49 */       paramPlayer.sendMessage(Util.getMessage("error.challenge-own"));
/*     */       
/*     */       return;
/*     */     } 
/*  53 */     if (Util.targetCantWar(str)) {
/*  54 */       paramPlayer.sendMessage(Util.getMessage("error.leader-offline"));
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     if (PlayerSelection.getByFactions(paramString, str) != null) {
/*  59 */       paramPlayer.sendMessage(Util.getMessage("error.already-challenged"));
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     if (!FactionWars.getMainConfig().getConfig().getBoolean("can-challenge-allies") && FactionWars.getHandler().getRelationBetween(paramString, str) == TeamHandler.Relation.ALLY) {
/*  64 */       paramPlayer.sendMessage(Util.getMessage("error.allies"));
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     PlayerSelection playerSelection1 = PlayerSelection.getByFactions(str, paramString);
/*  69 */     if (playerSelection1 != null) {
/*  70 */       if (playerSelection1.isConfirmed()) {
/*  71 */         HashMap<Object, Object> hashMap = new HashMap<>();
/*  72 */         hashMap.put("faction", paramVarArgs[0]);
/*  73 */         paramPlayer.sendMessage(Util.getMessage("error.faction-challenged", hashMap));
/*     */         
/*     */         return;
/*     */       } 
/*  77 */       Player player = FactionWars.get().getServer().getPlayer(playerSelection1.getPlayerSelecting());
/*  78 */       if (player != null) {
/*  79 */         player.closeInventory();
/*  80 */         player.sendMessage("§cError: another player from your faction is inviting this faction.");
/*     */       } 
/*  82 */       PlayerSelection.getPlayerSelections().remove(playerSelection1);
/*     */     } 
/*     */     
/*  85 */     if (Util.doesNotHaveEnoughPlayers(str)) {
/*  86 */       paramPlayer.sendMessage(Util.getMessage("error.target-no-player"));
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     FactionData factionData = FactionData.getFaction(paramString);
/*  91 */     if (factionData != null && factionData.isCooldown()) {
/*  92 */       paramPlayer.sendMessage(Util.getMessage("error.cooldown"));
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     double d = -1.0D;
/*  97 */     if (paramVarArgs.length == 2 && NumberUtils.isNumber(paramVarArgs[1])) {
/*  98 */       d = Double.parseDouble(paramVarArgs[1]);
/*  99 */       if (!Util.validBet(paramPlayer, d)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 103 */     int i = Util.getLimit(paramString, str);
/* 104 */     PlayerSelection playerSelection2 = new PlayerSelection(paramPlayer.getUniqueId(), i, paramString, str);
/* 105 */     if (d != -1.0D)
/* 106 */       playerSelection2.setBet(Double.valueOf(d)); 
/* 107 */     PlayerSelection.getPlayerSelections().add(playerSelection2);
/* 108 */     Util.openNext(paramPlayer);
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsInviteCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */