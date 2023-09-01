/*     */ package io.github.guipenedo.factionwars.managers;
/*     */ 
/*     */ import com.gmail.filoghost.holographicdisplays.api.Hologram;
/*     */ import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
/*     */ import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class HologramManager {
/*     */   private static HologramManager instance;
/*  23 */   public ArrayList<Location> locations = new ArrayList<>();
/*  24 */   public ConcurrentHashMap<String, ArrayList<Hologram>> holograms = new ConcurrentHashMap<>();
/*  25 */   private ArrayList<Hologram> noFaction = null; private boolean initialized = false;
/*     */   private boolean enabled = false;
/*     */   private ItemStack item;
/*     */   private int switchTime;
/*     */   private int numberOfTopFactions;
/*  30 */   private HologramType currentType = HologramType.TOP;
/*  31 */   private List<String> factionStatsLines = new ArrayList<>(); private String topHeader;
/*     */   private String topFaction;
/*     */   
/*     */   public static HologramManager get() {
/*  35 */     if (instance == null)
/*  36 */       instance = new HologramManager(); 
/*  37 */     return instance;
/*     */   }
/*     */   private String topFooter; private String noFactionStatsLine;
/*     */   public boolean isEnabled() {
/*  41 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void load() {
/*  45 */     ConfigurationSection configurationSection = FactionWars.getMainConfig().getConfig().getConfigurationSection("holograms");
/*     */     
/*  47 */     if (configurationSection.isConfigurationSection("item") && configurationSection.getConfigurationSection("item").contains("id")) {
/*  48 */       configurationSection.set("item", "DIAMOND_SWORD");
/*  49 */       FactionWars.getMainConfig().saveConfig();
/*     */     } 
/*     */     
/*  52 */     instance.item = (new ItemBuilder(Material.valueOf(configurationSection.getString("item")), "", null, true)).build();
/*  53 */     instance.switchTime = configurationSection.getInt("switchTime");
/*  54 */     instance.numberOfTopFactions = configurationSection.getInt("top.number-of-factions");
/*     */     
/*  56 */     boolean bool1 = configurationSection.getBoolean("top.enabled");
/*  57 */     boolean bool2 = configurationSection.getBoolean("faction.enabled");
/*     */ 
/*     */     
/*  60 */     instance.topHeader = configurationSection.getString("top.header");
/*  61 */     instance.topFaction = configurationSection.getString("top.line");
/*  62 */     instance.topFooter = configurationSection.getString("top.footer");
/*     */     
/*  64 */     instance.factionStatsLines = configurationSection.getStringList("faction.lines");
/*  65 */     instance.noFactionStatsLine = configurationSection.getString("faction.no-faction");
/*     */     
/*  67 */     instance.initialized = true;
/*  68 */     instance.enabled = ((bool1 || bool2) && FactionWars.get().getServer().getPluginManager().isPluginEnabled("HolographicDisplays"));
/*  69 */     if (instance.enabled) {
/*  70 */       instance.locations = getLocations();
/*  71 */       if (bool1 && bool2) {
/*  72 */         (new BukkitRunnable()
/*     */           {
/*     */             public void run() {
/*  75 */               if (HologramManager.this.currentType == HologramManager.HologramType.TOP) {
/*  76 */                 HologramManager.this.currentType = HologramManager.HologramType.FACTION;
/*     */               } else {
/*  78 */                 HologramManager.this.currentType = HologramManager.HologramType.TOP;
/*  79 */               }  HologramManager.this.updateAll();
/*     */             }
/*  81 */           }).runTaskTimer((Plugin)FactionWars.get(), 10L, (20 * this.switchTime));
/*  82 */       } else if (bool2) {
/*  83 */         this.currentType = HologramType.FACTION;
/*  84 */       }  for (Player player : FactionWars.get().getServer().getOnlinePlayers())
/*  85 */         handlePlayer(player); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void create(String paramString) {
/*  90 */     if (!this.initialized || !this.enabled || getHolograms(paramString) != null) {
/*     */       return;
/*     */     }
/*  93 */     ArrayList<Hologram> arrayList = new ArrayList();
/*     */     
/*  95 */     for (Location location : this.locations) {
/*  96 */       Hologram hologram = HologramsAPI.createHologram((Plugin)FactionWars.get(), location);
/*  97 */       VisibilityManager visibilityManager = hologram.getVisibilityManager();
/*  98 */       for (Player player : FactionWars.get().getServer().getOnlinePlayers()) {
/*  99 */         String str = FactionWars.getHandler().getPlayerTeam(player);
/* 100 */         if ((str == null && paramString == null) || (paramString != null && paramString.equals(str)))
/* 101 */           visibilityManager.showTo(player); 
/*     */       } 
/* 103 */       visibilityManager.setVisibleByDefault(false);
/* 104 */       arrayList.add(hologram);
/*     */     } 
/*     */     
/* 107 */     if (paramString == null) {
/* 108 */       this.noFaction = arrayList;
/*     */     } else {
/* 110 */       this.holograms.put(paramString, arrayList);
/* 111 */     }  refresh(paramString);
/*     */   }
/*     */   
/*     */   public void refresh(String paramString) {
/* 115 */     ArrayList<Hologram> arrayList = getHolograms(paramString);
/* 116 */     if (arrayList == null || !this.initialized || !this.enabled) {
/* 117 */       delete(paramString);
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     ArrayList<String> arrayList1 = new ArrayList();
/* 122 */     if (this.currentType == HologramType.FACTION) {
/* 123 */       if (paramString == null) {
/* 124 */         arrayList1.add(this.noFactionStatsLine.replaceAll("&", "§"));
/*     */       } else {
/* 126 */         FactionData factionData = FactionData.getFaction(paramString);
/* 127 */         if (factionData == null) {
/* 128 */           delete(paramString);
/*     */           return;
/*     */         } 
/* 131 */         for (String str : this.factionStatsLines)
/* 132 */           arrayList1.add(formatFactionStats(str, paramString)); 
/*     */       } 
/* 134 */     } else if (this.currentType == HologramType.TOP) {
/* 135 */       arrayList1.add(this.topHeader.replaceAll("&", "§"));
/* 136 */       for (FactionData factionData : FactionData.getTop(this.numberOfTopFactions))
/* 137 */         arrayList1.add(formatFactionStats(this.topFaction, factionData.getFactionId())); 
/* 138 */       arrayList1.add(this.topFooter.replaceAll("&", "§"));
/*     */     } 
/*     */     
/* 141 */     for (Hologram hologram : arrayList) {
/* 142 */       hologram.clearLines();
/* 143 */       hologram.appendItemLine(this.item);
/* 144 */       for (String str : arrayList1)
/* 145 */         hologram.appendTextLine(str); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatFactionStats(String paramString1, String paramString2) {
/* 150 */     if (paramString2 == null)
/* 151 */       return paramString1; 
/* 152 */     FactionData factionData = FactionData.getFaction(paramString2);
/* 153 */     if (factionData == null || factionData.getName() == null)
/* 154 */       return paramString1; 
/* 155 */     return paramString1.replaceAll("&", "§").replaceAll("%rank%", String.valueOf(factionData.getRank())).replaceAll("%faction%", factionData.getName()).replaceAll("%wins%", String.valueOf(factionData.getWins())).replaceAll("%losses%", String.valueOf(factionData.getLosts())).replaceAll("%kills%", String.valueOf(factionData.getKills())).replaceAll("%deaths%", String.valueOf(factionData.getDeaths()));
/*     */   }
/*     */   
/*     */   private ArrayList<Hologram> getHolograms(String paramString) {
/* 159 */     if (paramString == null)
/* 160 */       return this.noFaction; 
/* 161 */     return this.holograms.getOrDefault(paramString, null);
/*     */   }
/*     */   
/*     */   public void delete(String paramString) {
/* 165 */     ArrayList<Hologram> arrayList = getHolograms(paramString);
/* 166 */     if (arrayList == null || !this.initialized || !this.enabled)
/*     */       return; 
/* 168 */     for (Hologram hologram : arrayList)
/* 169 */       hologram.delete(); 
/* 170 */     this.holograms.remove(paramString);
/*     */   }
/*     */   
/*     */   private ArrayList<Location> getLocations() {
/* 174 */     ArrayList<Location> arrayList = new ArrayList();
/* 175 */     if (FactionWars.getMainConfig().contains("holograms.locations"))
/* 176 */       for (String str : FactionWars.getMainConfig().getConfig().getStringList("holograms.locations")) {
/* 177 */         String[] arrayOfString = str.split(";");
/* 178 */         arrayList.add(new Location(FactionWars.get().getServer().getWorld(arrayOfString[0]), Double.parseDouble(arrayOfString[1]), Double.parseDouble(arrayOfString[2]), Double.parseDouble(arrayOfString[3])));
/*     */       }  
/* 180 */     return arrayList;
/*     */   }
/*     */   
/*     */   private ArrayList<Hologram> getPlayerHolograms(Player paramPlayer) {
/* 184 */     if (paramPlayer != null) {
/* 185 */       String str = FactionWars.getHandler().getPlayerTeam(paramPlayer);
/* 186 */       ArrayList<Hologram> arrayList = getHolograms(str);
/* 187 */       if (arrayList != null)
/* 188 */         return arrayList; 
/*     */     } 
/* 190 */     return new ArrayList<>();
/*     */   }
/*     */   
/*     */   public void handlePlayer(Player paramPlayer) {
/* 194 */     ArrayList<Hologram> arrayList = getPlayerHolograms(paramPlayer);
/* 195 */     String str = FactionWars.getHandler().getPlayerTeam(paramPlayer);
/* 196 */     for (Map.Entry<String, ArrayList<Hologram>> entry : this.holograms.entrySet()) {
/* 197 */       if (!((String)entry.getKey()).equals(str))
/* 198 */         hideHolograms((ArrayList<Hologram>)entry.getValue(), paramPlayer); 
/* 199 */     }  if (str != null)
/* 200 */       hideHolograms(this.noFaction, paramPlayer); 
/* 201 */     for (Hologram hologram : arrayList) {
/* 202 */       if (!hologram.getVisibilityManager().isVisibleTo(paramPlayer))
/* 203 */         hologram.getVisibilityManager().showTo(paramPlayer); 
/* 204 */     }  if (arrayList.size() == 0)
/* 205 */       create(str); 
/*     */   }
/*     */   
/*     */   private void hideHolograms(ArrayList<Hologram> paramArrayList, Player paramPlayer) {
/* 209 */     if (paramArrayList == null)
/* 210 */       return;  for (Hologram hologram : paramArrayList) {
/* 211 */       if (hologram.getVisibilityManager().isVisibleTo(paramPlayer))
/* 212 */         hologram.getVisibilityManager().resetVisibility(paramPlayer); 
/*     */     } 
/*     */   }
/*     */   void updateAll() {
/* 216 */     for (String str : this.holograms.keySet())
/* 217 */       refresh(str); 
/*     */   }
/*     */   
/*     */   public void cleanUp(Player paramPlayer) {
/* 221 */     for (Hologram hologram : getPlayerHolograms(paramPlayer))
/* 222 */       hologram.getVisibilityManager().resetVisibility(paramPlayer); 
/*     */   }
/*     */   
/*     */   public enum HologramType {
/* 226 */     FACTION,
/* 227 */     TOP;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\HologramManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */