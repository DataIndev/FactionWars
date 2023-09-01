/*     */ package io.github.guipenedo.factionwars.managers;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public class ScoreboardManager
/*     */ {
/*     */   private static ScoreboardManager instance;
/*  20 */   private final char[] codes = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r' };
/*  21 */   private ConcurrentHashMap<UUID, Scoreboard> storage = new ConcurrentHashMap<>();
/*     */   
/*     */   public static ScoreboardManager get() {
/*  24 */     if (instance == null)
/*  25 */       instance = new ScoreboardManager(); 
/*  26 */     return instance;
/*     */   }
/*     */   
/*     */   public void clear(Player paramPlayer) {
/*  30 */     if (this.storage.containsKey(paramPlayer.getUniqueId())) {
/*  31 */       paramPlayer.setScoreboard(this.storage.get(paramPlayer.getUniqueId()));
/*  32 */       this.storage.remove(paramPlayer.getUniqueId());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void display(ArrayList<Player> paramArrayList, WarMap paramWarMap, String... paramVarArgs) {
/*  37 */     for (Player player : paramArrayList)
/*  38 */       display(player, paramWarMap, paramVarArgs); 
/*     */   }
/*     */   
/*     */   public void display(Player paramPlayer, WarMap paramWarMap, String... paramVarArgs) {
/*  42 */     if (!this.storage.containsKey(paramPlayer.getUniqueId())) {
/*  43 */       this.storage.put(paramPlayer.getUniqueId(), paramPlayer.getScoreboard());
/*  44 */       paramPlayer.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
/*     */     } 
/*  46 */     Scoreboard scoreboard = paramPlayer.getScoreboard();
/*  47 */     String str = "factionwars";
/*  48 */     Objective objective = scoreboard.getObjective(str);
/*  49 */     if (objective == null) {
/*     */       
/*  51 */       objective = scoreboard.registerNewObjective(str, "dummy");
/*  52 */       objective.setDisplayName(truncate(format(Util.getPlainMessage("scoreboard.title"), paramWarMap), 32));
/*     */       
/*  54 */       for (byte b = 0; b < this.codes.length; b++) {
/*  55 */         Team team = scoreboard.getTeam("factwt" + b);
/*  56 */         if (team == null)
/*  57 */           team = scoreboard.registerNewTeam("factwt" + b); 
/*  58 */         team.addEntry("§" + this.codes[b]);
/*     */       } 
/*     */     } 
/*  61 */     Team team1 = scoreboard.getTeam("factwtpl1");
/*  62 */     Team team2 = scoreboard.getTeam("factwtpl2");
/*  63 */     if (team1 == null) {
/*  64 */       FactionWars.debug("Creating team 1");
/*  65 */       team1 = scoreboard.registerNewTeam("factwtpl1");
/*  66 */       team1.setPrefix(Util.getTeam1Color().toString());
/*     */       try {
/*  68 */         team1.setColor(Util.getTeam1Color());
/*  69 */       } catch (Throwable throwable) {}
/*  70 */       for (Player player : paramWarMap.getPlayerList()) {
/*  71 */         if (paramWarMap.getF1().equals(FactionWars.getHandler().getPlayerTeam(player))) {
/*  72 */           FactionWars.debug("adding " + player.getName() + " to team 1");
/*  73 */           team1.addEntry(player.getName());
/*     */         } 
/*     */       } 
/*  76 */     }  if (team2 == null) {
/*  77 */       FactionWars.debug("Creating team 2");
/*  78 */       team2 = scoreboard.registerNewTeam("factwtpl2");
/*  79 */       team2.setPrefix(Util.getTeam2Color().toString());
/*     */       try {
/*  81 */         team2.setColor(Util.getTeam2Color());
/*  82 */       } catch (Throwable throwable) {}
/*  83 */       for (Player player : paramWarMap.getPlayerList()) {
/*  84 */         if (paramWarMap.getF2().equals(FactionWars.getHandler().getPlayerTeam(player))) {
/*  85 */           FactionWars.debug("adding " + player.getName() + " to team 2");
/*  86 */           team2.addEntry(player.getName());
/*     */         } 
/*     */       } 
/*  89 */     }  if (objective.getDisplaySlot() == null) {
/*  90 */       objective.setDisplaySlot(DisplaySlot.SIDEBAR);
/*     */     }
/*  92 */     ArrayList<String> arrayList = new ArrayList();
/*  93 */     for (String str1 : FactionWars.getMessagesConfig().getConfig().getStringList("scoreboard.top-lines")) {
/*  94 */       arrayList.add(format(str1, paramWarMap));
/*     */     }
/*  96 */     Collections.addAll(arrayList, paramVarArgs);
/*  97 */     for (String str1 : FactionWars.getMessagesConfig().getConfig().getStringList("scoreboard.bottom-lines"))
/*  98 */       arrayList.add(format(str1, paramWarMap));  int i;
/*  99 */     for (i = arrayList.size(); i < this.codes.length; i++) {
/* 100 */       if (objective.getScore("§" + this.codes[i]).isScoreSet())
/* 101 */         scoreboard.resetScores("§" + this.codes[i]); 
/* 102 */     }  i = arrayList.size() - 1;
/* 103 */     for (String str1 : arrayList) {
/* 104 */       Team team = scoreboard.getTeam("factwt" + i);
/* 105 */       String str2 = truncate(str1), str3 = (str1.length() > 16) ? truncate((str1.startsWith("§") ? str1.substring(0, 2) : "§f") + str1.substring(16)) : "";
/* 106 */       if (!team.getPrefix().equals(str2) || !team.getSuffix().equals(str3)) {
/* 107 */         team.setSuffix(str3);
/* 108 */         team.setPrefix(str2);
/*     */       } 
/*     */       
/* 111 */       if (!objective.getScore("§" + this.codes[i]).isScoreSet())
/* 112 */         objective.getScore("§" + this.codes[i]).setScore(i); 
/* 113 */       i--;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String format(String paramString, WarMap paramWarMap) {
/* 118 */     return paramString.replaceAll("&", "§").replaceAll("%gamemode%", paramWarMap.getGamemode().getName()).replaceAll("%map%", paramWarMap.getName()).replaceAll("%timer%", Util.formatTimer(paramWarMap.getTimer()));
/*     */   }
/*     */   
/*     */   private String truncate(String paramString, int paramInt) {
/* 122 */     return paramString.substring(0, Math.min(paramString.length(), paramInt));
/*     */   }
/*     */   
/*     */   private String truncate(String paramString) {
/* 126 */     return truncate(paramString, 16);
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\ScoreboardManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */