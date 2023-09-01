/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ 
/*     */ import com.massivecraft.factions.Rel;
/*     */ import com.massivecraft.factions.RelationParticipator;
/*     */ import com.massivecraft.factions.cmd.type.TypeFaction;
/*     */ import com.massivecraft.factions.entity.Faction;
/*     */ import com.massivecraft.factions.entity.FactionColl;
/*     */ import com.massivecraft.factions.entity.MPlayer;
/*     */ import com.massivecraft.factions.entity.Rank;
/*     */ import com.massivecraft.factions.event.EventFactionsCreate;
/*     */ import com.massivecraft.factions.event.EventFactionsDisband;
/*     */ import com.massivecraft.factions.event.EventFactionsMembershipChange;
/*     */ import com.massivecraft.factions.event.EventFactionsNameChange;
/*     */ import com.massivecraft.massivecore.MassiveException;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import io.github.guipenedo.factionwars.helpers.ReflectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ 
/*     */ public class Factions3TeamHandler extends TeamHandler implements Listener {
/*     */   public Factions3TeamHandler(String paramString) {
/*  28 */     super(paramString);
/*     */   }
/*     */   
/*     */   private Faction getFaction(String paramString) {
/*  32 */     return (Faction)FactionColl.get().get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  37 */     Faction faction = getFaction(paramString);
/*  38 */     return (faction != null) ? faction.getName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*     */     try {
/*  44 */       return TypeFaction.get().read(paramString, (CommandSender)FactionWars.get().getServer().getConsoleSender()).getId();
/*  45 */     } catch (MassiveException massiveException) {
/*  46 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  52 */     Faction faction = getFaction(paramString);
/*  53 */     if (faction == null)
/*  54 */       return Collections.emptyList(); 
/*  55 */     return faction.getOnlinePlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  60 */     Faction faction1 = getFaction(paramString1), faction2 = getFaction(paramString2);
/*  61 */     if (faction1 == null || faction2 == null)
/*  62 */       return TeamHandler.Relation.NEUTRAL; 
/*  63 */     Rel rel = faction1.getRelationTo((RelationParticipator)faction2);
/*  64 */     return (rel == Rel.ALLY) ? TeamHandler.Relation.ALLY : ((rel == Rel.ENEMY) ? TeamHandler.Relation.ENEMY : TeamHandler.Relation.NEUTRAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  69 */     return "faction-" + paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  74 */     Faction faction = getFaction(paramString1);
/*  75 */     if (faction != null) {
/*  76 */       faction.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  81 */     ArrayList<String> arrayList = new ArrayList();
/*  82 */     for (Faction faction : FactionColl.get().getAll()) {
/*  83 */       if (faction.getId().contains("-"))
/*  84 */         arrayList.add(faction.getId()); 
/*  85 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  90 */     Faction faction = getFaction(paramString);
/*  91 */     if (faction == null) return Collections.emptyList(); 
/*  92 */     ArrayList<Player> arrayList = new ArrayList();
/*     */     
/*     */     try {
/*  95 */       Rank rank = (Rank)ReflectionUtil.invokeMethod("getLeaderRank", faction);
/*  96 */       if (paramRole == TeamHandler.Role.ADMIN)
/*  97 */       { for (MPlayer mPlayer : ReflectionUtil.getMethod(faction.getClass(), "getMPlayersWhereRank", new Class[] { Rank.class }).invoke(faction, new Object[] { rank }))
/*  98 */           arrayList.add(mPlayer.getPlayer());  }
/*  99 */       else if (paramRole == TeamHandler.Role.MODERATOR)
/* 100 */       { Rank rank1 = rank.getRankBelow();
/* 101 */         for (MPlayer mPlayer : ReflectionUtil.getMethod(faction.getClass(), "getMPlayersWhereRank", new Class[] { Rank.class }).invoke(faction, new Object[] { rank1 }))
/* 102 */           arrayList.add(mPlayer.getPlayer());  }
/* 103 */       else if (paramRole == TeamHandler.Role.NORMAL)
/* 104 */       { return getOnlinePlayers(paramString); } 
/* 105 */     } catch (Exception exception) {
/* 106 */       exception.printStackTrace();
/*     */     } 
/* 108 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/*     */     MPlayer mPlayer;
/* 114 */     if (paramObject instanceof String)
/* 115 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 116 */     if (paramObject == null)
/* 117 */       return null; 
/* 118 */     if (paramObject instanceof org.bukkit.OfflinePlayer)
/* 119 */     { mPlayer = MPlayer.get(paramObject); }
/* 120 */     else { return null; }
/* 121 */      return (mPlayer != null && mPlayer.getFaction() != null && !mPlayer.getFaction().getId().equals("none")) ? mPlayer.getFaction().getId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionCreate(EventFactionsCreate paramEventFactionsCreate) {
/* 127 */     TeamHandlerListener.onTeamCreate(getTeamIdByName(paramEventFactionsCreate.getFactionName()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionDisband(EventFactionsDisband paramEventFactionsDisband) {
/* 132 */     TeamHandlerListener.onTeamDelete(paramEventFactionsDisband.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionRename(EventFactionsNameChange paramEventFactionsNameChange) {
/* 137 */     TeamHandlerListener.onTeamRename(paramEventFactionsNameChange.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMembershipChange(EventFactionsMembershipChange paramEventFactionsMembershipChange) {
/* 142 */     TeamHandlerListener.onTeamChange(paramEventFactionsMembershipChange.getMPlayer().getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\Factions3TeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */