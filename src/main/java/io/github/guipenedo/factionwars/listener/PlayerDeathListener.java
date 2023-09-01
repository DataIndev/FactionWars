/*     */ package io.github.guipenedo.factionwars.listener;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.managers.SettingsManager;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import io.github.guipenedo.factionwars.models.PlayerData;
/*     */ import io.github.guipenedo.factionwars.models.RespawnItems;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class PlayerDeathListener implements Listener {
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onDeath(PlayerDeathEvent paramPlayerDeathEvent) {
/*  22 */     WarMap warMap = MatchManager.get().getPlayerMap(paramPlayerDeathEvent.getEntity());
/*  23 */     if (warMap == null)
/*     */       return; 
/*  25 */     FactionWars.debug("Death event - has map");
/*  26 */     Player player = paramPlayerDeathEvent.getEntity();
/*  27 */     String str = FactionWars.getHandler().getPlayerTeam(player);
/*  28 */     if (str != null) {
/*  29 */       FactionWars.debug("Death event - has faction");
/*  30 */       FactionData factionData = FactionData.getFaction(str);
/*  31 */       if (factionData != null)
/*  32 */         factionData.setDeaths(factionData.getDeaths() + 1); 
/*  33 */       if (player.getKiller() != null) {
/*  34 */         String str1 = FactionWars.getHandler().getPlayerTeam(player.getKiller());
/*  35 */         if (!str.equals(str1)) {
/*  36 */           warMap.getManager().kill(player.getKiller().getUniqueId());
/*  37 */           if (str1 != null && FactionData.getFaction(str1) != null && MatchManager.get().getPlayerMap(player.getKiller()) != null && MatchManager.get().getPlayerMap(player.getKiller()).getId().equals(warMap.getId())) {
/*  38 */             FactionData.getFaction(str1).setKills(FactionData.getFaction(str1).getKills() + 1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  43 */     FactionWars.debug("Death event - running death()");
/*  44 */     warMap.getManager().death(player.getUniqueId());
/*  45 */     if (warMap.getManager().shouldRespawn(player.getUniqueId())) {
/*  46 */       FactionWars.debug("Death event - should respawn");
/*  47 */       if (!SettingsManager.getBool("kit.reset-every-death", warMap)) {
/*  48 */         RespawnItems.saveItems(player);
/*  49 */         FactionWars.debug("Death event - not reseting items - saving items");
/*     */       } 
/*  51 */       FactionWars.debug("Death event - deleting items");
/*  52 */       clear(paramPlayerDeathEvent);
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     FactionWars.debug("Death event - last check");
/*  57 */     if (SettingsManager.getBool("kit.use-kits", warMap) || !SettingsManager.getBool("kit.drop-loot-if-not-kits", warMap)) {
/*  58 */       FactionWars.debug("Death event - using kits or not dropping - deleting items");
/*  59 */       clear(paramPlayerDeathEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clear(PlayerDeathEvent paramPlayerDeathEvent) {
/*  64 */     paramPlayerDeathEvent.getDrops().clear();
/*  65 */     paramPlayerDeathEvent.setDroppedExp(0);
/*  66 */     paramPlayerDeathEvent.getEntity().getInventory().clear();
/*  67 */     paramPlayerDeathEvent.getEntity().getInventory().setArmorContents(null);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onRespawn(final PlayerRespawnEvent e) {
/*  72 */     final WarMap map = MatchManager.get().getPlayerMap(e.getPlayer());
/*  73 */     FactionWars.debug("Respawn event");
/*  74 */     if (warMap != null && warMap.getManager().shouldRespawn(e.getPlayer().getUniqueId())) {
/*  75 */       FactionWars.debug("Respawn event - has map and is respawning");
/*  76 */       Location location = MatchManager.get().getSpawn(e.getPlayer());
/*  77 */       if (location != null) {
/*  78 */         FactionWars.debug("Respawn event - has respawn location");
/*  79 */         e.setRespawnLocation(location);
/*  80 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run() {
/*  83 */               FactionWars.debug("Respawn event - entered task");
/*  84 */               PlayerData playerData = PlayerData.getPlayerData(e.getPlayer().getUniqueId());
/*  85 */               if (SettingsManager.getBool("kit.reset-every-death", map) && SettingsManager.getBool("kit.use-kits", map) && playerData != null && !playerData.getKit().isEmpty()) {
/*  86 */                 FactionWars.debug("Respawn event - giving kit");
/*  87 */                 MatchManager.get().giveKit(e.getPlayer(), playerData.getKit(), false);
/*     */               } else {
/*     */                 
/*  90 */                 FactionWars.debug("Respawn event - giving items");
/*  91 */                 RespawnItems.giveItems(e.getPlayer());
/*     */               } 
/*  93 */               MatchManager.get().invincible(e.getPlayer(), map);
/*     */             }
/*  95 */           }).runTaskLater((Plugin)FactionWars.get(), 5L);
/*     */       } 
/*     */       return;
/*     */     } 
/*  99 */     FactionWars.debug("Respawn event - not respawning");
/* 100 */     final PlayerData data = PlayerData.getPlayerData(e.getPlayer().getUniqueId());
/* 101 */     if (playerData != null) {
/* 102 */       FactionWars.debug("Respawn event - restoring player");
/* 103 */       e.setRespawnLocation(playerData.getLocation());
/* 104 */       playerData.teleport();
/* 105 */       playerData.restore();
/* 106 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/* 109 */             data.teleport();
/* 110 */             data.restore();
/* 111 */             PlayerData.getPlayerData().remove(data);
/*     */           }
/* 113 */         }).runTaskLater((Plugin)FactionWars.get(), 20L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerDeathListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */