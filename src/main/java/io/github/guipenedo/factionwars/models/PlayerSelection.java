/*     */ package io.github.guipenedo.factionwars.models;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class PlayerSelection
/*     */ {
/*  11 */   private static ArrayList<PlayerSelection> playerSelections = new ArrayList<>();
/*     */   
/*     */   private UUID playerSelecting;
/*     */   
/*     */   private String from;
/*  16 */   private ArrayList<UUID> onlinePlayers = new ArrayList<>(); private String to; private Gamemode gamemode; private boolean confirmed = false;
/*  17 */   private ArrayList<UUID> playersSelected = new ArrayList<>();
/*     */   private int limit;
/*     */   private boolean locked = false;
/*     */   private Double bet;
/*     */   
/*     */   public PlayerSelection(UUID paramUUID, int paramInt, String paramString1, String paramString2) {
/*  23 */     this(null, paramUUID, paramInt, paramString1, paramString2, null);
/*     */   }
/*     */   
/*     */   public PlayerSelection(Gamemode paramGamemode, UUID paramUUID, int paramInt, String paramString1, String paramString2, Double paramDouble) {
/*  27 */     this.gamemode = paramGamemode;
/*  28 */     this.limit = paramInt;
/*  29 */     this.from = paramString1;
/*  30 */     this.to = paramString2;
/*  31 */     this.bet = paramDouble;
/*  32 */     this.playerSelecting = paramUUID;
/*  33 */     updateOnlinePlayers();
/*     */   }
/*     */   
/*     */   public static ArrayList<PlayerSelection> getPlayerSelections() {
/*  37 */     return playerSelections;
/*     */   }
/*     */   
/*     */   public static PlayerSelection getBySelector(UUID paramUUID) {
/*  41 */     if (paramUUID == null) return null; 
/*  42 */     for (PlayerSelection playerSelection : playerSelections) {
/*  43 */       if (playerSelection.getPlayerSelecting() != null && playerSelection.getPlayerSelecting().equals(paramUUID))
/*  44 */         return playerSelection; 
/*  45 */     }  return null;
/*     */   }
/*     */   
/*     */   public static PlayerSelection getByFactions(String paramString1, String paramString2) {
/*  49 */     for (PlayerSelection playerSelection : playerSelections) {
/*  50 */       if (playerSelection.getFrom().equals(paramString1) && playerSelection.getTo().equals(paramString2))
/*  51 */         return playerSelection; 
/*  52 */     }  return null;
/*     */   }
/*     */   
/*     */   public static ArrayList<PlayerSelection> getToFaction(String paramString) {
/*  56 */     ArrayList<PlayerSelection> arrayList = new ArrayList();
/*  57 */     for (PlayerSelection playerSelection : playerSelections) {
/*  58 */       if (playerSelection.getTo().equals(paramString))
/*  59 */         arrayList.add(playerSelection); 
/*  60 */     }  return arrayList;
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/*  64 */     return this.locked;
/*     */   }
/*     */   
/*     */   public void setLocked(boolean paramBoolean) {
/*  68 */     this.locked = paramBoolean;
/*     */   }
/*     */   
/*     */   public Gamemode getGamemode() {
/*  72 */     return this.gamemode;
/*     */   }
/*     */   
/*     */   public void setGamemode(Gamemode paramGamemode) {
/*  76 */     this.gamemode = paramGamemode;
/*     */   }
/*     */   
/*     */   public boolean isConfirmed() {
/*  80 */     return this.confirmed;
/*     */   }
/*     */   
/*     */   public void setConfirmed(boolean paramBoolean) {
/*  84 */     this.confirmed = paramBoolean;
/*     */   }
/*     */   
/*     */   public String getFrom() {
/*  88 */     return this.from;
/*     */   }
/*     */   
/*     */   public String getTo() {
/*  92 */     return this.to;
/*     */   }
/*     */   
/*     */   public int getLimit() {
/*  96 */     return this.limit;
/*     */   }
/*     */   
/*     */   public void updateOnlinePlayers() {
/* 100 */     this.onlinePlayers.clear();
/* 101 */     for (Player player : Util.getOnlineOptedin(getFrom()))
/* 102 */       this.onlinePlayers.add(player.getUniqueId()); 
/* 103 */     this.onlinePlayers.removeAll(this.playersSelected);
/* 104 */     this.onlinePlayers.remove(this.playerSelecting);
/*     */   }
/*     */   
/*     */   public ArrayList<UUID> getOnlinePlayers() {
/* 108 */     return this.onlinePlayers;
/*     */   }
/*     */   
/*     */   public UUID getPlayerSelecting() {
/* 112 */     return this.playerSelecting;
/*     */   }
/*     */   
/*     */   public ArrayList<UUID> getPlayersSelected() {
/* 116 */     return this.playersSelected;
/*     */   }
/*     */   
/*     */   private ArrayList<UUID> getAllUuidsSelected() {
/* 120 */     ArrayList<UUID> arrayList = new ArrayList<>(getPlayersSelected());
/* 121 */     if (this.playerSelecting != null)
/* 122 */       arrayList.add(this.playerSelecting); 
/* 123 */     return arrayList;
/*     */   }
/*     */   
/*     */   public ArrayList<Player> getAllPlayersSelected() {
/* 127 */     ArrayList<Player> arrayList = new ArrayList();
/* 128 */     for (UUID uUID : getAllUuidsSelected()) {
/* 129 */       Player player = FactionWars.get().getServer().getPlayer(uUID);
/* 130 */       if (player != null) {
/* 131 */         arrayList.add(player); continue;
/*     */       } 
/* 133 */       this.playersSelected.remove(uUID);
/*     */     } 
/* 135 */     return arrayList;
/*     */   }
/*     */   
/*     */   public Double getBet() {
/* 139 */     return this.bet;
/*     */   }
/*     */   
/*     */   public void setBet(Double paramDouble) {
/* 143 */     this.bet = paramDouble;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\PlayerSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */