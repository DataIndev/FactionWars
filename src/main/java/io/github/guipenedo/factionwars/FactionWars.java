/*     */ package io.github.guipenedo.factionwars;
/*     */ import io.github.guipenedo.factionwars.api.FactionWarsAddonPlugin;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import io.github.guipenedo.factionwars.commands.WarsCommand;
/*     */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*     */ import io.github.guipenedo.factionwars.handler.SavageTeamHandler;
/*     */ import io.github.guipenedo.factionwars.helpers.Economy;
/*     */ import io.github.guipenedo.factionwars.helpers.Metrics;
/*     */ import io.github.guipenedo.factionwars.listener.PlayerCommandPrepocessListener;
/*     */ import io.github.guipenedo.factionwars.listener.PlayerDropListener;
/*     */ import io.github.guipenedo.factionwars.listener.PlayerInteractListener;
/*     */ import io.github.guipenedo.factionwars.managers.HologramManager;
/*     */ import io.github.guipenedo.factionwars.models.Config;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import io.github.guipenedo.factionwars.models.Gamemode;
/*     */ import io.github.guipenedo.factionwars.models.Kit;
/*     */ import io.github.guipenedo.factionwars.models.PlayerData;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import io.github.guipenedo.factionwars.stats.Database;
/*     */ import io.github.guipenedo.factionwars.stats.DatabaseHelper;
/*     */ import io.github.guipenedo.factionwars.stats.MySQLCore;
/*     */ import io.github.guipenedo.factionwars.stats.SQLiteCore;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.CommandMap;
/*     */ import org.bukkit.command.defaults.BukkitCommand;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class FactionWars extends JavaPlugin {
/*     */   private static FactionWars instance;
/*     */   private Config mainConfig;
/*     */   
/*     */   public ArrayList<FactionWarsAddonPlugin> getAddonPlugins() {
/*  47 */     return this.addonPlugins;
/*     */   }
/*     */   private Config kitsConfig; private Config mapsConfig; private Config messagesConfig; private Config gamemodesConfig; private Economy economy; private Database database; private String latestVersion; private ArrayList<FactionWarsAddonPlugin> addonPlugins;
/*     */   private TeamHandler teamHandler;
/*     */   
/*     */   public static TeamHandler getHandler() {
/*  53 */     return instance.teamHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Economy getEcon() {
/*  59 */     return instance.economy;
/*     */   }
/*     */   
/*     */   public static Database getDB() {
/*  63 */     return instance.database;
/*     */   }
/*     */   
/*     */   public static Config getMainConfig() {
/*  67 */     return instance.mainConfig;
/*     */   }
/*     */   
/*     */   public static Config getGamemodesConfig() {
/*  71 */     return instance.gamemodesConfig;
/*     */   }
/*     */   
/*     */   public static Config getMessagesConfig() {
/*  75 */     return instance.messagesConfig;
/*     */   }
/*     */   
/*     */   public static Config getKitsConfig() {
/*  79 */     return instance.kitsConfig;
/*     */   }
/*     */   
/*     */   public static Config getMapsConfig() {
/*  83 */     return instance.mapsConfig;
/*     */   }
/*     */   
/*     */   public static FactionWars get() {
/*  87 */     return instance;
/*     */   }
/*     */   
/*     */   public static void debug(String paramString) {
/*  91 */     if (getMainConfig().getConfig().getBoolean("debug"))
/*  92 */       System.out.println(paramString); 
/*     */   }
/*     */   
/*     */   public boolean isUpdated() {
/*  96 */     return getDescription().getVersion().equals(this.latestVersion);
/*     */   }
/*     */   
/*     */   public String getLatestVersion() {
/* 100 */     return this.latestVersion;
/*     */   }
/*     */   
/*     */   private void addListeners(Listener... paramVarArgs) {
/* 104 */     for (Listener listener : paramVarArgs)
/* 105 */       getServer().getPluginManager().registerEvents(listener, (Plugin)this); 
/*     */   }
/*     */   
/*     */   public void onEnable() {
/* 109 */     loadConfig0(); instance = this;
/* 110 */     this.latestVersion = getDescription().getVersion();
/*     */     
/* 112 */     loadConfigs();
/*     */     
/* 114 */     loadAddonPlugins();
/*     */ 
/*     */     
/* 117 */     hookTeams();
/* 118 */     if (this.teamHandler == null) {
/* 119 */       getServer().getLogger().severe("[FactionWars] Could not find compatible teams plugin! Disabling.");
/* 120 */       getServer().getPluginManager().disablePlugin((Plugin)this);
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     load();
/*     */ 
/*     */     
/* 127 */     addListeners(new Listener[] { (Listener)new PlayerDeathListener(), (Listener)new PlayerQuitListener(), (Listener)new PlayerMoveListener(), (Listener)new PlayerJoinListener(), (Listener)new PlayerInteractListener(), (Listener)new PlayerInteractListener(), (Listener)new PlayerTeleportListener(), (Listener)new PlayerDropListener(), (Listener)new PlayerPickupListener() });
/*     */     
/* 129 */     if (getMainConfig().getConfig().getBoolean("disable-commands.enabled")) {
/* 130 */       addListeners(new Listener[] { (Listener)new PlayerCommandPrepocessListener() });
/*     */     }
/*     */     
/* 133 */     getCommand("fw").setExecutor((CommandExecutor)new MainCommand());
/*     */ 
/*     */     
/* 136 */     if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
/* 137 */       (new PlaceholderManager()).register();
/*     */     }
/* 139 */     WarsCommand warsCommand = new WarsCommand(getMainConfig().getConfig().getString("command.main"));
/* 140 */     warsCommand.addSubCommand((WarsSubCommand)new WarsAcceptCommand());
/* 141 */     warsCommand.addSubCommand((WarsSubCommand)new WarsDeclineCommand());
/* 142 */     warsCommand.addSubCommand((WarsSubCommand)new WarsInviteCommand());
/* 143 */     warsCommand.addSubCommand((WarsSubCommand)new WarsListCommand());
/* 144 */     warsCommand.addSubCommand((WarsSubCommand)new WarsStatsCommand());
/* 145 */     warsCommand.addSubCommand((WarsSubCommand)new WarsTopCommand());
/* 146 */     warsCommand.addSubCommand((WarsSubCommand)new WarsGUICommand());
/* 147 */     if (getMainConfig().getConfig().getBoolean("enable-war-opt-out"))
/* 148 */       warsCommand.addSubCommand((WarsSubCommand)new WarsToggleCommand()); 
/* 149 */     warsCommand.setAliases(getMainConfig().getConfig().getStringList("command.aliases"));
/* 150 */     warsCommand.setDescription(getMainConfig().getConfig().getString("command.description"));
/* 151 */     registerCommand(getMainConfig().getConfig().getString("command.main"), (BukkitCommand)warsCommand);
/*     */     
/* 153 */     if (getMainConfig().getConfig().getBoolean("update-checker"))
/* 154 */       (new UpdateChecker()).runTaskAsynchronously((Plugin)this); 
/*     */   }
/*     */   
/*     */   private void loadAddonPlugins() {
/* 158 */     this.addonPlugins = new ArrayList<>();
/* 159 */     for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
/* 160 */       if (plugin instanceof FactionWarsAddonPlugin) {
/* 161 */         getPluginLoader().enablePlugin(plugin);
/* 162 */         this.addonPlugins.add((FactionWarsAddonPlugin)plugin);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void hookTeams() {
/* 167 */     for (FactionWarsAddonPlugin factionWarsAddonPlugin : this.addonPlugins) {
/* 168 */       this.teamHandler = factionWarsAddonPlugin.getTeamHandler();
/* 169 */       if (this.teamHandler != null) {
/* 170 */         get().getLogger().info("Hooked into custom teams plugin " + this.teamHandler.getName() + " successfully!");
/*     */         return;
/*     */       } 
/*     */     } 
/* 174 */     hook("Towny", (Class)TownyTeamHandler.class, new String[] { "com.palmergames.bukkit.towny.Towny" });
/* 175 */     hook("SavageFactions", (Class)SavageTeamHandler.class, new String[] { "com.massivecraft.factions.SavageFactions" });
/* 176 */     hook("FabledKingdoms", (Class)KingdomsTeamHandler.class, new String[] { "com.songoda.kingdoms.main.Kingdoms" });
/* 177 */     hook("LegacyFactions", (Class)LegacyFactionsTeamHandler.class, new String[] { "net.redstoneore.legacyfactions.Factions" });
/* 178 */     hook("FactionsUUID", (Class)FactionsUUIDTeamHandler.class, new String[] { "com.massivecraft.factions.perms.Relation" });
/* 179 */     hook("SaberFactions", (Class)SavageTeamHandler.class, new String[] { "com.massivecraft.factions.FactionsPlugin" });
/* 180 */     hook("Factions3", (Class)Factions3TeamHandler.class, new String[] { "com.massivecraft.factions.entity.Rank" });
/* 181 */     hook("Factions", (Class)FactionsTeamHandler.class, new String[] { "com.massivecraft.factions.Rel" });
/* 182 */     hook("Guilds", (Class)GuildsTeamHandler.class, new String[] { "me.glaremasters.guilds.Guilds" });
/* 183 */     hook("McMMOParties", (Class)McMMOTeamHandler.class, new String[] { "com.gmail.nossr50.mcMMO" });
/*     */   }
/*     */   
/*     */   private void registerCommand(String paramString, BukkitCommand paramBukkitCommand) {
/*     */     try {
/* 188 */       Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
/* 189 */       field.setAccessible(true);
/* 190 */       CommandMap commandMap = (CommandMap)field.get(Bukkit.getServer());
/* 191 */       commandMap.register(paramString, (Command)paramBukkitCommand);
/* 192 */     } catch (IllegalAccessException|IllegalArgumentException|NoSuchFieldException|SecurityException illegalAccessException) {
/* 193 */       illegalAccessException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hook(String paramString, Class<? extends TeamHandler> paramClass, String... paramVarArgs) {
/*     */     try {
/* 199 */       if (packagesExists(paramVarArgs) && this.teamHandler == null) {
/* 200 */         this.teamHandler = paramClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
/* 201 */         Bukkit.getPluginManager().registerEvents((Listener)this.teamHandler, (Plugin)this);
/* 202 */         get().getLogger().info("Hooked into " + paramString + " successfully!");
/*     */       } 
/* 204 */     } catch (Exception exception) {
/* 205 */       getLogger().severe(String.format("[FactionWars] There was an error hooking %s - check to make sure you're using a compatible version!", new Object[] { paramString }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean packagesExists(String... paramVarArgs) {
/*     */     try {
/* 211 */       for (String str : paramVarArgs) {
/* 212 */         Class.forName(str, false, getClass().getClassLoader());
/*     */       }
/* 214 */       return true;
/* 215 */     } catch (Exception exception) {
/* 216 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 221 */     getServer().getScheduler().cancelTasks((Plugin)this);
/* 222 */     for (WarMap warMap : WarMap.getMaps().values()) {
/* 223 */       if (warMap.getMatchState() != WarMap.MatchState.EMPTY)
/* 224 */         MatchManager.get().endGame(warMap, true); 
/*     */     } 
/* 226 */     for (PlayerData playerData : PlayerData.getPlayerData()) {
/* 227 */       playerData.teleport();
/* 228 */       playerData.restore();
/*     */     } 
/* 230 */     PlayerData.getPlayerData().clear();
/* 231 */     for (FactionData factionData : FactionData.factions)
/* 232 */       DatabaseHelper.get().saveStats(factionData); 
/* 233 */     for (String str : new ArrayList((HologramManager.get()).holograms.keySet()))
/* 234 */       HologramManager.get().delete(str); 
/* 235 */     if (getDB() != null)
/* 236 */       getDB().close(); 
/* 237 */     PlayerSelection.getPlayerSelections().clear();
/* 238 */     this.database = null;
/*     */   }
/*     */   
/*     */   public void load() {
/* 242 */     loadConfigs();
/*     */     
/* 244 */     debug("Loaded configs");
/*     */     
/* 246 */     this.economy = new Economy();
/*     */ 
/*     */     
/* 249 */     loadKits();
/*     */     
/* 251 */     loadGamemodes();
/*     */     
/* 253 */     loadMaps();
/*     */     
/* 255 */     connectDatabase();
/*     */ 
/*     */     
/* 258 */     FactionData.factions.clear();
/* 259 */     if (this.database.getConnection() != null) {
/* 260 */       debug("starting le loop");
/* 261 */       for (String str : this.teamHandler.getAllTeams()) {
/* 262 */         debug("looping faction ids: " + str);
/* 263 */         if (FactionData.getFaction(str) == null)
/* 264 */           FactionData.factions.add(new FactionData(str)); 
/*     */       } 
/* 266 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/* 269 */             for (String str : DatabaseHelper.getAllIds()) {
/* 270 */               if (FactionData.getFaction(str) == null)
/* 271 */                 DatabaseHelper.get().removeFactionData(str); 
/*     */             }  }
/* 273 */         }).runTaskAsynchronously((Plugin)this);
/*     */     } 
/*     */     
/* 276 */     debug("Loading match timer settings");
/* 277 */     debug("Loading rewards settings: end commands, rewards and punishments");
/*     */     
/* 279 */     if (getMainConfig().getConfig().getBoolean("disable-commands.enabled")) {
/* 280 */       PlayerCommandPrepocessListener.enabledCommands = getMainConfig().getConfig().getStringList("disable-commands.exceptions");
/*     */     }
/* 282 */     PlayerData.getOptedOut().clear();
/*     */     
/* 284 */     HologramManager.get().load();
/*     */ 
/*     */     
/* 287 */     Metrics metrics = new Metrics((Plugin)this);
/* 288 */     metrics.addCustomChart((Metrics.CustomChart)new Metrics.SimplePie("fwcore_factions_version", () -> this.teamHandler.getName()));
/* 289 */     metrics.addCustomChart((Metrics.CustomChart)new Metrics.AdvancedPie("maps_by_gamemode", new Callable<Map<String, Integer>>()
/*     */           {
/*     */             public Map<String, Integer> call() {
/* 292 */               HashMap<Object, Object> hashMap = new HashMap<>();
/* 293 */               for (Gamemode gamemode : Gamemode.getSetupGamemodes().values())
/* 294 */                 hashMap.put(gamemode.getGamemodeType().name(), Integer.valueOf(countMaps(gamemode))); 
/* 295 */               return (Map)hashMap;
/*     */             }
/*     */             
/*     */             private int countMaps(Gamemode param1Gamemode) {
/* 299 */               byte b = 0;
/* 300 */               for (WarMap warMap : WarMap.getMaps().values()) {
/* 301 */                 if (warMap.isSetup() && warMap.getGamemode().equals(param1Gamemode))
/* 302 */                   b++; 
/* 303 */               }  return b;
/*     */             }
/*     */           }));
/*     */     
/* 307 */     getLogger().info("Finished loading.");
/*     */   }
/*     */   
/*     */   public void loadMaps() {
/* 311 */     WarMap.getMaps().clear();
/* 312 */     if (this.mapsConfig.getConfig().contains("maps.1")) {
/* 313 */       for (String str : this.mapsConfig.getConfig().getConfigurationSection("maps").getKeys(false)) {
/* 314 */         WarMap warMap = new WarMap(str);
/* 315 */         if (warMap.getGamemode() != null) {
/* 316 */           WarMap.getMaps().put(str, warMap); continue;
/*     */         } 
/* 318 */         getLogger().warning("Could not load map " + warMap.getName() + "! Gamemode " + warMap.getGamemode() + " not found or invalid!");
/*     */       } 
/*     */     }
/* 321 */     getLogger().info("Loaded " + WarMap.getMaps().size() + " map(s).");
/*     */   }
/*     */   
/*     */   private void loadGamemodes() {
/* 325 */     Gamemode.getGamemodes().clear();
/* 326 */     Gamemode.defaultGamemode = this.gamemodesConfig.getConfig().getString("default-gamemode");
/* 327 */     if (this.gamemodesConfig.getConfig().contains("gamemodes")) {
/* 328 */       for (String str : this.gamemodesConfig.getConfig().getConfigurationSection("gamemodes").getKeys(false)) {
/* 329 */         Gamemode.getGamemodes().put(str, new Gamemode(str));
/*     */       }
/*     */     }
/* 332 */     getLogger().info("Loaded " + Gamemode.getGamemodes().size() + " gamemode(s).");
/*     */   }
/*     */   
/*     */   public void loadKits() {
/* 336 */     Kit.getKits().clear();
/* 337 */     if (this.kitsConfig.getConfig().contains("kits.1")) {
/* 338 */       for (String str : this.kitsConfig.getConfig().getConfigurationSection("kits").getKeys(false)) {
/* 339 */         Kit.getKits().add(new Kit(str));
/*     */       }
/*     */     }
/* 342 */     getLogger().info("Loaded " + Kit.getKits().size() + " kit(s).");
/*     */   }
/*     */   private void connectDatabase() {
/*     */     try {
/*     */       SQLiteCore sQLiteCore;
/* 347 */       ConfigurationSection configurationSection = getMainConfig().getConfig().getConfigurationSection("mysql");
/*     */ 
/*     */       
/* 350 */       if (getMainConfig().getConfig().getBoolean("stats.use-mysql")) {
/* 351 */         MySQLCore mySQLCore = new MySQLCore(configurationSection.getString("hostname"), configurationSection.getString("username"), configurationSection.getString("password"), configurationSection.getString("database"), configurationSection.getString("port"));
/*     */       } else {
/* 353 */         sQLiteCore = new SQLiteCore(new File(getDataFolder(), "stats.db"));
/*     */       } 
/* 355 */       this.database = new Database((DatabaseCore)sQLiteCore, getMainConfig().getConfig().getString("stats.table"));
/* 356 */       DatabaseHelper.get().setup(this.database);
/* 357 */     } catch (io.github.guipenedo.factionwars.stats.Database.ConnectionException connectionException) {
/* 358 */       connectionException.printStackTrace();
/* 359 */       getLogger().severe("Error connecting to database. Disabling stats.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadConfigs() {
/* 364 */     this.mainConfig = new Config("config.yml");
/* 365 */     this.mainConfig.loadDefaults();
/*     */     
/* 367 */     this.kitsConfig = new Config("kits.yml");
/*     */     
/* 369 */     this.gamemodesConfig = new Config("gamemodes.yml");
/*     */     
/* 371 */     this.mapsConfig = new Config("maps.yml");
/*     */     
/* 373 */     this.messagesConfig = new Config("messages.yml");
/* 374 */     this.messagesConfig.loadDefaults();
/*     */   }
/*     */   
/*     */   private class UpdateChecker extends BukkitRunnable {
/*     */     private UpdateChecker() {}
/*     */     
/*     */     public void run() {
/*     */       try {
/* 382 */         HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("\nhttps://api.spigotmc.org/legacy/update.php?resource=10961")).openConnection();
/* 383 */         FactionWars.this.latestVersion = (new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))).readLine();
/* 384 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\FactionWars.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */