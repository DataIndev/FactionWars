/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ 
/*     */ import com.massivecraft.factions.Rel;
/*     */ import com.massivecraft.factions.RelationParticipator;
/*     */ import com.massivecraft.factions.cmd.type.TypeFaction;
/*     */ import com.massivecraft.factions.entity.Faction;
/*     */ import com.massivecraft.factions.entity.FactionColl;
/*     */ import com.massivecraft.factions.entity.MPlayer;
/*     */ import com.massivecraft.factions.event.EventFactionsCreate;
/*     */ import com.massivecraft.factions.event.EventFactionsDisband;
/*     */ import com.massivecraft.factions.event.EventFactionsMembershipChange;
/*     */ import com.massivecraft.factions.event.EventFactionsNameChange;
/*     */ import com.massivecraft.massivecore.MassiveException;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ 
/*     */ public class FactionsTeamHandler extends TeamHandler implements Listener {
/*     */   public FactionsTeamHandler(String paramString) {
/*  26 */     super(paramString);
/*     */   }
/*     */   
/*     */   private Faction getFaction(String paramString) {
/*  30 */     return (Faction)FactionColl.get().get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  35 */     Faction faction = getFaction(paramString);
/*  36 */     return (faction != null) ? faction.getName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*     */     try {
/*  42 */       return TypeFaction.get().read(paramString, (CommandSender)FactionWars.get().getServer().getConsoleSender()).getId();
/*  43 */     } catch (MassiveException massiveException) {
/*  44 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  50 */     Faction faction = getFaction(paramString);
/*  51 */     if (faction == null)
/*  52 */       return Collections.emptyList(); 
/*  53 */     return faction.getOnlinePlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  58 */     Faction faction1 = getFaction(paramString1), faction2 = getFaction(paramString2);
/*  59 */     if (faction1 == null || faction2 == null)
/*  60 */       return TeamHandler.Relation.NEUTRAL; 
/*  61 */     Rel rel = faction1.getRelationTo((RelationParticipator)faction2);
/*  62 */     return (rel == Rel.ALLY) ? TeamHandler.Relation.ALLY : ((rel == Rel.ENEMY) ? TeamHandler.Relation.ENEMY : TeamHandler.Relation.NEUTRAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  67 */     return "faction-" + paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  72 */     Faction faction = getFaction(paramString1);
/*  73 */     if (faction != null) {
/*  74 */       faction.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  79 */     ArrayList<String> arrayList = new ArrayList();
/*  80 */     for (Faction faction : FactionColl.get().getAll()) {
/*  81 */       if (faction.getId().contains("-"))
/*  82 */         arrayList.add(faction.getId()); 
/*  83 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  88 */     Faction faction = getFaction(paramString);
/*  89 */     if (faction == null) return Collections.emptyList(); 
/*  90 */     ArrayList<Player> arrayList = new ArrayList();
/*  91 */     if (paramRole == TeamHandler.Role.ADMIN) {
/*  92 */       for (MPlayer mPlayer : faction.getMPlayersWhereRole(Rel.LEADER)) {
/*  93 */         arrayList.add(mPlayer.getPlayer());
/*     */       }
/*  95 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/*  96 */       for (MPlayer mPlayer : faction.getMPlayersWhereRole(Rel.OFFICER))
/*  97 */         arrayList.add(mPlayer.getPlayer()); 
/*  98 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/*  99 */       return getOnlinePlayers(paramString);
/* 100 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/*     */     MPlayer mPlayer;
/* 106 */     if (paramObject instanceof String)
/* 107 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 108 */     if (paramObject == null)
/* 109 */       return null; 
/* 110 */     if (paramObject instanceof org.bukkit.OfflinePlayer)
/* 111 */     { mPlayer = MPlayer.get(paramObject); }
/* 112 */     else { return null; }
/* 113 */      return (mPlayer != null && mPlayer.getFaction() != null && !mPlayer.getFaction().getId().equals("none")) ? mPlayer.getFaction().getId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionCreate(EventFactionsCreate paramEventFactionsCreate) {
/* 119 */     TeamHandlerListener.onTeamCreate(getTeamIdByName(paramEventFactionsCreate.getFactionName()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionDisband(EventFactionsDisband paramEventFactionsDisband) {
/* 124 */     TeamHandlerListener.onTeamDelete(paramEventFactionsDisband.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionRename(EventFactionsNameChange paramEventFactionsNameChange) {
/* 129 */     TeamHandlerListener.onTeamRename(paramEventFactionsNameChange.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMembershipChange(EventFactionsMembershipChange paramEventFactionsMembershipChange) {
/* 134 */     TeamHandlerListener.onTeamChange(paramEventFactionsMembershipChange.getMPlayer().getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\FactionsTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */