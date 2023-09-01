/*     */ package io.github.guipenedo.factionwars.models;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.stats.DatabaseHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentSkipListSet;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class FactionData {
/*  15 */   public static final ConcurrentSkipListSet<FactionData> factions = new ConcurrentSkipListSet<>(new RankComparator());
/*     */   private final String factionId;
/*  17 */   private int wins = 0; private int losts = 0; private int kills = 0; private int deaths = 0;
/*     */   private boolean initialized = false;
/*  19 */   private long cooldown = -1L;
/*     */   
/*     */   public FactionData(String paramString) {
/*  22 */     this.factionId = paramString;
/*     */     
/*  24 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/*  27 */           DatabaseHelper.get().loadFactionStats(FactionData.getFaction(FactionData.this.getFactionId()));
/*     */         }
/*  29 */       }).runTaskAsynchronously((Plugin)FactionWars.get());
/*     */   }
/*     */   
/*     */   public FactionData(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  33 */     this.factionId = paramString;
/*  34 */     this.wins = paramInt1;
/*  35 */     this.losts = paramInt3;
/*  36 */     this.kills = paramInt2;
/*  37 */     this.deaths = paramInt4;
/*  38 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   public static FactionData getFaction(String paramString) {
/*  42 */     for (FactionData factionData : factions) {
/*  43 */       if (factionData.getFactionId().equals(paramString))
/*  44 */         return factionData; 
/*  45 */     }  if (FactionWars.getHandler().getTeamName(paramString) != null) {
/*  46 */       FactionData factionData = new FactionData(paramString);
/*  47 */       factions.add(factionData);
/*  48 */       return factionData;
/*     */     } 
/*  50 */     return null;
/*     */   }
/*     */   
/*     */   public static List<FactionData> getTop(int paramInt) {
/*  54 */     ArrayList<FactionData> arrayList = new ArrayList();
/*     */     
/*  56 */     Iterator<FactionData> iterator = factions.iterator();
/*  57 */     byte b = 0;
/*  58 */     while (b < paramInt && iterator.hasNext()) {
/*  59 */       arrayList.add(iterator.next());
/*  60 */       b++;
/*     */     } 
/*  62 */     return arrayList;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  66 */     return FactionWars.getHandler().getTeamName(this.factionId);
/*     */   }
/*     */   
/*     */   public String getFactionId() {
/*  70 */     return this.factionId;
/*     */   }
/*     */   
/*     */   public int getWins() {
/*  74 */     return this.wins;
/*     */   }
/*     */   
/*     */   public void setWins(int paramInt) {
/*  78 */     factions.remove(this);
/*  79 */     this.wins = paramInt;
/*  80 */     factions.add(this);
/*     */   }
/*     */   
/*     */   public int getLosts() {
/*  84 */     return this.losts;
/*     */   }
/*     */   
/*     */   public void setLosts(int paramInt) {
/*  88 */     factions.remove(this);
/*  89 */     this.losts = paramInt;
/*  90 */     factions.add(this);
/*     */   }
/*     */   
/*     */   public int getKills() {
/*  94 */     return this.kills;
/*     */   }
/*     */   
/*     */   public void setKills(int paramInt) {
/*  98 */     factions.remove(this);
/*  99 */     this.kills = paramInt;
/* 100 */     factions.add(this);
/*     */   }
/*     */   
/*     */   public int getDeaths() {
/* 104 */     return this.deaths;
/*     */   }
/*     */   
/*     */   public void setDeaths(int paramInt) {
/* 108 */     factions.remove(this);
/* 109 */     this.deaths = paramInt;
/* 110 */     factions.add(this);
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 114 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public void setInitialized() {
/* 118 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   public int getRank() {
/* 122 */     return factions.headSet(this).size() + 1;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 126 */     return this.factionId + ": wins=" + this.wins + " kills=" + this.kills + " losses=" + this.losts + " deaths=" + this.deaths;
/*     */   }
/*     */   
/*     */   public boolean isCooldown() {
/* 130 */     return (this.cooldown != -1L && this.cooldown > System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public void setCooldown() {
/* 134 */     long l = FactionWars.getMainConfig().getConfig().getLong("war-cooldown-seconds", -1L);
/* 135 */     if (l != -1L)
/* 136 */       this.cooldown = System.currentTimeMillis() + l * 1000L; 
/*     */   }
/*     */   
/*     */   private static class RankComparator implements Comparator<FactionData> { private RankComparator() {}
/*     */     
/*     */     public int compare(FactionData param1FactionData1, FactionData param1FactionData2) {
/* 142 */       int i = Util.compare(new int[] { param1FactionData2.getWins(), param1FactionData1.getWins(), param1FactionData2.getKills(), param1FactionData1.getKills(), param1FactionData1.getLosts(), param1FactionData2.getLosts(), param1FactionData1.getDeaths(), param1FactionData2.getDeaths() });
/* 143 */       return (i == 0) ? param1FactionData1.getFactionId().compareTo(param1FactionData2.getFactionId()) : i;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\FactionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */