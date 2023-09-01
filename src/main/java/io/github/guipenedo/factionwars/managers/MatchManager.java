/*     */ package io.github.guipenedo.factionwars.managers;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.events.FactionWarsWarEndEvent;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import io.github.guipenedo.factionwars.helpers.Rewards;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import io.github.guipenedo.factionwars.models.Kit;
/*     */ import io.github.guipenedo.factionwars.models.PlayerData;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import io.github.guipenedo.factionwars.stats.DatabaseHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class MatchManager {
/*  24 */   private final String gameTime = "end-game.timer", invincibleTime = "start-game.invincible-tag", waitTime = "start-game.wait-time", waitBeforeTpTime = "start-game.time-before-teleport", endTpTime = "end-game.teleport-time";
/*     */   private static MatchManager instance;
/*     */   
/*     */   public static MatchManager get() {
/*  28 */     if (instance == null)
/*  29 */       instance = new MatchManager(); 
/*  30 */     return instance;
/*     */   }
/*     */   
/*     */   public WarMap findFreeArena(Gamemode paramGamemode) {
/*  34 */     FactionWars.debug("Finding free arenas for gamemode " + paramGamemode.getName());
/*  35 */     ArrayList<?> arrayList = new ArrayList(WarMap.getMaps().values());
/*  36 */     long l = System.nanoTime();
/*  37 */     Collections.shuffle(arrayList, new Random(l));
/*  38 */     for (WarMap warMap : arrayList) {
/*  39 */       if (warMap.isSetup() && warMap.getGamemode().equals(paramGamemode) && warMap.isFree())
/*  40 */         return warMap; 
/*     */     } 
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   public void start(PlayerSelection paramPlayerSelection1, PlayerSelection paramPlayerSelection2, WarMap paramWarMap) {
/*  46 */     if (paramWarMap == null || !paramWarMap.isSetup() || !paramWarMap.isSetup() || paramPlayerSelection1 == null || paramPlayerSelection2 == null) {
/*     */       return;
/*     */     }
/*  49 */     FactionWarsWarStartEvent factionWarsWarStartEvent = new FactionWarsWarStartEvent(paramPlayerSelection1, paramPlayerSelection2, paramWarMap);
/*  50 */     FactionWars.get().getServer().getPluginManager().callEvent((Event)factionWarsWarStartEvent);
/*  51 */     if (factionWarsWarStartEvent.isCancelled()) {
/*  52 */       PlayerSelection.getPlayerSelections().remove(paramPlayerSelection1);
/*  53 */       PlayerSelection.getPlayerSelections().remove(paramPlayerSelection2);
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     FactionWars.debug("Starting war! f1: " + FactionWars.getHandler().getTeamName(paramPlayerSelection1.getFrom()) + " f2: " + FactionWars.getHandler().getTeamName(paramPlayerSelection2.getFrom()));
/*  58 */     paramWarMap.setF1(paramPlayerSelection1.getFrom());
/*  59 */     paramWarMap.setF2(paramPlayerSelection2.getFrom());
/*  60 */     paramWarMap.setBet(paramPlayerSelection1.getBet().doubleValue());
/*     */     
/*  62 */     ArrayList<PlayerSelection> arrayList = new ArrayList();
/*  63 */     String str1 = paramPlayerSelection1.getFrom(), str2 = paramPlayerSelection2.getFrom();
/*  64 */     for (PlayerSelection playerSelection : PlayerSelection.getPlayerSelections()) {
/*  65 */       String str3 = playerSelection.getFrom(), str4 = playerSelection.getTo();
/*  66 */       if ((str3.equals(str1) || str3.equals(str2) || str4.equals(str1) || str4.equals(str2)) && !playerSelection.equals(paramPlayerSelection1) && !playerSelection.equals(paramPlayerSelection2)) {
/*  67 */         arrayList.add(playerSelection);
/*  68 */         if (!playerSelection.isConfirmed()) {
/*  69 */           Player player = FactionWars.get().getServer().getPlayer(playerSelection.getPlayerSelecting());
/*  70 */           if (player != null) {
/*  71 */             player.closeInventory();
/*     */           }
/*  73 */           player.sendMessage("§cThat faction has now accepted a war.");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     paramWarMap.remove.clear();
/*  79 */     PlayerSelection.getPlayerSelections().removeAll(arrayList);
/*     */     
/*  81 */     paramWarMap.setMatchState(WarMap.MatchState.WAITINGTP);
/*     */     
/*  83 */     waitBeforeTeleport(paramWarMap);
/*     */   }
/*     */   
/*     */   private void waitBeforeTeleport(final WarMap map) {
/*  87 */     FactionWars.debug("Waiting for teleport");
/*  88 */     map.setTimer(SettingsManager.getInt("start-game.time-before-teleport", map));
/*  89 */     for (Player player : map.getS1().getAllPlayersSelected())
/*  90 */       map.getPlayers().add(player.getUniqueId()); 
/*  91 */     for (Player player : map.getS2().getAllPlayersSelected()) {
/*  92 */       map.getPlayers().add(player.getUniqueId());
/*     */     }
/*  94 */     for (Player player : map.getPlayerList())
/*  95 */       LegacyUtil.sendTitle(player, Util.getMessage("teleporting.title"), Util.getMessage("teleporting.subtitle"), 5, 40, 5); 
/*  96 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/*  99 */           if (map.getTimer() == 0) {
/* 100 */             cancel();
/* 101 */             MatchManager.this.teleportToArena(map);
/*     */           } else {
/* 103 */             MatchManager.this.announceTimer(map, Util.getPlainMessage("teleporting.timer"));
/*     */           } 
/* 105 */           map.setTimer(map.getTimer() - 1);
/*     */         }
/* 107 */       }).runTaskTimer((Plugin)FactionWars.get(), 20L, 20L);
/*     */   }
/*     */   
/*     */   private void announceTimer(WarMap paramWarMap, String paramString) {
/* 111 */     announceTimer(paramWarMap, paramString, paramWarMap.getTimer());
/*     */   }
/*     */   
/*     */   private String timerMessage(String paramString, int paramInt) {
/* 115 */     if (paramInt % 60 == 0)
/* 116 */       return paramString.replaceAll("%time%", (paramInt / 60) + " " + ((paramInt > 60) ? Util.getMessage("timer.minutes") : Util.getMessage("timer.minute"))); 
/* 117 */     if (paramInt < 60 && (paramInt % 10 == 0 || paramInt < 10) && paramInt > 0)
/* 118 */       return paramString.replaceAll("%time%", paramInt + " " + ((paramInt > 1) ? Util.getMessage("timer.seconds") : Util.getMessage("timer.second"))); 
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   private void announceTimer(WarMap paramWarMap, String paramString, int paramInt) {
/* 123 */     String str = timerMessage(paramString, paramInt);
/* 124 */     if (str != null)
/* 125 */       paramWarMap.message(str); 
/*     */   }
/*     */   
/*     */   private void teleportToArena(WarMap paramWarMap) {
/* 129 */     FactionWars.debug("Teleporting to arena");
/* 130 */     if (paramWarMap.getS1().getAllPlayersSelected().size() < Util.getMinPlayers() || paramWarMap.getS2().getAllPlayersSelected().size() < Util.getMinPlayers() || Metrics.debugging) {
/* 131 */       paramWarMap.message("There aren't enough players online from one/both factions! WAR cancelled!");
/* 132 */       endGame(paramWarMap, true);
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     paramWarMap.getManager().startMatch();
/*     */     
/* 138 */     ItemStack itemStack = (new ItemBuilder(new ItemStack(Material.STICK), "§a§lKit Selector", new String[] { "Right click to open", "kit selection gui" })).build();
/* 139 */     for (Player player : paramWarMap.getPlayerList()) {
/* 140 */       FactionWars.debug("Saving player inventory and maxing health");
/* 141 */       PlayerData.getPlayerData().add(new PlayerData(player, (SettingsManager.getBool("kit.use-kits", paramWarMap) || !SettingsManager.getBool("kit.drop-loot-if-not-kits", paramWarMap))));
/* 142 */       player.setFoodLevel(20);
/* 143 */       LegacyUtil.maxHealth(player);
/* 144 */       player.setExp(0.0F);
/* 145 */       player.setLevel(0);
/* 146 */       player.setFlying(false);
/* 147 */       player.setAllowFlight(false);
/* 148 */       if (SettingsManager.getBool("kit.use-kits", paramWarMap)) {
/* 149 */         FactionWars.debug("Clearing player inventory");
/* 150 */         Util.get().clear(player);
/* 151 */         player.getInventory().addItem(new ItemStack[] { itemStack });
/* 152 */         player.updateInventory();
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     for (String str : SettingsManager.getStringList("start-game.commands", paramWarMap)) {
/* 157 */       if (str.contains("%player%")) {
/* 158 */         for (Player player : paramWarMap.getPlayerList()) {
/* 159 */           String str1 = str.replaceAll("%player%", player.getName());
/* 160 */           FactionWars.debug("Running command:");
/* 161 */           FactionWars.debug(str1);
/* 162 */           FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str1);
/*     */         }  continue;
/* 164 */       }  FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str);
/*     */     } 
/*     */ 
/*     */     
/* 168 */     byte b = 0;
/* 169 */     ArrayList<Location> arrayList1 = (ArrayList)paramWarMap.getLocations().get("l1"), arrayList2 = (ArrayList)paramWarMap.getLocations().get("l2");
/* 170 */     for (Player player : paramWarMap.getS1().getAllPlayersSelected()) {
/* 171 */       player.teleport(arrayList1.get(b++ % arrayList1.size()));
/*     */     }
/* 173 */     for (Player player : paramWarMap.getS2().getAllPlayersSelected()) {
/* 174 */       player.teleport(arrayList2.get(b++ % arrayList2.size()));
/*     */     }
/*     */     
/* 177 */     paramWarMap.setMatchState(WarMap.MatchState.WAITINGSTART);
/*     */     
/* 179 */     waitStart(paramWarMap);
/*     */   }
/*     */   
/*     */   private void waitStart(final WarMap map) {
/* 183 */     map.setTimer(SettingsManager.getInt("start-game.wait-time", map));
/*     */     
/* 185 */     if (SettingsManager.getBool("kit.use-kits", map)) {
/* 186 */       for (Player player : map.getPlayerList()) {
/* 187 */         KitSelector kitSelector = new KitSelector(player);
/* 188 */         kitSelector.open();
/*     */       } 
/*     */     }
/* 191 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 194 */           if (map.getTimer() == 0) {
/* 195 */             cancel();
/* 196 */             MatchManager.this.startMatch(map);
/*     */           } else {
/* 198 */             MatchManager.this.announceTimer(map, Util.getPlainMessage("warmup.timer"));
/* 199 */             if (map.getTimer() <= 3) {
/* 200 */               for (Player player : map.getPlayerList()) {
/* 201 */                 HashMap<Object, Object> hashMap = new HashMap<>();
/* 202 */                 hashMap.put("timer", Integer.valueOf(map.getTimer()));
/* 203 */                 LegacyUtil.sendTitle(player, Util.getMessage("warmup.title"), Util.getMessage("warmup.subtitle", hashMap), 2, 20, 2);
/*     */               } 
/*     */             }
/*     */           } 
/* 207 */           map.setTimer(map.getTimer() - 1);
/*     */         }
/* 210 */       }).runTaskTimer((Plugin)FactionWars.get(), 0L, 20L);
/*     */   }
/*     */   
/*     */   public void invincible(Player paramPlayer, WarMap paramWarMap) {
/* 214 */     int i = SettingsManager.getInt("start-game.invincible-tag", paramWarMap);
/* 215 */     if (i > 0) {
/* 216 */       paramPlayer.setNoDamageTicks(SettingsManager.getInt("start-game.invincible-tag", paramWarMap) * 20);
/* 217 */       paramPlayer.sendMessage(timerMessage(Util.getPlainMessage("invincible.timer"), i));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startMatch(WarMap paramWarMap) {
/* 222 */     for (Player player : paramWarMap.getPlayerList()) {
/* 223 */       player.closeInventory();
/* 224 */       invincible(player, paramWarMap);
/* 225 */       LegacyUtil.sendTitle(player, Util.getMessage("started.title"), Util.getMessage("started.subtitle"), 5, 60, 5);
/* 226 */       PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
/* 227 */       if (playerData != null) {
/* 228 */         if (SettingsManager.getBool("kit.use-kits", paramWarMap)) {
/* 229 */           if (playerData.getKit().isEmpty()) {
/* 230 */             Kit kit = KitSelector.getDefaultKit(player);
/* 231 */             if (kit != null)
/* 232 */               playerData.setKit(kit.getId()); 
/*     */           } 
/* 234 */           if (!playerData.getKit().isEmpty())
/* 235 */             giveKit(player, playerData.getKit(), true);  continue;
/* 236 */         }  if (SettingsManager.getBool("kit.reset-every-death", paramWarMap))
/* 237 */           RespawnItems.saveItems(player); 
/*     */       } 
/*     */     } 
/* 240 */     if (paramWarMap.getMatchState() != WarMap.MatchState.ENDING) {
/* 241 */       paramWarMap.message(Util.getMessage("started.message"));
/* 242 */       matchCountdown(paramWarMap);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void giveKit(Player paramPlayer, String paramString, boolean paramBoolean) {
/* 247 */     Kit kit = Kit.getKit(paramString);
/* 248 */     if (kit != null) {
/* 249 */       FactionWars.debug("Giving " + paramPlayer.getName() + " kit " + kit.getName());
/* 250 */       if (paramPlayer.getInventory() != null && kit.getItems() != null) {
/* 251 */         if (paramPlayer.getInventory().getContents() != null)
/* 252 */           paramPlayer.getInventory().setContents(Arrays.<ItemStack>copyOfRange(kit.getItems(), 0, (paramPlayer.getInventory().getContents()).length)); 
/* 253 */         paramPlayer.getInventory().setArmorContents(kit.getArmor());
/*     */       } 
/* 255 */       for (PotionEffect potionEffect : kit.getPotionEffects())
/* 256 */         paramPlayer.addPotionEffect(potionEffect); 
/* 257 */       if (!kit.isFree() && paramBoolean) {
/* 258 */         FactionWars.getEcon().withdraw(paramPlayer.getUniqueId(), kit.getPrice());
/* 259 */         FactionWars.getEcon().transaction(-kit.getPrice());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void matchCountdown(final WarMap map) {
/* 265 */     if (map.getMatchState() == WarMap.MatchState.ENDING) {
/*     */       return;
/*     */     }
/* 268 */     map.setMatchState(WarMap.MatchState.PLAYING);
/* 269 */     map.setTimer(SettingsManager.getInt("end-game.timer", map));
/* 270 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 273 */           map.getManager().updateScoreboard();
/* 274 */           if (map.getTimer() <= 0 || map.getMatchState() == WarMap.MatchState.ENDING) {
/* 275 */             cancel();
/* 276 */             if (map.getTimer() == 0)
/* 277 */               map.getManager().timeOut(); 
/*     */           } else {
/* 279 */             MatchManager.this.announceTimer(map, Util.getPlainMessage("game-countdown.timer"));
/*     */           } 
/* 281 */           map.setTimer(map.getTimer() - 1);
/*     */         }
/* 284 */       }).runTaskTimer((Plugin)FactionWars.get(), 0L, 20L);
/*     */   }
/*     */   
/*     */   public void tied(WarMap paramWarMap) {
/* 288 */     for (Player player : paramWarMap.getPlayerList())
/* 289 */       LegacyUtil.sendTitle(player, Util.getMessage("end-game.tied.title"), Util.getMessage("end-game.tied.subtitle"), 5, 80, 5); 
/* 290 */     paramWarMap.message(Util.getMessage("end-game.tied.title"));
/* 291 */     Rewards.runEndCommands(null, null, paramWarMap);
/*     */     
/* 293 */     paramWarMap.setWinner(null);
/* 294 */     endGame(paramWarMap, true);
/* 295 */     FactionWarsWarEndEvent factionWarsWarEndEvent = new FactionWarsWarEndEvent(paramWarMap, paramWarMap.getF1(), paramWarMap.getF2(), true);
/* 296 */     FactionWars.get().getServer().getPluginManager().callEvent((Event)factionWarsWarEndEvent);
/*     */   }
/*     */   
/*     */   public void won(WarMap paramWarMap, String paramString) {
/* 300 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 301 */     hashMap.put("winner", FactionWars.getHandler().getTeamName(paramString));
/* 302 */     hashMap.put("map", paramWarMap.getName());
/* 303 */     hashMap.put("bet", Double.valueOf(paramWarMap.getBet()));
/* 304 */     paramWarMap.setWinner(paramString);
/* 305 */     FactionData factionData1 = FactionData.getFaction(paramString);
/* 306 */     if (factionData1 != null)
/* 307 */       factionData1.setWins(factionData1.getWins() + 1); 
/* 308 */     FactionWars.get().getServer().broadcastMessage(Util.getMessage("end-game.won.broadcast", hashMap));
/* 309 */     for (Player player : paramWarMap.getPlayerList()) {
/* 310 */       LegacyUtil.sendTitle(player, Util.getMessage("end-game.won.title", hashMap), Util.getMessage("end-game.won.subtitle", hashMap), 5, 80, 5);
/*     */     }
/* 312 */     FactionData factionData2 = FactionData.getFaction(paramWarMap.getLoser());
/* 313 */     if (factionData2 != null) {
/* 314 */       factionData2.setLosts(factionData2.getLosts() + 1);
/*     */     }
/* 316 */     Util.logWar(FactionWars.getHandler().getTeamName(paramString), FactionWars.getHandler().getTeamName(paramWarMap.getLoser()));
/* 317 */     Rewards.runEndCommands(paramString, paramWarMap.getLoser(), paramWarMap);
/* 318 */     Rewards.handleBets(paramString, paramWarMap.getLoser(), paramWarMap.getBet());
/*     */ 
/*     */     
/* 321 */     for (UUID uUID : paramWarMap.getPlayers()) {
/* 322 */       OfflinePlayer offlinePlayer = FactionWars.get().getServer().getOfflinePlayer(uUID);
/* 323 */       if (FactionWars.getHandler().getPlayerTeam(offlinePlayer).equals(paramString)) {
/* 324 */         Player player = FactionWars.get().getServer().getPlayer(uUID);
/* 325 */         if (SettingsManager.getBool("end-game.fireworks.enabled", paramWarMap)) {
/* 326 */           Rewards.fireworks(player, 10, SettingsManager.getInt("end-game.fireworks.amount-per-five-ticks", paramWarMap));
/*     */         }
/*     */       } 
/*     */     } 
/* 330 */     endGame(paramWarMap, false);
/* 331 */     FactionWarsWarEndEvent factionWarsWarEndEvent = new FactionWarsWarEndEvent(paramWarMap, paramString, paramWarMap.getLoser());
/* 332 */     FactionWars.get().getServer().getPluginManager().callEvent((Event)factionWarsWarEndEvent);
/*     */   }
/*     */   
/*     */   public void endGame(final WarMap map, boolean paramBoolean) {
/* 336 */     map.setMatchState(WarMap.MatchState.ENDING);
/* 337 */     map.setTimer(-1);
/* 338 */     map.message(Util.getMessage("end-game.teleporting"));
/*     */     
/* 340 */     final String f1Id = map.getF1(), f2Id = map.getF2();
/* 341 */     if (FactionWars.get().isEnabled()) {
/* 342 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/* 345 */             DatabaseHelper.get().saveStats(FactionData.getFaction(f1Id));
/* 346 */             DatabaseHelper.get().saveStats(FactionData.getFaction(f2Id));
/*     */           }
/* 348 */         }).runTaskAsynchronously((Plugin)FactionWars.get());
/*     */       
/* 350 */       if (!paramBoolean)
/* 351 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run() {
/* 354 */               MatchManager.this.resetMap(map);
/*     */             }
/* 356 */           }).runTaskLater((Plugin)FactionWars.get(), (20 * SettingsManager.getInt("end-game.teleport-time", map))); 
/*     */     } 
/* 358 */     if (paramBoolean) {
/* 359 */       resetMap(map);
/*     */     }
/* 361 */     FactionData factionData1 = FactionData.getFaction(str1), factionData2 = FactionData.getFaction(str2);
/* 362 */     if (factionData1 != null)
/* 363 */       factionData1.setCooldown(); 
/* 364 */     if (factionData2 != null)
/* 365 */       factionData2.setCooldown(); 
/*     */   }
/*     */   
/*     */   private void resetMap(WarMap paramWarMap) {
/* 369 */     FactionWars.debug("Reseting map");
/* 370 */     for (UUID uUID : paramWarMap.getPlayers()) {
/* 371 */       FactionWars.debug("Map reset - playeruuid " + uUID);
/* 372 */       OfflinePlayer offlinePlayer = FactionWars.get().getServer().getOfflinePlayer(uUID);
/* 373 */       FactionWars.debug("Map reset - player " + offlinePlayer.getName());
/* 374 */       Player player = FactionWars.get().getServer().getPlayer(uUID);
/* 375 */       if (player != null) {
/* 376 */         FactionWars.debug("Map reset - player not null - leaving");
/* 377 */         playerLeave(player, true);
/*     */       } else {
/* 379 */         FactionWars.debug("Map reset - player IS NULL!!!");
/* 380 */       }  if (paramWarMap.getWinner() != null) {
/* 381 */         FactionWars.debug("Map reset - checking rewards for player");
/* 382 */         if (FactionWars.getHandler().getPlayerTeam(offlinePlayer).equals(paramWarMap.getWinner())) {
/* 383 */           Rewards.reward(offlinePlayer, paramWarMap.getWinner(), paramWarMap.getLoser(), paramWarMap); continue;
/*     */         } 
/* 385 */         Rewards.punish(offlinePlayer, paramWarMap.getWinner(), paramWarMap.getLoser(), paramWarMap);
/*     */       } 
/*     */     } 
/* 388 */     paramWarMap.setMatchState(WarMap.MatchState.EMPTY);
/* 389 */     PlayerSelection.getPlayerSelections().remove(paramWarMap.getS1());
/* 390 */     PlayerSelection.getPlayerSelections().remove(paramWarMap.getS2());
/* 391 */     paramWarMap.setF1(null);
/* 392 */     paramWarMap.setF2(null);
/* 393 */     paramWarMap.setWinner(null);
/* 394 */     paramWarMap.getManager().reset();
/* 395 */     paramWarMap.getPlayers().clear();
/* 396 */     paramWarMap.setBet(0.0D);
/* 397 */     HologramManager.get().updateAll();
/* 398 */     for (Item item : paramWarMap.remove) {
/* 399 */       if (item != null)
/* 400 */         item.remove(); 
/* 401 */     }  paramWarMap.remove.clear();
/*     */   }
/*     */   
/*     */   public WarMap getPlayerMap(Player paramPlayer) {
/* 405 */     for (WarMap warMap : WarMap.getMaps().values()) {
/* 406 */       if (warMap.getPlayers().contains(paramPlayer.getUniqueId()))
/* 407 */         return warMap; 
/* 408 */     }  return null;
/*     */   }
/*     */   
/*     */   public void playerLeave(Player paramPlayer, boolean paramBoolean) {
/* 412 */     FactionWars.debug("Running player leave for: " + paramPlayer.getName());
/* 413 */     paramPlayer.closeInventory();
/* 414 */     WarMap warMap = getPlayerMap(paramPlayer);
/* 415 */     if (warMap == null) {
/*     */       return;
/*     */     }
/* 418 */     paramPlayer.setNoDamageTicks(1);
/* 419 */     ScoreboardManager.get().clear(paramPlayer);
/*     */     
/* 421 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 422 */     hashMap.put("player", paramPlayer.getName());
/*     */     
/* 424 */     if (warMap.getMatchState() != WarMap.MatchState.ENDING) {
/* 425 */       warMap.message(Util.getMessage("leave-game", hashMap));
/*     */     }
/* 427 */     PlayerData playerData = PlayerData.getPlayerData(paramPlayer.getUniqueId());
/* 428 */     if (playerData != null) {
/* 429 */       playerData.teleport();
/* 430 */       if (paramBoolean) {
/* 431 */         if (SettingsManager.getBool("kit.use-kits", warMap))
/* 432 */           playerData.restore(); 
/* 433 */         PlayerData.getPlayerData().remove(playerData);
/*     */       } 
/* 435 */       playerData.setKit("");
/*     */     } 
/*     */     
/* 438 */     RespawnItems.removeItems(paramPlayer);
/* 439 */     FactionWarsPlayerLeaveWarEvent factionWarsPlayerLeaveWarEvent = new FactionWarsPlayerLeaveWarEvent(paramPlayer, warMap);
/* 440 */     FactionWars.get().getServer().getPluginManager().callEvent((Event)factionWarsPlayerLeaveWarEvent);
/*     */   }
/*     */   
/*     */   public boolean isFactionBusy(String paramString) {
/* 444 */     for (Player player : Util.getOnlineOptedin(paramString)) {
/* 445 */       if (getPlayerMap(player) != null)
/* 446 */         return true; 
/* 447 */     }  return false;
/*     */   }
/*     */   
/*     */   public void checkEnd(WarMap paramWarMap) {
/* 451 */     FactionWars.debug("Checking game end");
/* 452 */     boolean bool1 = false, bool2 = false;
/*     */     
/* 454 */     for (Player player : paramWarMap.getPlayerList()) {
/* 455 */       String str = FactionWars.getHandler().getPlayerTeam(player);
/* 456 */       if (paramWarMap.getF1().equals(str)) {
/* 457 */         bool1 = true; continue;
/* 458 */       }  if (paramWarMap.getF2().equals(str)) {
/* 459 */         bool2 = true;
/*     */       }
/*     */     } 
/* 462 */     if (!bool1 && bool2) {
/* 463 */       get().won(paramWarMap, paramWarMap.getF2());
/* 464 */     } else if (!bool2 && bool1) {
/* 465 */       get().won(paramWarMap, paramWarMap.getF1());
/*     */     } 
/*     */   } public Location getSpawn(Player paramPlayer) {
/*     */     ArrayList<Location> arrayList;
/* 469 */     WarMap warMap = getPlayerMap(paramPlayer);
/* 470 */     if (warMap == null) return null;
/*     */     
/* 472 */     if (FactionWars.getHandler().getPlayerTeam(paramPlayer).equals(warMap.getF1())) {
/* 473 */       arrayList = (ArrayList)warMap.getLocations().get("l1");
/*     */     } else {
/* 475 */       arrayList = (ArrayList)warMap.getLocations().get("l2");
/* 476 */     }  return arrayList.get((new Random(System.currentTimeMillis())).nextInt(arrayList.size()));
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\MatchManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */