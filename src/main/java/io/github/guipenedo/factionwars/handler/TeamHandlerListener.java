/*    */ package io.github.guipenedo.factionwars.handler;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.HologramManager;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import io.github.guipenedo.factionwars.stats.DatabaseHelper;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class TeamHandlerListener {
/*    */   public static void onTeamCreate(final String id) {
/* 13 */     FactionWars.debug("Faction " + id + " created ");
/* 14 */     if (id == null)
/* 15 */       return;  FactionData.factions.add(new FactionData(id));
/*    */     
/* 17 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 20 */           HologramManager.get().create(id);
/* 21 */           for (Player player : FactionWars.getHandler().getOnlinePlayers(id))
/* 22 */             HologramManager.get().handlePlayer(player); 
/*    */         }
/* 24 */       }).runTaskLater((Plugin)FactionWars.get(), 20L);
/*    */   }
/*    */   
/*    */   public static void onTeamDelete(final String id) {
/* 28 */     FactionWars.debug("Faction " + id + " deleted ");
/* 29 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 32 */           DatabaseHelper.get().removeFactionData(id);
/*    */         }
/* 34 */       }).runTaskAsynchronously((Plugin)FactionWars.get());
/* 35 */     FactionData factionData = FactionData.getFaction(id);
/* 36 */     if (factionData != null)
/* 37 */       FactionData.factions.remove(factionData); 
/* 38 */     if (HologramManager.get().isEnabled()) {
/* 39 */       HologramManager.get().delete(id);
/* 40 */       (new BukkitRunnable()
/*    */         {
/*    */           public void run() {
/* 43 */             for (Player player : FactionWars.get().getServer().getOnlinePlayers())
/* 44 */               HologramManager.get().handlePlayer(player); 
/*    */           }
/* 46 */         }).runTaskLater((Plugin)FactionWars.get(), 10L);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void onTeamChange(final Player player) {
/* 51 */     if (!HologramManager.get().isEnabled() || player == null)
/*    */       return; 
/* 53 */     FactionWars.debug("player " + player.getName() + " changed team ");
/* 54 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 57 */           HologramManager.get().handlePlayer(player);
/*    */         }
/* 59 */       }).runTaskLater((Plugin)FactionWars.get(), 20L);
/*    */   }
/*    */   
/*    */   public static void onTeamRename(final String id) {
/* 63 */     FactionWars.debug("Faction " + id + " renamed ");
/* 64 */     DatabaseHelper.get().saveStats(FactionData.getFaction(id));
/* 65 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 68 */           HologramManager.get().refresh(id);
/*    */         }
/* 70 */       }).runTaskLater((Plugin)FactionWars.get(), 20L);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\TeamHandlerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */