/*    */ package io.github.guipenedo.factionwars.models;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class Kit
/*    */ {
/* 15 */   private static ArrayList<Kit> kits = new ArrayList<>();
/*    */   
/*    */   private String id;
/*    */   
/*    */   private String name;
/*    */   private ItemStack[] items;
/*    */   
/*    */   public Kit(String paramString) {
/* 23 */     ConfigurationSection configurationSection = FactionWars.getKitsConfig().getConfig().getConfigurationSection("kits." + paramString);
/* 24 */     this.id = paramString;
/* 25 */     this.name = configurationSection.getString("name", "?");
/* 26 */     this.item = configurationSection.getItemStack("item", new ItemStack(LegacyUtil.STAINED_GLASS_PANE));
/* 27 */     this.price = configurationSection.getDouble("price", 0.0D);
/* 28 */     this.items = Util.get().getInventory(FactionWars.getKitsConfig().getConfig(), "kits." + paramString + ".inv");
/* 29 */     this.armor = Util.get().getArmorContents(FactionWars.getKitsConfig().getConfig(), "kits." + paramString + ".inv");
/* 30 */     this.potionEffects = Util.get().getPotionEffects(FactionWars.getKitsConfig().getConfig(), "kits." + paramString + ".inv");
/* 31 */     FactionWars.debug("Loaded kit " + this.name + " id: " + paramString + " item: " + this.item.getType().toString());
/*    */   }
/*    */   private ItemStack[] armor; private Collection<PotionEffect> potionEffects; private double price; private ItemStack item;
/*    */   public static Kit getKit(String paramString) {
/* 35 */     for (Kit kit : kits) {
/* 36 */       if (kit.id.equals(paramString))
/* 37 */         return kit; 
/* 38 */     }  return null;
/*    */   }
/*    */   
/*    */   public static ArrayList<Kit> getKits() {
/* 42 */     return kits;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 46 */     return this.item;
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack paramItemStack) {
/* 50 */     this.item = paramItemStack;
/*    */   }
/*    */   
/*    */   public ItemStack[] getItems() {
/* 54 */     return this.items;
/*    */   }
/*    */   
/*    */   public void setItems(ItemStack[] paramArrayOfItemStack) {
/* 58 */     this.items = paramArrayOfItemStack;
/*    */   }
/*    */   
/*    */   public ItemStack[] getArmor() {
/* 62 */     return this.armor;
/*    */   }
/*    */   
/*    */   public void setArmor(ItemStack[] paramArrayOfItemStack) {
/* 66 */     this.armor = paramArrayOfItemStack;
/*    */   }
/*    */   
/*    */   public Collection<PotionEffect> getPotionEffects() {
/* 70 */     return this.potionEffects;
/*    */   }
/*    */   
/*    */   public void setPotionEffects(Collection<PotionEffect> paramCollection) {
/* 74 */     this.potionEffects = paramCollection;
/*    */   }
/*    */   
/*    */   public double getPrice() {
/* 78 */     return this.price;
/*    */   }
/*    */   
/*    */   public void setPrice(double paramDouble) {
/* 82 */     this.price = paramDouble;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 86 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 90 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean canBeSelectedBy(Player paramPlayer) {
/* 94 */     return (paramPlayer.hasPermission("factionwars.kit." + getName().toLowerCase().replaceAll(" ", "_")) || paramPlayer.hasPermission("factionwars.kit.*"));
/*    */   }
/*    */   
/*    */   public boolean isFree() {
/* 98 */     return (getPrice() <= 0.0D);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\Kit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */