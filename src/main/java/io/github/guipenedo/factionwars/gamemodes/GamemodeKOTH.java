/*     */ package io.github.guipenedo.factionwars.gamemodes;
/*     */ import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.managers.ScoreboardManager;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class GamemodeKOTH implements GamemodeManager {
/*  23 */   private final String msg = "gamemodes.koth."; private final String map;
/*  24 */   private int conquerTime = 20;
/*  25 */   private int secs = 0;
/*  26 */   private int range = 3;
/*  27 */   private int contestTime = 10;
/*  28 */   private int pointsSecond = 1;
/*  29 */   private int pointsWin = 120;
/*     */   private int score1;
/*     */   private int score2;
/*     */   private int n;
/*     */   private int h;
/*  34 */   private int[] dirx = new int[] { 0, 1, -1, 1, -1, 1, 0, -1, 0 };
/*  35 */   private int[] dirz = new int[] { 0, 1, -1, -1, 1, 0, 1, 0, -1 }; private BukkitTask pointsTask; private BukkitTask conquerTask;
/*     */   private BukkitTask contestTask;
/*     */   private Location[] hills;
/*     */   private Hologram hologram;
/*     */   private String conqueror;
/*  40 */   private ArrayList<BlockState> bs = new ArrayList<>();
/*     */   
/*     */   public GamemodeKOTH(WarMap paramWarMap, ConfigurationSection paramConfigurationSection) {
/*  43 */     this.map = paramWarMap.getId();
/*  44 */     this.conquerTime = paramConfigurationSection.getInt("gamemode-settings.conquer-time", this.conquerTime);
/*  45 */     this.contestTime = paramConfigurationSection.getInt("gamemode-settings.contest-time", this.contestTime);
/*  46 */     this.range = paramConfigurationSection.getInt("gamemode-settings.range", this.range);
/*  47 */     this.pointsSecond = paramConfigurationSection.getInt("gamemode-settings.points-per-second", this.pointsSecond);
/*  48 */     this.pointsWin = paramConfigurationSection.getInt("gamemode-settings.points-to-win", this.pointsWin);
/*  49 */     updateLocations(paramWarMap);
/*     */   }
/*     */   
/*     */   private void updateHologram() {
/*  53 */     if (!FactionWars.get().getServer().getPluginManager().isPluginEnabled("HolographicDisplays"))
/*  54 */       return;  Location location = this.hills[this.h].clone();
/*  55 */     location.setY(location.getY() + 4.0D);
/*  56 */     if (this.hologram == null || this.hologram.isDeleted())
/*  57 */       this.hologram = HologramsAPI.createHologram((Plugin)FactionWars.get(), location); 
/*  58 */     this.hologram.clearLines();
/*  59 */     this.hologram.appendTextLine("§6♚ ♚ ♚");
/*  60 */     this.hologram.appendTextLine(Util.getPlainMessage("gamemodes.koth.holograms.status") + ": §l" + ((this.conqueror == null) ? (notRunning(this.conquerTask) ? Util.getPlainMessage("gamemodes.koth.holograms.unclaimed") : Util.getPlainMessage("gamemodes.koth.holograms.conquering")) : (notRunning(this.contestTask) ? Util.getPlainMessage("gamemodes.koth.holograms.conquered") : Util.getPlainMessage("gamemodes.koth.holograms.contested"))));
/*  61 */     String str = getStatus();
/*  62 */     if (!str.equals(""))
/*  63 */       this.hologram.appendTextLine(str); 
/*  64 */     this.hologram.appendTextLine(Util.getPlainMessage("gamemodes.koth.holograms.king") + ": §b" + ((this.conqueror != null) ? FactionWars.getHandler().getTeamName(this.conqueror) : Util.getPlainMessage("gamemodes.koth.holograms.none")));
/*     */   }
/*     */   
/*     */   public void updateLocations(WarMap paramWarMap) {
/*  68 */     if (paramWarMap.isSetup(this)) {
/*  69 */       this.n = ((ArrayList)paramWarMap.getLocations().get("hill")).size();
/*  70 */       this.hills = new Location[this.n];
/*  71 */       for (byte b = 0; b < this.n; b++) {
/*  72 */         this.hills[b] = ((ArrayList<Location>)paramWarMap.getLocations().get("hill")).get(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startMatch() {
/*  78 */     this.conqueror = null;
/*  79 */     ArrayList<?> arrayList = new ArrayList(Arrays.asList((Object[])this.hills));
/*  80 */     Collections.shuffle(arrayList);
/*  81 */     this.hills = arrayList.<Location>toArray(new Location[this.n]);
/*  82 */     this.score1 = 0;
/*  83 */     this.score2 = 0;
/*  84 */     this.h = 0;
/*  85 */     spawnHill();
/*     */   }
/*     */   
/*     */   private ArrayList<Location> getGoldLocations(Location paramLocation) {
/*  89 */     ArrayList<Location> arrayList = new ArrayList();
/*  90 */     double d1 = paramLocation.getX(), d2 = paramLocation.getZ();
/*  91 */     for (byte b = 0; b < this.dirx.length; b++) {
/*  92 */       paramLocation.setX(d1 + this.dirx[b]);
/*  93 */       paramLocation.setZ(d2 + this.dirz[b]);
/*  94 */       arrayList.add(paramLocation.clone());
/*     */     } 
/*  96 */     return arrayList;
/*     */   }
/*     */   
/*     */   private void spawnHill() {
/* 100 */     Location location = this.hills[this.h].clone();
/* 101 */     for (Location location1 : getGoldLocations(location.clone())) {
/* 102 */       this.bs.add(location1.getBlock().getState());
/* 103 */       location1.getBlock().setType(Material.GOLD_BLOCK);
/*     */     } 
/* 105 */     location.setY(location.getY() + 1.0D);
/* 106 */     this.bs.add(location.getBlock().getState());
/* 107 */     location.getBlock().setType(Material.BEACON);
/* 108 */     while (location.getY() < location.getWorld().getMaxHeight()) {
/* 109 */       location.setY(location.getY() + 1.0D);
/* 110 */       if (!location.getBlock().isEmpty() && !location.getBlock().getType().isTransparent()) {
/* 111 */         this.bs.add(location.getBlock().getState());
/* 112 */         location.getBlock().setType(Material.AIR);
/*     */       } 
/*     */     } 
/* 115 */     if (this.hologram != null)
/* 116 */       this.hologram.delete(); 
/* 117 */     updateHologram();
/*     */   }
/*     */ 
/*     */   
/*     */   public void timeOut() {
/* 122 */     MatchManager.get().tied(WarMap.getMap(this.map));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScoreboard() {
/* 127 */     WarMap warMap = WarMap.getMap(this.map);
/* 128 */     String str = getStatus();
/* 129 */     ScoreboardManager.get().display(warMap.getPlayerList(), warMap, new String[] {
/* 130 */           Util.getPlainMessage("gamemodes.koth.scoreboard.points"), ((this.conqueror != null && this.conqueror
/* 131 */           .equals(warMap.getF1())) ? "§6♚ " : "") + Util.getTeam1Color() + FactionWars.getHandler().getTeamName(warMap.getF1()), this.score1 + "/" + this.pointsWin, ((this.conqueror != null && this.conqueror
/*     */           
/* 133 */           .equals(warMap.getF2())) ? "§6♚ " : "") + Util.getTeam2Color() + FactionWars.getHandler().getTeamName(warMap.getF2()), this.score2 + "/" + this.pointsWin, "", 
/*     */ 
/*     */           
/* 136 */           Util.getPlainMessage("gamemodes.koth.scoreboard.status"), (this.conqueror == null) ? (
/* 137 */           notRunning(this.conquerTask) ? Util.getPlainMessage("gamemodes.koth.scoreboard.unclaimed") : Util.getPlainMessage("gamemodes.koth.scoreboard.conquering")) : (notRunning(this.contestTask) ? Util.getPlainMessage("gamemodes.koth.scoreboard.conquered") : Util.getPlainMessage("gamemodes.koth.scoreboard.contested")), str
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void kill(UUID paramUUID) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void death(UUID paramUUID) {}
/*     */ 
/*     */   
/*     */   public void reset() {
/* 151 */     if (this.contestTask != null)
/* 152 */       this.contestTask.cancel(); 
/* 153 */     if (this.conquerTask != null)
/* 154 */       this.conquerTask.cancel(); 
/* 155 */     if (this.pointsTask != null)
/* 156 */       this.pointsTask.cancel(); 
/* 157 */     removeHill();
/* 158 */     if (this.hologram != null) {
/* 159 */       this.hologram.delete();
/* 160 */       this.hologram = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRespawn(UUID paramUUID) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(PlayerMoveEvent paramPlayerMoveEvent) {
/* 171 */     if (paramPlayerMoveEvent.getTo().distanceSquared(this.hills[this.h]) > (this.range * this.range))
/* 172 */       return;  String str = FactionWars.getHandler().getPlayerTeam(paramPlayerMoveEvent.getPlayer());
/* 173 */     if (this.h == -1 || (this.conqueror != null && this.conqueror.equals(str)))
/* 174 */       return;  if (this.conqueror == null) {
/* 175 */       if (notRunning(this.conquerTask))
/* 176 */         this.conquerTask = (new ConquerTask(str)).runTaskTimer((Plugin)FactionWars.get(), 0L, 20L); 
/* 177 */     } else if (notRunning(this.contestTask)) {
/* 178 */       this.contestTask = (new ContestTask(str)).runTaskTimer((Plugin)FactionWars.get(), 0L, 20L);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, String> getLocations(WarMap paramWarMap) {
/* 183 */     return (Map<String, String>)ImmutableMap.of("hill", "Add hill locations by doing §c/fw setlocation " + paramWarMap.getId() + " hill\nYou can (and should!) add multiple hills.");
/*     */   }
/*     */   
/*     */   private boolean notRunning(BukkitTask paramBukkitTask) {
/* 187 */     return (paramBukkitTask == null || (!FactionWars.get().getServer().getScheduler().isCurrentlyRunning(paramBukkitTask.getTaskId()) && !FactionWars.get().getServer().getScheduler().isQueued(paramBukkitTask.getTaskId())));
/*     */   }
/*     */   
/*     */   private void contest() {
/* 191 */     this.conqueror = null;
/* 192 */     removeHill();
/* 193 */     this.h = (this.h + 1) % this.n;
/* 194 */     spawnHill();
/* 195 */     if (this.pointsTask != null) this.pointsTask.cancel(); 
/* 196 */     WarMap.getMap(this.map).message(Util.getMessage("gamemodes.koth.contested"));
/*     */   }
/*     */   
/*     */   private void removeHill() {
/* 200 */     for (BlockState blockState : this.bs)
/* 201 */       blockState.update(true); 
/* 202 */     this.bs.clear();
/*     */   }
/*     */   
/*     */   private void conquer(String paramString) {
/* 206 */     this.conqueror = paramString;
/* 207 */     this.pointsTask = (new PointsTask()).runTaskTimer((Plugin)FactionWars.get(), 0L, 20L);
/* 208 */     updateHologram();
/* 209 */     WarMap.getMap(this.map).message(Util.getMessage("gamemodes.koth.conquered", Util.getVars(new Object[] { "conqueror", FactionWars.getHandler().getTeamName(paramString) })));
/*     */   }
/*     */   
/*     */   private String getStatus() {
/* 213 */     StringBuilder stringBuilder = new StringBuilder();
/* 214 */     if (!notRunning(this.conquerTask)) {
/* 215 */       stringBuilder = new StringBuilder("§c");
/* 216 */       int i = this.secs * 10 / this.conquerTime;
/* 217 */       for (byte b = 0; b < 10; b++) {
/* 218 */         if (b == i) stringBuilder.append("§8"); 
/* 219 */         stringBuilder.append("█");
/*     */       } 
/* 221 */     } else if (!notRunning(this.contestTask)) {
/* 222 */       stringBuilder = new StringBuilder("§4");
/* 223 */       int i = this.secs * 10 / this.contestTime;
/* 224 */       for (byte b = 0; b < 10; b++) {
/* 225 */         if (b == i) stringBuilder.append("§8"); 
/* 226 */         stringBuilder.append("█");
/*     */       } 
/*     */     } 
/* 229 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private class ContestTask
/*     */     extends BukkitRunnable {
/*     */     final String contester;
/*     */     
/*     */     ContestTask(String param1String) {
/* 237 */       GamemodeKOTH.this.secs = 0;
/* 238 */       this.contester = param1String;
/* 239 */       WarMap.getMap(GamemodeKOTH.this.map).message(Util.getMessage("gamemodes.koth.contesting", Util.getVars(new Object[] { "contester", FactionWars.getHandler().getTeamName(param1String) })));
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 244 */       if (!check()) {
/* 245 */         cancel();
/* 246 */         GamemodeKOTH.this.contestTask = null;
/*     */       } 
/* 248 */       if (GamemodeKOTH.this.secs++ == GamemodeKOTH.this.contestTime) {
/* 249 */         GamemodeKOTH.this.contest();
/* 250 */         cancel();
/* 251 */         GamemodeKOTH.this.contestTask = null;
/*     */       } 
/* 253 */       GamemodeKOTH.this.updateHologram();
/*     */     }
/*     */     
/*     */     private boolean check() {
/* 257 */       for (Player player : WarMap.getMap(GamemodeKOTH.this.map).getPlayerList()) {
/* 258 */         if (!player.isDead() && this.contester.equals(FactionWars.getHandler().getPlayerTeam(player)) && player.getLocation().distanceSquared(GamemodeKOTH.this.hills[GamemodeKOTH.this.h]) <= (GamemodeKOTH.this.range * GamemodeKOTH.this.range))
/* 259 */           return true; 
/* 260 */       }  return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ConquerTask
/*     */     extends BukkitRunnable {
/*     */     final String conqueror;
/*     */     
/*     */     ConquerTask(String param1String) {
/* 269 */       GamemodeKOTH.this.secs = 0;
/* 270 */       this.conqueror = param1String;
/* 271 */       WarMap.getMap(GamemodeKOTH.this.map).message(Util.getMessage("gamemodes.koth.conquering", Util.getVars(new Object[] { "conqueror", FactionWars.getHandler().getTeamName(param1String) })));
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 276 */       if (!check()) {
/* 277 */         cancel();
/* 278 */         GamemodeKOTH.this.conquerTask = null;
/*     */       } 
/* 280 */       if (GamemodeKOTH.this.secs++ == GamemodeKOTH.this.conquerTime) {
/* 281 */         GamemodeKOTH.this.conquer(this.conqueror);
/* 282 */         cancel();
/* 283 */         GamemodeKOTH.this.conquerTask = null;
/*     */       } 
/* 285 */       GamemodeKOTH.this.updateHologram();
/*     */     }
/*     */     
/*     */     private boolean check() {
/* 289 */       for (Player player : WarMap.getMap(GamemodeKOTH.this.map).getPlayerList()) {
/* 290 */         if (!player.isDead() && this.conqueror.equals(FactionWars.getHandler().getPlayerTeam(player)) && player.getLocation().distanceSquared(GamemodeKOTH.this.hills[GamemodeKOTH.this.h]) <= (GamemodeKOTH.this.range * GamemodeKOTH.this.range))
/* 291 */           return true; 
/* 292 */       }  return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private class PointsTask extends BukkitRunnable {
/*     */     private PointsTask() {}
/*     */     
/*     */     public void run() {
/* 300 */       WarMap warMap = WarMap.getMap(GamemodeKOTH.this.map);
/* 301 */       if (GamemodeKOTH.this.conqueror == null) {
/* 302 */         cancel();
/*     */         return;
/*     */       } 
/* 305 */       if (GamemodeKOTH.this.score1 >= GamemodeKOTH.this.pointsWin || GamemodeKOTH.this.score2 >= GamemodeKOTH.this.pointsWin)
/* 306 */       { GamemodeKOTH.this.reset();
/* 307 */         MatchManager.get().won(warMap, (GamemodeKOTH.this.score1 > GamemodeKOTH.this.score2) ? warMap.getF1() : warMap.getF2());
/* 308 */         cancel(); }
/*     */       
/* 310 */       else if (GamemodeKOTH.this.conqueror.equals(warMap.getF1())) { GamemodeKOTH.this.score1 = GamemodeKOTH.this.score1 + GamemodeKOTH.this.pointsSecond; }
/* 311 */       else { GamemodeKOTH.this.score2 = GamemodeKOTH.this.score2 + GamemodeKOTH.this.pointsSecond; }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeKOTH.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */