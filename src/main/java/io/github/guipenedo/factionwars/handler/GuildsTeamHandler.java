/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import me.glaremasters.guilds.Guilds;
/*     */ import me.glaremasters.guilds.api.events.GuildCreateEvent;
/*     */ import me.glaremasters.guilds.api.events.GuildJoinEvent;
/*     */ import me.glaremasters.guilds.api.events.GuildLeaveEvent;
/*     */ import me.glaremasters.guilds.api.events.GuildRemoveEvent;
/*     */ import me.glaremasters.guilds.api.events.GuildRenameEvent;
/*     */ import me.glaremasters.guilds.guild.Guild;
/*     */ import me.glaremasters.guilds.guild.GuildMember;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ 
/*     */ public class GuildsTeamHandler extends TeamHandler implements Listener {
/*     */   public GuildsTeamHandler(String paramString) {
/*  21 */     super(paramString);
/*     */   }
/*     */   
/*     */   private Guild getGuild(String paramString) {
/*  25 */     return Guilds.getApi().getGuild(UUID.fromString(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  30 */     Guild guild = getGuild(paramString);
/*  31 */     return (guild != null) ? guild.getName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  36 */     Guild guild = Guilds.getApi().getGuild(paramString);
/*  37 */     return (guild != null) ? guild.getId().toString() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  42 */     Guild guild = getGuild(paramString);
/*  43 */     if (guild == null)
/*  44 */       return Collections.emptyList(); 
/*  45 */     return guild.getOnlineAsPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  50 */     Guild guild1 = getGuild(paramString1), guild2 = getGuild(paramString2);
/*  51 */     if (guild1 == null || guild2 == null) return null; 
/*  52 */     if (guild1.getAllies().contains(guild2.getId()))
/*  53 */       return TeamHandler.Relation.ALLY; 
/*  54 */     return TeamHandler.Relation.NEUTRAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBalance(String paramString) {
/*  64 */     Guild guild = getGuild(paramString);
/*  65 */     return (guild == null) ? 0.0D : guild.getBalance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeBalance(String paramString, double paramDouble) {
/*  70 */     Guild guild = getGuild(paramString);
/*  71 */     if (guild != null) {
/*  72 */       guild.setBalance(guild.getBalance() + paramDouble);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  77 */     Guild guild = getGuild(paramString1);
/*  78 */     if (guild != null) {
/*  79 */       guild.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  84 */     ArrayList<String> arrayList = new ArrayList();
/*  85 */     for (Guild guild : Guilds.getApi().getGuildHandler().getGuilds())
/*  86 */       arrayList.add(guild.getId().toString()); 
/*  87 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  92 */     Guild guild = getGuild(paramString);
/*  93 */     if (guild == null) return Collections.emptyList(); 
/*  94 */     ArrayList<Player> arrayList = new ArrayList();
/*  95 */     if (paramRole == TeamHandler.Role.ADMIN) {
/*  96 */       if (FactionWars.get().getServer().getPlayer(guild.getGuildMaster().getUuid()) != null)
/*  97 */         arrayList.add(FactionWars.get().getServer().getPlayer(guild.getGuildMaster().getUuid())); 
/*  98 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/*  99 */       for (GuildMember guildMember : guild.getOnlineMembers())
/* 100 */       { if (guildMember.getRole().isKick())
/* 101 */           arrayList.add(FactionWars.get().getServer().getPlayer(guildMember.getUuid()));  } 
/* 102 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/* 103 */       return getOnlinePlayers(paramString);
/* 104 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/* 109 */     if (paramObject instanceof String)
/* 110 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 111 */     if (paramObject instanceof OfflinePlayer) {
/* 112 */       Guild guild = Guilds.getApi().getGuild((OfflinePlayer)paramObject);
/* 113 */       if (guild != null)
/* 114 */         return guild.getId().toString(); 
/*     */     } 
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onGuildCreate(GuildCreateEvent paramGuildCreateEvent) {
/* 122 */     TeamHandlerListener.onTeamCreate(paramGuildCreateEvent.getGuild().getId().toString());
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onGuildRemove(GuildRemoveEvent paramGuildRemoveEvent) {
/* 127 */     TeamHandlerListener.onTeamDelete(paramGuildRemoveEvent.getGuild().getId().toString());
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onGuildRename(GuildRenameEvent paramGuildRenameEvent) {
/* 132 */     TeamHandlerListener.onTeamRename(paramGuildRenameEvent.getGuild().getId().toString());
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onGuildLeave(GuildLeaveEvent paramGuildLeaveEvent) {
/* 137 */     TeamHandlerListener.onTeamChange(paramGuildLeaveEvent.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled = true)
/*     */   public void onGuildJoin(GuildJoinEvent paramGuildJoinEvent) {
/* 142 */     TeamHandlerListener.onTeamChange(paramGuildJoinEvent.getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\GuildsTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */