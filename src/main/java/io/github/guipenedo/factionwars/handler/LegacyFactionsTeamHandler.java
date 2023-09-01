/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.redstoneore.legacyfactions.Relation;
/*     */ import net.redstoneore.legacyfactions.Role;
/*     */ import net.redstoneore.legacyfactions.entity.FPlayer;
/*     */ import net.redstoneore.legacyfactions.entity.FPlayerColl;
/*     */ import net.redstoneore.legacyfactions.entity.Faction;
/*     */ import net.redstoneore.legacyfactions.entity.FactionColl;
/*     */ import net.redstoneore.legacyfactions.event.EventFactionsChange;
/*     */ import net.redstoneore.legacyfactions.event.EventFactionsCreate;
/*     */ import net.redstoneore.legacyfactions.event.EventFactionsDisband;
/*     */ import net.redstoneore.legacyfactions.event.EventFactionsNameChange;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class LegacyFactionsTeamHandler extends TeamHandler implements Listener {
/*     */   public LegacyFactionsTeamHandler(String paramString) {
/*  24 */     super(paramString);
/*     */   }
/*     */   
/*     */   private Faction getFaction(String paramString) {
/*  28 */     return FactionColl.get().getFactionById(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  33 */     Faction faction = getFaction(paramString);
/*  34 */     return (faction != null) ? faction.getTag() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  39 */     Faction faction = FactionColl.get().getByTag(paramString);
/*  40 */     return (faction != null) ? faction.getId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  45 */     Faction faction = getFaction(paramString);
/*  46 */     if (faction == null)
/*  47 */       return Collections.emptyList(); 
/*  48 */     return faction.getOnlinePlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  53 */     Faction faction1 = getFaction(paramString1), faction2 = getFaction(paramString2);
/*  54 */     if (faction1 == null || faction2 == null)
/*  55 */       return TeamHandler.Relation.NEUTRAL; 
/*  56 */     Relation relation = faction1.getRelationTo((RelationParticipator)faction2);
/*  57 */     return (relation == Relation.ALLY) ? TeamHandler.Relation.ALLY : ((relation == Relation.ENEMY) ? TeamHandler.Relation.ENEMY : TeamHandler.Relation.NEUTRAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  62 */     Faction faction = getFaction(paramString);
/*  63 */     return (faction != null) ? faction.getAccountId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  68 */     Faction faction = getFaction(paramString1);
/*  69 */     if (faction != null) {
/*  70 */       faction.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  75 */     ArrayList<String> arrayList = new ArrayList();
/*  76 */     for (Faction faction : FactionColl.get().getAllFactions()) {
/*  77 */       if (Integer.parseInt(faction.getId()) > 0)
/*  78 */         arrayList.add(faction.getId()); 
/*  79 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  84 */     Faction faction = getFaction(paramString);
/*  85 */     if (faction == null) return Collections.emptyList(); 
/*  86 */     ArrayList<Player> arrayList = new ArrayList();
/*  87 */     if (paramRole == TeamHandler.Role.ADMIN) {
/*  88 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.ADMIN))
/*  89 */         arrayList.add(fPlayer.getPlayer()); 
/*  90 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.COLEADER)) {
/*  91 */         arrayList.add(fPlayer.getPlayer());
/*     */       }
/*  93 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/*  94 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.MODERATOR))
/*  95 */         arrayList.add(fPlayer.getPlayer()); 
/*  96 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/*  97 */       return getOnlinePlayers(paramString);
/*  98 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/* 103 */     FPlayer fPlayer = FPlayerColl.get(paramObject);
/* 104 */     return (fPlayer != null && fPlayer.getFactionId() != null && Integer.parseInt(fPlayer.getFactionId()) > 0) ? fPlayer.getFactionId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onLegacyFactionCreate(final EventFactionsCreate e) {
/* 110 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run() {
/* 113 */           TeamHandlerListener.onTeamCreate(LegacyFactionsTeamHandler.this.getTeamIdByName(e.getFactionTag()));
/*     */         }
/* 115 */       }).runTaskLater((Plugin)FactionWars.get(), 20L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionDisband(EventFactionsDisband paramEventFactionsDisband) {
/* 120 */     TeamHandlerListener.onTeamDelete(paramEventFactionsDisband.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionRename(EventFactionsNameChange paramEventFactionsNameChange) {
/* 125 */     TeamHandlerListener.onTeamRename(paramEventFactionsNameChange.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFPlayerLeave(EventFactionsChange paramEventFactionsChange) {
/* 130 */     TeamHandlerListener.onTeamChange(paramEventFactionsChange.getFPlayer().getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\LegacyFactionsTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */