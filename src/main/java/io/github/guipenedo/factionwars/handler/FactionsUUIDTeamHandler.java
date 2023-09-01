/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import com.massivecraft.factions.Faction;
/*     */ import com.massivecraft.factions.Factions;
/*     */ import com.massivecraft.factions.event.FPlayerJoinEvent;
/*     */ import com.massivecraft.factions.event.FPlayerLeaveEvent;
/*     */ import com.massivecraft.factions.event.FactionDisbandEvent;
/*     */ import com.massivecraft.factions.event.FactionRenameEvent;
/*     */ import com.massivecraft.factions.perms.Relation;
/*     */ import com.massivecraft.factions.perms.Role;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ 
/*     */ public class FactionsUUIDTeamHandler extends TeamHandler implements Listener {
/*     */   public FactionsUUIDTeamHandler(String paramString) {
/*  21 */     super(paramString);
/*     */   }
/*     */   
/*     */   private Faction getFaction(String paramString) {
/*  25 */     return Factions.getInstance().getFactionById(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  30 */     Faction faction = getFaction(paramString);
/*  31 */     return (faction != null) ? faction.getTag() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  36 */     Faction faction = Factions.getInstance().getByTag(paramString);
/*  37 */     return (faction != null) ? faction.getId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  42 */     Faction faction = getFaction(paramString);
/*  43 */     if (faction == null)
/*  44 */       return Collections.emptyList(); 
/*  45 */     return faction.getOnlinePlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  50 */     Faction faction1 = getFaction(paramString1), faction2 = getFaction(paramString2);
/*  51 */     if (faction1 == null || faction2 == null) {
/*  52 */       return TeamHandler.Relation.NEUTRAL;
/*     */     }
/*  54 */     Relation relation = faction1.getRelationTo((RelationParticipator)faction2);
/*  55 */     return (relation == Relation.ALLY) ? TeamHandler.Relation.ALLY : ((relation == Relation.ENEMY) ? TeamHandler.Relation.ENEMY : TeamHandler.Relation.NEUTRAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  60 */     Faction faction = getFaction(paramString);
/*  61 */     return (faction != null) ? faction.getAccountId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  66 */     Faction faction = getFaction(paramString1);
/*  67 */     if (faction != null) {
/*  68 */       faction.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  73 */     ArrayList<String> arrayList = new ArrayList();
/*  74 */     for (Faction faction : Factions.getInstance().getAllFactions()) {
/*  75 */       if (Integer.parseInt(faction.getId()) > 0)
/*  76 */         arrayList.add(faction.getId()); 
/*  77 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  82 */     Faction faction = getFaction(paramString);
/*  83 */     if (faction == null) return Collections.emptyList(); 
/*  84 */     ArrayList<Player> arrayList = new ArrayList();
/*  85 */     if (paramRole == TeamHandler.Role.ADMIN) {
/*     */       
/*  87 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.ADMIN)) {
/*  88 */         arrayList.add(fPlayer.getPlayer());
/*     */       }
/*  90 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.COLEADER)) {
/*  91 */         arrayList.add(fPlayer.getPlayer());
/*     */       }
/*  93 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/*     */       
/*  95 */       for (FPlayer fPlayer : faction.getFPlayersWhereRole(Role.MODERATOR))
/*  96 */         arrayList.add(fPlayer.getPlayer()); 
/*  97 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/*  98 */       return getOnlinePlayers(paramString);
/*  99 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/*     */     FPlayer fPlayer;
/* 105 */     if (paramObject instanceof String)
/* 106 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 107 */     if (paramObject == null)
/* 108 */       return null; 
/* 109 */     if (paramObject instanceof Player)
/* 110 */     { fPlayer = FPlayers.getInstance().getByPlayer((Player)paramObject); }
/* 111 */     else if (paramObject instanceof OfflinePlayer)
/* 112 */     { fPlayer = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)paramObject); }
/* 113 */     else { return null; }
/* 114 */      return (fPlayer != null && fPlayer.getFaction() != null && Integer.parseInt(fPlayer.getFactionId()) > 0) ? fPlayer.getFactionId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionCreate(FactionCreateEvent paramFactionCreateEvent) {
/* 120 */     TeamHandlerListener.onTeamCreate(paramFactionCreateEvent.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionDisband(FactionDisbandEvent paramFactionDisbandEvent) {
/* 125 */     TeamHandlerListener.onTeamDelete(paramFactionDisbandEvent.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionRename(FactionRenameEvent paramFactionRenameEvent) {
/* 130 */     TeamHandlerListener.onTeamRename(paramFactionRenameEvent.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFPlayerLeave(FPlayerLeaveEvent paramFPlayerLeaveEvent) {
/* 135 */     TeamHandlerListener.onTeamChange(paramFPlayerLeaveEvent.getfPlayer().getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFPlayerJoin(FPlayerJoinEvent paramFPlayerJoinEvent) {
/* 140 */     TeamHandlerListener.onTeamChange(paramFPlayerJoinEvent.getfPlayer().getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\FactionsUUIDTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */