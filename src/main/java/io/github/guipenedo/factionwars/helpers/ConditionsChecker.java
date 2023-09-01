/*     */ package io.github.guipenedo.factionwars.helpers;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import io.github.guipenedo.factionwars.models.Gamemode;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class ConditionsChecker
/*     */ {
/*     */   private Player player;
/*     */   private boolean silent;
/*     */   private boolean checkHasFaction = false;
/*     */   private boolean checkHasInvites = false;
/*     */   private boolean checkIsLeader = false;
/*     */   private boolean checkHasAcceptPermission = false;
/*     */   private boolean checkHasDeclinePermission = false;
/*     */   private boolean checkHasInvitePermission = false;
/*     */   private boolean checkHasEnoughPlayers = false;
/*     */   private boolean checkIsNotPlaying = false;
/*     */   private boolean checkGamemodesSetup = false;
/*     */   
/*     */   private ConditionsChecker(Player paramPlayer, Condition... paramVarArgs) {
/*  25 */     this.player = paramPlayer;
/*  26 */     for (Condition condition : paramVarArgs) {
/*  27 */       switch (condition) {
/*     */         case HAS_INVITES:
/*  29 */           this.checkHasInvites = true;
/*  30 */           this.checkHasFaction = true;
/*     */           break;
/*     */         case HAS_FACTION:
/*  33 */           this.checkHasFaction = true;
/*     */           break;
/*     */         case IS_LEADER:
/*  36 */           this.checkHasFaction = true;
/*  37 */           this.checkIsLeader = true;
/*     */           break;
/*     */         case HAS_ACCEPT_PERMISSION:
/*  40 */           this.checkHasAcceptPermission = true;
/*     */           break;
/*     */         case HAS_DECLINE_PERMISSION:
/*  43 */           this.checkHasDeclinePermission = true;
/*     */           break;
/*     */         case HAS_INVITE_PERMISSION:
/*  46 */           this.checkHasInvitePermission = true;
/*     */           break;
/*     */         case HAS_ENOUGH_PLAYERS:
/*  49 */           this.checkHasEnoughPlayers = true;
/*  50 */           this.checkHasFaction = true;
/*     */           break;
/*     */         case IS_NOT_PLAYING:
/*  53 */           this.checkIsNotPlaying = true;
/*  54 */           this.checkHasFaction = true;
/*     */           break;
/*     */         case GAMEMODES_SETUP:
/*  57 */           this.checkGamemodesSetup = true;
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean check() {
/*  63 */     if (this.checkHasAcceptPermission && 
/*  64 */       !this.player.hasPermission("factionwars.player.*") && !this.player.hasPermission("factionwars.player.accept")) {
/*  65 */       if (!this.silent)
/*  66 */         this.player.sendMessage(Util.getMessage("error.no-permission")); 
/*  67 */       return false;
/*     */     } 
/*  69 */     if (this.checkHasDeclinePermission && 
/*  70 */       !this.player.hasPermission("factionwars.player.*") && !this.player.hasPermission("factionwars.player.decline")) {
/*  71 */       if (!this.silent)
/*  72 */         this.player.sendMessage(Util.getMessage("error.no-permission")); 
/*  73 */       return false;
/*     */     } 
/*  75 */     if (this.checkHasInvitePermission && 
/*  76 */       !this.player.hasPermission("factionwars.player.*") && !this.player.hasPermission("factionwars.player.invite")) {
/*  77 */       if (!this.silent)
/*  78 */         this.player.sendMessage(Util.getMessage("error.no-permission")); 
/*  79 */       return false;
/*     */     } 
/*  81 */     if (this.checkHasFaction) {
/*  82 */       String str = FactionWars.getHandler().getPlayerTeam(this.player);
/*  83 */       if (str == null || FactionData.getFaction(str) == null) {
/*  84 */         if (!this.silent)
/*  85 */           this.player.sendMessage(Util.getMessage("error.no-faction")); 
/*  86 */         return false;
/*     */       } 
/*  88 */       if (this.checkIsLeader && 
/*  89 */         !Util.getLeaders(str).contains(this.player)) {
/*  90 */         if (!this.silent)
/*  91 */           this.player.sendMessage(Util.getMessage("error.not-leader")); 
/*  92 */         return false;
/*     */       } 
/*  94 */       if (this.checkHasInvites && PlayerSelection.getToFaction(str).isEmpty()) {
/*  95 */         if (!this.silent)
/*  96 */           this.player.sendMessage(Util.getMessage("error.no-invites")); 
/*  97 */         return false;
/*     */       } 
/*  99 */       if (this.checkIsNotPlaying && 
/* 100 */         MatchManager.get().isFactionBusy(str)) {
/* 101 */         if (!this.silent)
/* 102 */           this.player.sendMessage(Util.getMessage("error.faction-playing")); 
/* 103 */         return false;
/*     */       } 
/* 105 */       if (this.checkHasEnoughPlayers && 
/* 106 */         Util.doesNotHaveEnoughPlayers(str)) {
/* 107 */         if (!this.silent)
/* 108 */           this.player.sendMessage(Util.getMessage("error.faction-no-player")); 
/* 109 */         return false;
/*     */       } 
/*     */     } 
/* 112 */     if (this.checkGamemodesSetup && 
/* 113 */       Gamemode.getSetupGamemodes().isEmpty()) {
/* 114 */       if (!this.silent)
/* 115 */         this.player.sendMessage(Util.getMessage("error.no-gamemodes")); 
/* 116 */       return false;
/*     */     } 
/* 118 */     return !Metrics.debugging;
/*     */   }
/*     */   
/*     */   public static boolean check(Player paramPlayer, Condition... paramVarArgs) {
/* 122 */     ConditionsChecker conditionsChecker = new ConditionsChecker(paramPlayer, paramVarArgs);
/* 123 */     return conditionsChecker.check();
/*     */   }
/*     */   
/*     */   public static boolean checkSilently(Player paramPlayer, Condition... paramVarArgs) {
/* 127 */     ConditionsChecker conditionsChecker = new ConditionsChecker(paramPlayer, paramVarArgs);
/* 128 */     conditionsChecker.silent = true;
/* 129 */     return conditionsChecker.check();
/*     */   }
/*     */   
/*     */   public enum Condition {
/* 133 */     HAS_FACTION, HAS_INVITES, IS_LEADER, HAS_ACCEPT_PERMISSION, HAS_DECLINE_PERMISSION, HAS_INVITE_PERMISSION, HAS_ENOUGH_PLAYERS, IS_NOT_PLAYING, GAMEMODES_SETUP;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\ConditionsChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */