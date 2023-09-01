/*    */ package io.github.guipenedo.factionwars.helpers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.SettingsManager;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.FireworkEffect;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Firework;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.meta.FireworkMeta;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class Rewards {
/*    */   public static void handleBets(String paramString1, String paramString2, double paramDouble) {
/* 23 */     FactionWars.debug("handling bet: " + paramDouble);
/* 24 */     if (paramDouble > 0.0D) {
/* 25 */       FactionWars.getHandler().changeBalance(paramString2, -paramDouble);
/* 26 */       FactionWars.getHandler().changeBalance(paramString1, paramDouble);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void fireworks(final Player p, final int seconds, final int amount) {
/* 31 */     final ArrayList types = new ArrayList(Arrays.asList((Object[])new FireworkEffect.Type[] { FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.STAR, FireworkEffect.Type.CREEPER }));
/* 32 */     final ArrayList colours = new ArrayList(Arrays.asList((Object[])new Color[] { Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW }));
/* 33 */     final long startTime = System.currentTimeMillis();
/* 34 */     if (FactionWars.get().isEnabled())
/* 35 */       (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 38 */             if (System.currentTimeMillis() >= startTime + (seconds * 1000) || FactionWars.get().getServer().getPlayer(p.getUniqueId()) == null) {
/* 39 */               cancel();
/*    */             } else {
/* 41 */               for (byte b = 0; b < amount; b++) {
/* 42 */                 Location location = p.getLocation();
/* 43 */                 Firework firework = (Firework)p.getLocation().getWorld().spawn(location, Firework.class);
/* 44 */                 FireworkMeta fireworkMeta = firework.getFireworkMeta();
/* 45 */                 fireworkMeta.addEffects(new FireworkEffect[] { FireworkEffect.builder().withColor(this.val$colours.get((new Random()).nextInt(17))).withColor(this.val$colours.get((new Random()).nextInt(17))).withColor(this.val$colours.get((new Random()).nextInt(17))).with(this.val$types.get((new Random()).nextInt(5))).trail((new Random()).nextBoolean()).flicker((new Random()).nextBoolean()).build() });
/* 46 */                 fireworkMeta.setPower((new Random()).nextInt(2) + 2);
/* 47 */                 firework.setFireworkMeta(fireworkMeta);
/*    */               } 
/*    */             } 
/*    */           }
/* 51 */         }).runTaskTimer((Plugin)FactionWars.get(), 0L, 5L); 
/*    */   }
/*    */   
/*    */   public static void reward(OfflinePlayer paramOfflinePlayer, String paramString1, String paramString2, WarMap paramWarMap) {
/* 55 */     for (String str1 : SettingsManager.getStringList("rewards.commands", paramWarMap)) {
/* 56 */       String str2 = str1.replaceAll("%player%", paramOfflinePlayer.getName()).replaceAll("%winner%", FactionWars.getHandler().getTeamName(paramString1)).replace("%looser%", FactionWars.getHandler().getTeamName(paramString2));
/* 57 */       FactionWars.debug("Running command:");
/* 58 */       FactionWars.debug(str2);
/* 59 */       FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str2);
/*    */     } 
/* 61 */     double d = SettingsManager.getDouble("rewards.money", paramWarMap);
/* 62 */     if (d > 0.0D && FactionWars.getEcon() != null && FactionWars.getEcon().isValid()) {
/* 63 */       FactionWars.getEcon().deposit(paramOfflinePlayer.getUniqueId(), d);
/* 64 */       FactionWars.getEcon().transaction(d);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void punish(OfflinePlayer paramOfflinePlayer, String paramString1, String paramString2, WarMap paramWarMap) {
/* 69 */     for (String str1 : SettingsManager.getStringList("punishments.commands", paramWarMap)) {
/* 70 */       String str2 = str1.replaceAll("%player%", paramOfflinePlayer.getName()).replaceAll("%winner%", FactionWars.getHandler().getTeamName(paramString1)).replace("%looser%", FactionWars.getHandler().getTeamName(paramString2));
/* 71 */       FactionWars.debug("Running command");
/* 72 */       FactionWars.debug(str2);
/* 73 */       FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str2);
/*    */     } 
/* 75 */     double d = SettingsManager.getDouble("punishments.money", paramWarMap);
/* 76 */     if (d > 0.0D && FactionWars.getEcon() != null && FactionWars.getEcon().isValid()) {
/* 77 */       FactionWars.getEcon().withdraw(paramOfflinePlayer.getUniqueId(), d);
/* 78 */       FactionWars.getEcon().transaction(-d);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void runEndCommands(String paramString1, String paramString2, WarMap paramWarMap) {
/* 83 */     for (String str1 : SettingsManager.getStringList("end-game.commands", paramWarMap)) {
/* 84 */       if ((str1.contains("%winner%") || str1.contains("%looser%")) && (paramString1 == null || paramString2 == null))
/*    */         continue; 
/* 86 */       String str2 = str1;
/* 87 */       if (paramString1 != null && paramString2 != null)
/* 88 */         str2 = str1.replaceAll("%winner%", FactionWars.getHandler().getTeamName(paramString1)).replace("%looser%", FactionWars.getHandler().getTeamName(paramString2)); 
/* 89 */       if (str2.contains("%player%")) {
/* 90 */         for (Player player : paramWarMap.getPlayerList()) {
/* 91 */           str2 = str2.replaceAll("%player%", player.getName());
/* 92 */           FactionWars.debug("Running command:");
/* 93 */           FactionWars.debug(str2);
/* 94 */           FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str2);
/*    */         } 
/*    */         continue;
/*    */       } 
/* 98 */       FactionWars.get().getServer().dispatchCommand((CommandSender)FactionWars.get().getServer().getConsoleSender(), str2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Rewards.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */