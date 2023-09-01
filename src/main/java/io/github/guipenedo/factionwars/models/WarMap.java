/*     */ package io.github.guipenedo.factionwars.models;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.FactionWarsAddonPlugin;
/*     */ import io.github.guipenedo.factionwars.gamemodes.GamemodeManager;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class WarMap {
/*  15 */   private static Map<String, WarMap> maps = new TreeMap<>();
/*  16 */   public ArrayList<Item> remove = new ArrayList<>();
/*  17 */   private HashSet<UUID> players = new HashSet<>();
/*  18 */   private MatchState matchState = MatchState.EMPTY; private String winner; private double bet; private String f1; private String f2; private String name;
/*     */   private String id;
/*     */   private int timer;
/*     */   private GamemodeManager manager;
/*     */   private String gamemode;
/*     */   
/*     */   public double getBet() {
/*  25 */     return this.bet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBet(double paramDouble) {
/*  33 */     this.bet = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private Map<String, ArrayList<Location>> locations = new HashMap<>();
/*     */   
/*     */   public WarMap(String paramString) {
/*  44 */     ConfigurationSection configurationSection = FactionWars.getMapsConfig().getConfig().getConfigurationSection("maps." + paramString);
/*  45 */     this.id = paramString;
/*  46 */     this.name = configurationSection.getString("name", "?");
/*  47 */     this.gamemode = configurationSection.getString("gamemode", Gamemode.defaultGamemode);
/*  48 */     if (getGamemode() == null)
/*  49 */       return;  if (configurationSection.contains("locations")) {
/*  50 */       ConfigurationSection configurationSection1 = configurationSection.getConfigurationSection("locations");
/*  51 */       for (String str : configurationSection1.getKeys(false)) {
/*  52 */         ArrayList<Location> arrayList = new ArrayList();
/*  53 */         if (configurationSection1.isList(str)) {
/*  54 */           for (String str1 : configurationSection1.getStringList(str))
/*  55 */             arrayList.add(Util.get().stringToLocation(str1)); 
/*     */         } else {
/*  57 */           arrayList.add(Util.get().stringToLocation(configurationSection1.getString(str)));
/*  58 */         }  this.locations.put(str, arrayList);
/*     */       } 
/*     */     } 
/*  61 */     setManager();
/*  62 */     FactionWars.debug("Loaded map " + this.name + " id: " + paramString + " gamemode=" + this.gamemode + " locations=" + this.locations.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WarMap getMap(String paramString) {
/*  71 */     return maps.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, WarMap> getMaps() {
/*  79 */     return maps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void message(String paramString) {
/*  88 */     for (Player player : getPlayerList())
/*  89 */       player.sendMessage(paramString); 
/*     */   }
/*     */   
/*     */   private void setManager() {
/*  93 */     switch (getGamemode().getGamemodeType()) {
/*     */       case KOTH:
/*  95 */         this.manager = (GamemodeManager)new GamemodeKOTH(this, getGamemode().getConfig());
/*     */         break;
/*     */       case TDM:
/*  98 */         this.manager = (GamemodeManager)new GamemodeTDM(this, getGamemode().getConfig());
/*     */         break;
/*     */       case BLITZ:
/* 101 */         this.manager = (GamemodeManager)new GamemodeBLITZ(this, getGamemode().getConfig());
/*     */         break;
/*     */       case CTF:
/* 104 */         this.manager = (GamemodeManager)new GamemodeCTF(this, getGamemode().getConfig());
/*     */         break;
/*     */       case HALO:
/* 107 */         this.manager = (GamemodeManager)new GamemodeHALO(this, getGamemode().getConfig());
/*     */         break;
/*     */       case CUSTOM:
/* 110 */         for (FactionWarsAddonPlugin factionWarsAddonPlugin : FactionWars.get().getAddonPlugins()) {
/* 111 */           GamemodeManager gamemodeManager = factionWarsAddonPlugin.getGamemodeManager(getGamemode().getCustomType(), this, getGamemode().getConfig());
/* 112 */           if (gamemodeManager != null) {
/* 113 */             this.manager = gamemodeManager;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GamemodeManager getManager() {
/* 129 */     return this.manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, ArrayList<Location>> getLocations() {
/* 137 */     return this.locations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gamemode getGamemode() {
/* 145 */     return Gamemode.get(this.gamemode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGamemode(Gamemode paramGamemode) {
/* 153 */     this.gamemode = paramGamemode.getName();
/* 154 */     setManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerSelection getS1() {
/* 162 */     return PlayerSelection.getByFactions(getF1(), getF2());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerSelection getS2() {
/* 170 */     return PlayerSelection.getByFactions(getF2(), getF1());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchState getMatchState() {
/* 178 */     return this.matchState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatchState(MatchState paramMatchState) {
/* 186 */     this.matchState = paramMatchState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFree() {
/* 194 */     return (this.matchState == MatchState.EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getF1() {
/* 202 */     return this.f1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setF1(String paramString) {
/* 210 */     this.f1 = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getF2() {
/* 218 */     return this.f2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setF2(String paramString) {
/* 226 */     this.f2 = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimer() {
/* 234 */     return this.timer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimer(int paramInt) {
/* 242 */     this.timer = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Player> getPlayerList() {
/* 250 */     ArrayList<Player> arrayList = new ArrayList();
/* 251 */     for (UUID uUID : this.players) {
/* 252 */       Player player = FactionWars.get().getServer().getPlayer(uUID);
/* 253 */       if (player != null)
/* 254 */         arrayList.add(player); 
/*     */     } 
/* 256 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashSet<UUID> getPlayers() {
/* 264 */     return this.players;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 272 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 280 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSetup() {
/* 288 */     return isSetup(getManager());
/*     */   }
/*     */   
/*     */   public boolean isSetup(GamemodeManager paramGamemodeManager) {
/* 292 */     ArrayList arrayList = new ArrayList(Arrays.asList((Object[])new String[] { "l1", "l2" }));
/* 293 */     arrayList.addAll(paramGamemodeManager.getLocations(this).keySet());
/* 294 */     for (String str : arrayList) {
/* 295 */       if (!this.locations.containsKey(str) || ((ArrayList)this.locations.get(str)).size() == 0)
/* 296 */         return false; 
/* 297 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLocation(String paramString, Location paramLocation) {
/* 307 */     ArrayList<Location> arrayList = new ArrayList();
/* 308 */     if (this.locations.containsKey(paramString))
/* 309 */       arrayList = this.locations.get(paramString); 
/* 310 */     arrayList.add(paramLocation);
/* 311 */     this.locations.put(paramString, arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveLocations() {
/* 318 */     for (String str : this.locations.keySet()) {
/* 319 */       ArrayList<String> arrayList = new ArrayList();
/* 320 */       for (Location location : this.locations.get(str))
/* 321 */         arrayList.add(Util.get().locationToString(location)); 
/* 322 */       FactionWars.getMapsConfig().getConfig().set("maps." + this.id + ".locations." + str, arrayList);
/*     */     } 
/* 324 */     FactionWars.getMapsConfig().saveConfig();
/*     */   }
/*     */   
/*     */   public String getWinner() {
/* 328 */     return this.winner;
/*     */   }
/*     */   
/*     */   public void setWinner(String paramString) {
/* 332 */     this.winner = paramString;
/*     */   }
/*     */   
/*     */   public String getLoser() {
/* 336 */     if (this.winner == null)
/* 337 */       return null; 
/* 338 */     return this.winner.equals(this.f1) ? this.f2 : this.f1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum MatchState
/*     */   {
/* 349 */     EMPTY,
/* 350 */     WAITINGTP,
/* 351 */     WAITINGSTART,
/* 352 */     PLAYING,
/* 353 */     ENDING;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\WarMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */