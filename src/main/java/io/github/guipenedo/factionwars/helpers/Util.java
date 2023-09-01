/*     */ package io.github.guipenedo.factionwars.helpers;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import io.github.guipenedo.factionwars.menus.BetSelector;
/*     */ import io.github.guipenedo.factionwars.menus.GamemodeSelector;
/*     */ import io.github.guipenedo.factionwars.menus.PlayerSelector;
/*     */ import io.github.guipenedo.factionwars.models.Gamemode;
/*     */ import io.github.guipenedo.factionwars.models.PlayerData;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ public class Util {
/*     */   private static Util instance;
/*     */   
/*     */   public static Util get() {
/*  34 */     if (instance == null)
/*  35 */       instance = new Util(); 
/*  36 */     return instance;
/*     */   }
/*     */   private static final String debugMessage = "§cThis server is running a §4PIRATED§c version of FactionWars. Please consider purchasing a copy from §6spigotmc.org/resources/10961/";
/*     */   public static boolean runMapChecks(CommandSender paramCommandSender) {
/*  40 */     if (Metrics.debugging) {
/*  41 */       paramCommandSender.sendMessage("§cThis server is running a §4PIRATED§c version of FactionWars. Please consider purchasing a copy from §6spigotmc.org/resources/10961/");
/*  42 */       return true;
/*     */     } 
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location stringToLocation(String paramString) {
/*  52 */     String[] arrayOfString = paramString.split(":");
/*  53 */     return new Location(FactionWars.get().getServer().getWorld(arrayOfString[0]), Double.parseDouble(arrayOfString[1]), Double.parseDouble(arrayOfString[2]), Double.parseDouble(arrayOfString[3]), Float.parseFloat(arrayOfString[4]), Float.parseFloat(arrayOfString[5]));
/*     */   }
/*     */   
/*     */   public String locationToString(Location paramLocation) {
/*  57 */     return paramLocation.getWorld().getName() + ":" + paramLocation.getX() + ":" + paramLocation.getY() + ":" + paramLocation.getZ() + ":" + paramLocation.getYaw() + ":" + paramLocation.getPitch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveInventory(PlayerInventory paramPlayerInventory, FileConfiguration paramFileConfiguration, String paramString) {
/*  72 */     Player player = (Player)paramPlayerInventory.getHolder();
/*  73 */     if (paramFileConfiguration == null)
/*     */       return; 
/*  75 */     paramString = paramString.toLowerCase();
/*     */     
/*  77 */     ItemStack[] arrayOfItemStack1 = paramPlayerInventory.getContents();
/*  78 */     ItemStack[] arrayOfItemStack2 = paramPlayerInventory.getArmorContents();
/*     */     
/*  80 */     if (paramFileConfiguration.contains(paramString)) {
/*  81 */       paramFileConfiguration.set(paramString, null);
/*     */     }
/*     */     byte b;
/*  84 */     for (b = 0; b < arrayOfItemStack1.length; b++) {
/*  85 */       ItemStack itemStack = arrayOfItemStack1[b];
/*  86 */       if (itemStack != null && itemStack.getType() != Material.AIR) paramFileConfiguration.set(paramString + ".slot." + b, itemStack);
/*     */     
/*     */     } 
/*  89 */     for (b = 0; b < arrayOfItemStack2.length; b++) {
/*  90 */       ItemStack itemStack = arrayOfItemStack2[b];
/*  91 */       if (itemStack != null && itemStack.getType() != Material.AIR) paramFileConfiguration.set(paramString + ".armor." + b, itemStack);
/*     */     
/*     */     } 
/*  94 */     for (PotionEffect potionEffect : player.getActivePotionEffects()) {
/*  95 */       paramFileConfiguration.set(paramString + ".effects." + potionEffect.getType().getName() + ".level", Integer.valueOf(potionEffect.getAmplifier()));
/*  96 */       paramFileConfiguration.set(paramString + ".effects." + potionEffect.getType().getName() + ".duration", Integer.valueOf(potionEffect.getDuration()));
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
/*     */   public ItemStack[] getInventory(FileConfiguration paramFileConfiguration, String paramString) {
/* 108 */     if (paramFileConfiguration == null) return null;
/*     */     
/* 110 */     if (paramFileConfiguration.contains(paramString + ".slot") && paramFileConfiguration.isConfigurationSection(paramString + ".slot")) {
/* 111 */       ArrayList<ItemStack> arrayList = new ArrayList();
/* 112 */       for (byte b = 0; b <= 40; b++) {
/* 113 */         if (paramFileConfiguration.contains(paramString + ".slot." + b)) { arrayList.add(b, paramFileConfiguration.getItemStack(paramString + ".slot." + b)); }
/* 114 */         else { arrayList.add(new ItemStack(Material.AIR)); }
/*     */       
/*     */       } 
/* 117 */       return arrayList.<ItemStack>toArray(new ItemStack[0]);
/*     */     } 
/*     */     
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getArmorContents(FileConfiguration paramFileConfiguration, String paramString) {
/* 129 */     if (paramFileConfiguration == null) return null; 
/* 130 */     ItemStack[] arrayOfItemStack = null;
/*     */     
/* 132 */     if (paramFileConfiguration.contains(paramString + ".armor") && paramFileConfiguration.isConfigurationSection(paramString + ".armor")) {
/* 133 */       int i = paramFileConfiguration.getInt(paramString + ".armor", 4);
/*     */       
/* 135 */       arrayOfItemStack = new ItemStack[i];
/*     */       
/* 137 */       for (byte b = 0; b < i; b++) {
/* 138 */         if (paramFileConfiguration.contains(paramString + ".armor." + b)) {
/* 139 */           arrayOfItemStack[b] = paramFileConfiguration.getItemStack(paramString + ".armor." + b);
/*     */         } else {
/* 141 */           arrayOfItemStack[b] = new ItemStack(Material.AIR);
/*     */         } 
/*     */       } 
/*     */     } 
/* 145 */     return arrayOfItemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PotionEffect> getPotionEffects(FileConfiguration paramFileConfiguration, String paramString) {
/* 154 */     if (paramFileConfiguration == null) return null; 
/* 155 */     HashSet<PotionEffect> hashSet = new HashSet();
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (paramFileConfiguration.contains(paramString + ".effects") && paramFileConfiguration.isConfigurationSection(paramString + ".effects")) {
/* 160 */       for (String str : paramFileConfiguration.getConfigurationSection(paramString + ".effects").getKeys(false)) {
/* 161 */         int i = paramFileConfiguration.getInt(paramString + ".effects." + str + ".level");
/* 162 */         int j = paramFileConfiguration.getInt(paramString + ".effects." + str + ".duration");
/* 163 */         PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(str), j, i);
/* 164 */         hashSet.add(potionEffect);
/*     */       } 
/*     */     }
/*     */     
/* 168 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(Player paramPlayer) {
/* 175 */     paramPlayer.getInventory().clear();
/* 176 */     paramPlayer.getInventory().setArmorContents(null);
/*     */     
/* 178 */     for (PotionEffect potionEffect : paramPlayer.getActivePotionEffects()) {
/* 179 */       paramPlayer.removePotionEffect(potionEffect.getType());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMessage(String paramString, Map<String, Object> paramMap) {
/* 188 */     String str = getPlainMessage(paramString, paramMap);
/*     */     
/* 190 */     if (!paramString.contains("title") && !paramString.equals("prefix") && !paramString.contains("commands.") && !paramString.contains("timer.")) {
/* 191 */       str = getMessage("prefix") + str;
/*     */     }
/* 193 */     return str.replaceAll("%", "");
/*     */   }
/*     */   
/*     */   public static String getPlainMessage(String paramString) {
/* 197 */     return FactionWars.getMessagesConfig().getConfig().getString(paramString).replaceAll("&", "§");
/*     */   }
/*     */   
/*     */   public static String getPlainMessage(String paramString, Map<String, Object> paramMap) {
/* 201 */     String str = getPlainMessage(paramString);
/*     */     
/* 203 */     if (paramMap != null)
/* 204 */       for (Map.Entry<String, Object> entry : paramMap.entrySet())
/* 205 */         str = str.replaceAll("%" + (String)entry.getKey() + "%", String.valueOf(entry.getValue()));  
/* 206 */     return str;
/*     */   }
/*     */   
/*     */   public static String getMessage(String paramString) {
/* 210 */     return getMessage(paramString, null);
/*     */   }
/*     */   
/*     */   public static String formatTimer(int paramInt) {
/* 214 */     int i = 0, j = paramInt % 60;
/* 215 */     if (paramInt >= 60) {
/* 216 */       i = (paramInt - j) / 60;
/*     */     }
/* 218 */     return i + ":" + String.format("%02d", new Object[] { Integer.valueOf(j) });
/*     */   }
/*     */   
/*     */   public static ChatColor getTeam1Color() {
/* 222 */     return ChatColor.getByChar(getPlainMessage("team-colors.team1"));
/*     */   }
/*     */   
/*     */   public static ChatColor getTeam2Color() {
/* 226 */     return ChatColor.getByChar(getPlainMessage("team-colors.team2"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<Player> getOnlineWithRole(String paramString, TeamHandler.Role paramRole) {
/* 233 */     ArrayList<Player> arrayList = new ArrayList();
/* 234 */     for (Player player : FactionWars.getHandler().getMembersWithRole(paramString, paramRole)) {
/* 235 */       if (player != null && player.isOnline())
/* 236 */         arrayList.add(player); 
/* 237 */     }  return arrayList;
/*     */   }
/*     */   
/*     */   public static int getMinPlayers() {
/* 241 */     return FactionWars.getMainConfig().getConfig().getInt("selection.minimum-per-faction");
/*     */   }
/*     */   
/*     */   private static int getMaxPlayers() {
/* 245 */     return FactionWars.getMainConfig().getConfig().getInt("selection.maximum-per-faction");
/*     */   }
/*     */   
/*     */   public static int getLimit(String paramString1, String paramString2) {
/* 249 */     if (FactionWars.getMainConfig().getConfig().getBoolean("selection.equal-number")) {
/* 250 */       PlayerSelection playerSelection = PlayerSelection.getByFactions(paramString2, paramString1);
/* 251 */       if (playerSelection != null && playerSelection.isConfirmed())
/* 252 */         return playerSelection.getPlayersSelected().size(); 
/* 253 */       int i = Math.min(getOnlineOptedin(paramString2).size(), getOnlineOptedin(paramString1).size());
/* 254 */       return ((getMaxPlayers() == -1) ? i : Math.min(i, getMaxPlayers())) - 1;
/*     */     } 
/* 256 */     return getMaxPlayers() - 1;
/*     */   }
/*     */   
/*     */   public static boolean doesNotHaveEnoughPlayers(String paramString) {
/* 260 */     return (getOnlineOptedin(paramString).size() < getMinPlayers());
/*     */   }
/*     */   
/*     */   public static boolean targetCantWar(String paramString) {
/* 264 */     return (getLeaders(paramString).size() == 0);
/*     */   }
/*     */   
/*     */   public static ArrayList<Player> getLeaders(String paramString) {
/* 268 */     ArrayList<Player> arrayList = new ArrayList<>(getOnlineWithRole(paramString, TeamHandler.Role.ADMIN));
/*     */     
/* 270 */     if (FactionWars.getMainConfig().getConfig().getBoolean("faction-mods-can-start")) {
/* 271 */       arrayList.addAll(getOnlineWithRole(paramString, TeamHandler.Role.MODERATOR));
/*     */     }
/* 273 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static ArrayList<Player> getOnlineOptedin(String paramString) {
/* 277 */     ArrayList<Player> arrayList = new ArrayList();
/* 278 */     for (Player player : FactionWars.getHandler().getOnlinePlayers(paramString)) {
/* 279 */       if (player != null && !PlayerData.getOptedOut().contains(player.getUniqueId()))
/* 280 */         arrayList.add(player); 
/* 281 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, Object> getVars(Object... paramVarArgs) {
/* 288 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 289 */     for (byte b = 1; b < paramVarArgs.length; b += 2)
/* 290 */       hashMap.put(String.valueOf(paramVarArgs[b - 1]), paramVarArgs[b]); 
/* 291 */     return (HashMap)hashMap;
/*     */   }
/*     */   
/*     */   public static String factionByString(String paramString) {
/* 295 */     String str = FactionWars.getHandler().getTeamIdByName(paramString);
/* 296 */     if (str == null) {
/* 297 */       Player player = FactionWars.get().getServer().getPlayer(paramString);
/* 298 */       if (player == null || FactionWars.getHandler().getPlayerTeam(player) == null)
/* 299 */         return null; 
/* 300 */       str = FactionWars.getHandler().getPlayerTeam(player);
/*     */     } 
/* 302 */     return str;
/*     */   }
/*     */   
/*     */   public static int compare(int... paramVarArgs) {
/* 306 */     if ((paramVarArgs.length & 0x1) != 0)
/* 307 */       throw new InvalidParameterException("Odd number of compare parameters given."); 
/* 308 */     for (byte b = 0; b < paramVarArgs.length - 2; b += 2) {
/* 309 */       if (paramVarArgs[b] != paramVarArgs[b + 1])
/* 310 */         return Integer.compare(paramVarArgs[b], paramVarArgs[b + 1]); 
/* 311 */     }  return Integer.compare(paramVarArgs[paramVarArgs.length - 2], paramVarArgs[paramVarArgs.length - 1]);
/*     */   }
/*     */   
/*     */   public static String substring16(String paramString) {
/* 315 */     return paramString.substring(0, Math.min(paramString.length(), 16));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void openNext(Player paramPlayer) {
/* 320 */     PlayerSelection playerSelection = PlayerSelection.getBySelector(paramPlayer.getUniqueId());
/* 321 */     FactionWars.debug(String.valueOf(playerSelection.getBet()));
/* 322 */     FactionWars.debug(String.valueOf(FactionWars.getMainConfig().getConfig().getDouble("selection.bets.minimum")));
/* 323 */     FactionWars.debug(String.valueOf((FactionWars.getEcon() != null)));
/* 324 */     if (playerSelection.getGamemode() == null) {
/* 325 */       FactionWars.debug("next: gamemode");
/* 326 */       playerSelection.setGamemode(Gamemode.getDefault());
/* 327 */       GamemodeSelector gamemodeSelector = new GamemodeSelector(paramPlayer);
/* 328 */       gamemodeSelector.open();
/* 329 */     } else if ((playerSelection.getBet() == null || playerSelection.getBet().doubleValue() < FactionWars.getMainConfig().getConfig().getDouble("selection.bets.minimum")) && FactionWars.getMainConfig().getConfig().getDouble("selection.bets.maximum") > 0.0D && FactionWars.getEcon() != null) {
/* 330 */       FactionWars.debug("next: bets");
/* 331 */       playerSelection.setBet(Double.valueOf(0.0D));
/* 332 */       BetSelector betSelector = new BetSelector(paramPlayer);
/* 333 */       betSelector.open();
/*     */     } else {
/* 335 */       FactionWars.debug("next: selector");
/* 336 */       PlayerSelector playerSelector = new PlayerSelector(paramPlayer);
/* 337 */       playerSelector.open();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean validBet(Player paramPlayer, double paramDouble) {
/* 342 */     String str = FactionWars.getHandler().getPlayerTeam(paramPlayer);
/* 343 */     if (str == null) return false; 
/* 344 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 345 */     if (paramDouble < FactionWars.getMainConfig().getConfig().getDouble("selection.bets.minimum") || paramDouble > 
/* 346 */       FactionWars.getMainConfig().getConfig().getDouble("selection.bets.maximum")) {
/* 347 */       hashMap.put("min", Double.valueOf(FactionWars.getMainConfig().getConfig().getDouble("selection.bets.minimum")));
/* 348 */       hashMap.put("max", Double.valueOf(FactionWars.getMainConfig().getConfig().getDouble("selection.bets.maximum")));
/* 349 */       paramPlayer.sendMessage(getMessage("error.bet-value", (Map)hashMap));
/* 350 */       return false;
/*     */     } 
/* 352 */     if (FactionWars.getHandler().getBalance(str) < paramDouble) {
/* 353 */       paramPlayer.sendMessage(getMessage("error.no-money", (Map)hashMap));
/* 354 */       return false;
/*     */     } 
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   public static void logWar(String paramString1, String paramString2) {
/* 360 */     String str = "wars.log";
/* 361 */     File file = new File(FactionWars.get().getDataFolder(), "wars.log");
/*     */     try {
/* 363 */       if (!file.exists()) {
/* 364 */         file.getParentFile().mkdirs();
/* 365 */         file.createNewFile();
/*     */       } 
/* 367 */       FileWriter fileWriter = new FileWriter(file, true);
/* 368 */       LocalDateTime localDateTime = LocalDateTime.now();
/* 369 */       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
/* 370 */       fileWriter.write(localDateTime.format(dateTimeFormatter) + " W:" + paramString1 + " L:" + paramString2 + System.lineSeparator());
/* 371 */       fileWriter.close();
/* 372 */     } catch (IOException iOException) {
/* 373 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */