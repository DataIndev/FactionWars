/*     */ package io.github.guipenedo.factionwars.api;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ 
/*     */ 
/*     */ public abstract class TeamHandler
/*     */   implements Listener
/*     */ {
/*     */   String name;
/*     */   
/*     */   public String getName() {
/*  15 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TeamHandler(String paramString) {
/*  21 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getTeamName(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getTeamIdByName(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<Player> getOnlinePlayers(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Relation getRelationBetween(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract String getBankName(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void sendMessage(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<String> getAllTeams();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<Player> getMembersWithRole(String paramString, Role paramRole);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getPlayerTeam(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBalance(String paramString) {
/* 113 */     return FactionWars.getEcon().getBalance(getBankName(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeBalance(String paramString, double paramDouble) {
/* 124 */     if (paramDouble < 0.0D) {
/* 125 */       FactionWars.getEcon().withdraw(getBankName(paramString), -paramDouble);
/* 126 */     } else if (paramDouble > 0.0D) {
/* 127 */       FactionWars.getEcon().deposit(getBankName(paramString), paramDouble);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public enum Relation
/*     */   {
/* 134 */     ALLY,
/* 135 */     ENEMY,
/* 136 */     NEUTRAL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Role
/*     */   {
/* 143 */     ADMIN,
/* 144 */     MODERATOR,
/* 145 */     NORMAL,
/* 146 */     OTHER;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\TeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */