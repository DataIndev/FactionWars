/*    */ package io.github.guipenedo.factionwars.models;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class RespawnItems
/*    */ {
/* 15 */   public static ArrayList<RespawnItems> players = new ArrayList<>();
/*    */   
/*    */   private UUID uuid;
/*    */   private ItemStack[] items;
/*    */   
/*    */   private RespawnItems(Player paramPlayer) {
/* 21 */     this.uuid = paramPlayer.getUniqueId();
/* 22 */     this.items = paramPlayer.getInventory().getContents();
/* 23 */     this.armor = paramPlayer.getInventory().getArmorContents();
/* 24 */     this.potionEffects = paramPlayer.getActivePotionEffects();
/*    */   }
/*    */   private ItemStack[] armor; private Collection<PotionEffect> potionEffects;
/*    */   public static void saveItems(Player paramPlayer) {
/* 28 */     players.add(new RespawnItems(paramPlayer));
/*    */   }
/*    */   
/*    */   public static RespawnItems getItems(Player paramPlayer) {
/* 32 */     for (RespawnItems respawnItems : players) {
/* 33 */       if (respawnItems.getUuid().equals(paramPlayer.getUniqueId()))
/* 34 */         return respawnItems; 
/* 35 */     }  return null;
/*    */   }
/*    */   
/*    */   public static void giveItems(Player paramPlayer) {
/* 39 */     RespawnItems respawnItems = getItems(paramPlayer);
/* 40 */     if (respawnItems == null)
/*    */       return; 
/* 42 */     LegacyUtil.maxHealth(paramPlayer);
/* 43 */     paramPlayer.setFoodLevel(20);
/* 44 */     Util.get().clear(paramPlayer);
/* 45 */     paramPlayer.getInventory().setContents(respawnItems.items);
/*    */     
/* 47 */     paramPlayer.getInventory().setArmorContents(respawnItems.armor);
/* 48 */     for (PotionEffect potionEffect : respawnItems.potionEffects)
/* 49 */       paramPlayer.addPotionEffect(potionEffect); 
/* 50 */     paramPlayer.updateInventory();
/* 51 */     players.remove(respawnItems);
/*    */   }
/*    */   
/*    */   public static void removeItems(Player paramPlayer) {
/* 55 */     RespawnItems respawnItems = getItems(paramPlayer);
/* 56 */     if (respawnItems != null)
/* 57 */       players.remove(respawnItems); 
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 61 */     return FactionWars.get().getServer().getPlayer(this.uuid);
/*    */   }
/*    */   
/*    */   private UUID getUuid() {
/* 65 */     return this.uuid;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\RespawnItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */