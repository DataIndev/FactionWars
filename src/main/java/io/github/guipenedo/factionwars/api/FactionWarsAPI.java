/*     */ package io.github.guipenedo.factionwars.api;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.api.exception.InvalidMapException;
/*     */ import io.github.guipenedo.factionwars.handler.TeamHandlerListener;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.managers.ScoreboardManager;
/*     */ import io.github.guipenedo.factionwars.models.Gamemode;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FactionWarsAPI
/*     */ {
/*     */   public static void showScoreboard(WarMap paramWarMap, String... paramVarArgs) {
/*  28 */     ScoreboardManager.get().display(paramWarMap.getPlayerList(), paramWarMap, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showScoreboard(Player paramPlayer, WarMap paramWarMap, String... paramVarArgs) {
/*  39 */     ScoreboardManager.get().display(paramPlayer, paramWarMap, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void matchWon(WarMap paramWarMap, String paramString) {
/*  49 */     MatchManager.get().won(paramWarMap, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void matchTied(WarMap paramWarMap) {
/*  58 */     MatchManager.get().tied(paramWarMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startMatchOnMap(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2, String paramString3, double paramDouble) {
/*  74 */     startMatchOnMap(paramList1, paramString1, paramList2, paramString2, WarMap.getMap(paramString3), paramDouble);
/*     */   }
/*     */   
/*     */   private static void startMatchOnMap(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2, WarMap paramWarMap, double paramDouble) {
/*  78 */     PlayerSelection playerSelection1 = new PlayerSelection(null, 0, paramString1, paramString2), playerSelection2 = new PlayerSelection(null, 0, paramString2, paramString1);
/*  79 */     playerSelection1.getPlayersSelected().addAll(paramList1);
/*  80 */     playerSelection2.getPlayersSelected().addAll(paramList2);
/*  81 */     playerSelection1.setBet(Double.valueOf(paramDouble));
/*  82 */     playerSelection2.setBet(Double.valueOf(paramDouble));
/*  83 */     playerSelection1.setConfirmed(true);
/*  84 */     playerSelection2.setConfirmed(true);
/*  85 */     if (paramWarMap == null || !paramWarMap.isSetup() || !paramWarMap.isFree())
/*  86 */       throw new InvalidMapException(); 
/*  87 */     MatchManager.get().start(playerSelection1, playerSelection2, paramWarMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startMatchOnMap(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2, String paramString3) {
/* 102 */     startMatchOnMap(paramList1, paramString1, paramList2, paramString2, paramString3, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startMatch(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2, String paramString3, double paramDouble) {
/* 118 */     Gamemode gamemode = Gamemode.get(paramString3);
/* 119 */     if (gamemode == null)
/* 120 */       throw new InvalidMapException(); 
/* 121 */     WarMap warMap = MatchManager.get().findFreeArena(gamemode);
/* 122 */     startMatchOnMap(paramList1, paramString1, paramList2, paramString2, warMap, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startMatch(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2, String paramString3) {
/* 137 */     startMatch(paramList1, paramString1, paramList2, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startMatch(List<UUID> paramList1, String paramString1, List<UUID> paramList2, String paramString2) {
/* 151 */     startMatch(paramList1, paramString1, paramList2, paramString2, Gamemode.getDefault().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTeamCreate(String paramString) {
/* 160 */     TeamHandlerListener.onTeamCreate(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTeamDelete(String paramString) {
/* 169 */     TeamHandlerListener.onTeamDelete(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTeamChange(Player paramPlayer) {
/* 178 */     TeamHandlerListener.onTeamChange(paramPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTeamRename(String paramString) {
/* 187 */     TeamHandlerListener.onTeamRename(paramString);
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\FactionWarsAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */