/*    */ package io.github.guipenedo.factionwars.models;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class Gamemode
/*    */ {
/* 14 */   private static Map<String, Gamemode> gamemodes = new TreeMap<>();
/*    */   
/*    */   private GamemodeType gamemodeType;
/*    */   
/*    */   private String name;
/*    */   private String customType;
/*    */   
/*    */   public ItemStack getItem() {
/* 22 */     return this.item;
/*    */   }
/*    */   public static String defaultGamemode; private ConfigurationSection config; private ItemStack item;
/*    */   public Gamemode(String paramString) {
/* 26 */     ConfigurationSection configurationSection = FactionWars.getGamemodesConfig().getConfig().getConfigurationSection("gamemodes." + paramString);
/* 27 */     this.gamemodeType = GamemodeType.valueOf(configurationSection.getString("type"));
/* 28 */     if (this.gamemodeType == GamemodeType.CUSTOM)
/* 29 */       this.customType = configurationSection.getString("custom-type"); 
/* 30 */     this.name = paramString;
/* 31 */     this.config = configurationSection;
/* 32 */     this.item = configurationSection.getItemStack("item", new ItemStack(Material.MAP));
/*    */   }
/*    */   
/*    */   public static Map<String, Gamemode> getGamemodes() {
/* 36 */     return gamemodes;
/*    */   }
/*    */   
/*    */   public static Map<String, Gamemode> getSetupGamemodes() {
/* 40 */     return (Map<String, Gamemode>)gamemodes.entrySet().stream().filter(paramEntry -> WarMap.getMaps().entrySet().stream().anyMatch(()))
/*    */       
/* 42 */       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*    */   }
/*    */   
/*    */   public static Gamemode get(String paramString) {
/* 46 */     return gamemodes.get(paramString);
/*    */   }
/*    */   
/*    */   public static Gamemode getDefault() {
/* 50 */     Map<String, Gamemode> map = getSetupGamemodes();
/* 51 */     return (!map.containsKey(defaultGamemode) && !map.isEmpty()) ? map.values().iterator().next() : get(defaultGamemode);
/*    */   }
/*    */   
/*    */   public ConfigurationSection getConfig() {
/* 55 */     return this.config;
/*    */   }
/*    */   
/*    */   public GamemodeType getGamemodeType() {
/* 59 */     return this.gamemodeType;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 63 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean isDefault() {
/* 67 */     return defaultGamemode.equals(this.name);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack paramItemStack) {
/* 71 */     this.item = paramItemStack;
/*    */   }
/*    */   
/*    */   public String getCustomType() {
/* 75 */     return this.customType;
/*    */   }
/*    */   
/*    */   public enum GamemodeType {
/* 79 */     KOTH, TDM, BLITZ, CTF, HALO, CUSTOM;
/*    */   }
/*    */   
/*    */   public boolean canBeSelectedBy(Player paramPlayer) {
/* 83 */     return (isDefault() || paramPlayer.hasPermission("factionwars.gamemode." + getName().toLowerCase().replaceAll(" ", "_")) || paramPlayer.hasPermission("factionwars.gamemode.*"));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\Gamemode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */