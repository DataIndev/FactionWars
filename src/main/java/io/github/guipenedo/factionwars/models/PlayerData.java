/*     */ package io.github.guipenedo.factionwars.models;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ public class PlayerData
/*     */ {
/*  15 */   private static ArrayList<PlayerData> playerData = new ArrayList<>();
/*  16 */   private static ArrayList<UUID> optedOut = new ArrayList<>();
/*  17 */   private String kit = "";
/*     */   
/*     */   private UUID uuid;
/*     */   
/*     */   private float exp;
/*     */   
/*     */   private int level;
/*     */   private int food;
/*     */   private double health;
/*     */   
/*     */   public PlayerData(Player paramPlayer, boolean paramBoolean) {
/*  28 */     this.shouldRestore = paramBoolean;
/*  29 */     this.uuid = paramPlayer.getUniqueId();
/*  30 */     this.exp = paramPlayer.getExp();
/*  31 */     this.level = paramPlayer.getLevel();
/*  32 */     this.food = paramPlayer.getFoodLevel();
/*  33 */     this.health = paramPlayer.getHealth();
/*  34 */     if (this.health > 20.0D || this.health < 0.0D)
/*  35 */       this.health = 20.0D; 
/*  36 */     this.items = paramPlayer.getInventory().getContents();
/*  37 */     this.armor = paramPlayer.getInventory().getArmorContents();
/*  38 */     this.potionEffects = paramPlayer.getActivePotionEffects();
/*  39 */     this.location = paramPlayer.getLocation().clone();
/*     */   }
/*     */   private ItemStack[] items; private ItemStack[] armor; private Collection<PotionEffect> potionEffects; private Location location; private boolean shouldRestore;
/*     */   public static ArrayList<UUID> getOptedOut() {
/*  43 */     return optedOut;
/*     */   }
/*     */   
/*     */   public static ArrayList<PlayerData> getPlayerData() {
/*  47 */     return playerData;
/*     */   }
/*     */   
/*     */   public static PlayerData getPlayerData(UUID paramUUID) {
/*  51 */     for (PlayerData playerData : getPlayerData()) {
/*  52 */       if (playerData.getUuid().toString().equals(paramUUID.toString()))
/*  53 */         return playerData; 
/*  54 */     }  return null;
/*     */   }
/*     */   
/*     */   public String getKit() {
/*  58 */     return this.kit;
/*     */   }
/*     */   
/*     */   public void setKit(String paramString) {
/*  62 */     this.kit = paramString;
/*     */   }
/*     */   
/*     */   public void teleport() {
/*  66 */     Player player = getPlayer();
/*  67 */     if (player == null)
/*     */       return; 
/*  69 */     FactionWars.debug("Teleporting player " + player.getName());
/*  70 */     player.teleport(this.location.clone());
/*     */   }
/*     */   
/*     */   public void restore() {
/*  74 */     Player player = getPlayer();
/*  75 */     if (player == null) {
/*     */       return;
/*     */     }
/*  78 */     FactionWars.debug("Restoring player " + player.getName());
/*  79 */     player.setLevel(this.level);
/*  80 */     player.setExp(this.exp);
/*  81 */     player.setHealth(this.health);
/*  82 */     player.setFoodLevel(this.food);
/*  83 */     if (this.shouldRestore) {
/*  84 */       FactionWars.debug("Restoring inventory of " + player.getName());
/*  85 */       Util.get().clear(player);
/*  86 */       player.getInventory().setContents(this.items);
/*     */       
/*  88 */       player.getInventory().setArmorContents(this.armor);
/*  89 */       for (PotionEffect potionEffect : this.potionEffects)
/*  90 */         player.addPotionEffect(potionEffect); 
/*  91 */       player.updateInventory();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Player getPlayer() {
/*  96 */     return FactionWars.get().getServer().getPlayer(this.uuid);
/*     */   }
/*     */   
/*     */   private UUID getUuid() {
/* 100 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 104 */     return this.location;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\PlayerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */