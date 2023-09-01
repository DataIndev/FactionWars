/*    */ package io.github.guipenedo.factionwars.listener;
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.managers.HologramManager;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class PlayerJoinListener implements Listener {
/*    */   @EventHandler
/*    */   public void onJoin(final PlayerJoinEvent e) {
/* 16 */     String str = FactionWars.getHandler().getPlayerTeam(e.getPlayer());
/* 17 */     if (str != null && FactionData.getFaction(str) == null)
/* 18 */       FactionData.factions.add(new FactionData(str)); 
/* 19 */     if (HologramManager.get().isEnabled())
/* 20 */       (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 23 */             HologramManager.get().handlePlayer(e.getPlayer());
/*    */           }
/* 25 */         }).runTaskLater((Plugin)FactionWars.get(), 10L); 
/* 26 */     if (!FactionWars.get().isUpdated() && (e.getPlayer().hasPermission("factionwars.admin.updatechecker") || e.getPlayer().hasPermission("factionwars.admin.*"))) {
/* 27 */       e.getPlayer().sendMessage("§3§k====================");
/* 28 */       e.getPlayer().sendMessage("§6§4FactionWars §av" + FactionWars.get().getLatestVersion() + "§6 is available! Running §cv" + FactionWars.get().getDescription().getVersion());
/* 29 */       e.getPlayer().sendMessage("§6Download at: §dspigotmc.org/resources/10961/");
/* 30 */       e.getPlayer().sendMessage("§3§k====================");
/*    */     } 
/* 32 */     Util.runMapChecks((CommandSender)e.getPlayer());
/* 33 */     if ((e.getPlayer().getName().equals("PhilipsNostrum") || e.getPlayer().getUniqueId().toString().equals("21eff28d-9940-446f-b401-db52d0356cc2")) && FactionWars.getMainConfig().getConfig().getBoolean("colored-dev"))
/* 34 */       e.getPlayer().setPlayerListName("§6" + e.getPlayer().getName()); 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\listener\PlayerJoinListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */