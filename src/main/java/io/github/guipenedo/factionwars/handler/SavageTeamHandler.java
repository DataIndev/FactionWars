/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.Faction;
/*     */ import com.massivecraft.factions.Factions;
/*     */ import com.massivecraft.factions.event.FPlayerJoinEvent;
/*     */ import com.massivecraft.factions.event.FPlayerLeaveEvent;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import io.github.guipenedo.factionwars.helpers.ReflectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ 
/*     */ public class SavageTeamHandler extends TeamHandler implements Listener {
/*     */   public SavageTeamHandler(String paramString) {
/*  17 */     super(paramString);
/*     */   }
/*     */ 
/*     */   
/*  21 */   private static Map<String, Class<?>> loadedClasses = new HashMap<>();
/*     */   public static Class<?> getClass(String paramString) {
/*     */     Class<?> clazz;
/*  24 */     if (loadedClasses.containsKey(paramString)) {
/*  25 */       return loadedClasses.get(paramString);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  30 */       clazz = Class.forName(paramString);
/*  31 */     } catch (Throwable throwable) {
/*  32 */       throwable.printStackTrace();
/*  33 */       return loadedClasses.put(paramString, null);
/*     */     } 
/*     */     
/*  36 */     loadedClasses.put(paramString, clazz);
/*  37 */     return clazz;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> T getEnumFromString(Class<T> paramClass, String paramString) {
/*  41 */     if (paramClass != null && paramString != null) {
/*     */       try {
/*  43 */         return Enum.valueOf(paramClass, paramString.trim().toUpperCase());
/*  44 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */     
/*  47 */     return null;
/*     */   }
/*     */   
/*     */   private Faction getFaction(String paramString) {
/*  51 */     return Factions.getInstance().getFactionById(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  56 */     Faction faction = getFaction(paramString);
/*  57 */     return (faction != null) ? faction.getTag() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  62 */     Faction faction = Factions.getInstance().getByTag(paramString);
/*  63 */     return (faction != null) ? faction.getId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  68 */     Faction faction = getFaction(paramString);
/*  69 */     if (faction == null)
/*  70 */       return Collections.emptyList(); 
/*  71 */     ArrayList<Player> arrayList = new ArrayList();
/*  72 */     faction.getFPlayersWhereOnline(true).forEach(paramFPlayer -> paramList.add(paramFPlayer.getPlayer()));
/*  73 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  79 */     Faction faction1 = getFaction(paramString1), faction2 = getFaction(paramString2);
/*  80 */     if (faction1 == null || faction2 == null)
/*  81 */       return TeamHandler.Relation.NEUTRAL; 
/*     */     try {
/*  83 */       Enum enum_ = (Enum)ReflectionUtil.getMethod(faction1.getClass(), "getRelationTo", new Class[] { getClass("com.massivecraft.factions.iface.RelationParticipator") }).invoke(faction1, new Object[] { faction2 });
/*  84 */       Class<?> clazz = getClass("com.massivecraft.factions.struct.Relation");
/*  85 */       return (enum_ == Enum.valueOf(clazz, "ALLY")) ? TeamHandler.Relation.ALLY : ((enum_ == Enum.valueOf(clazz, "ENEMY")) ? TeamHandler.Relation.ENEMY : TeamHandler.Relation.NEUTRAL);
/*  86 */     } catch (Exception exception) {
/*  87 */       return TeamHandler.Relation.NEUTRAL;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  93 */     Faction faction = getFaction(paramString);
/*  94 */     return (faction != null) ? faction.getAccountId() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  99 */     Faction faction = getFaction(paramString1);
/* 100 */     if (faction != null) {
/* 101 */       faction.sendMessage(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/* 106 */     ArrayList<String> arrayList = new ArrayList();
/* 107 */     for (Faction faction : Factions.getInstance().getAllFactions()) {
/* 108 */       if (Integer.parseInt(faction.getId()) > 0)
/* 109 */         arrayList.add(faction.getId()); 
/* 110 */     }  return arrayList;
/*     */   }
/*     */   
/*     */   private ArrayList<FPlayer> getFPlayersWhereRole(Faction paramFaction, Object paramObject) {
/* 114 */     ArrayList<FPlayer> arrayList = new ArrayList();
/* 115 */     if (!paramFaction.isNormal()) {
/* 116 */       return arrayList;
/*     */     }
/*     */     
/* 119 */     for (FPlayer fPlayer : paramFaction.getFPlayers()) {
/* 120 */       if (ReflectionUtil.invokeMethod("getRole", fPlayer) == paramObject) {
/* 121 */         arrayList.add(fPlayer);
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/* 131 */     Faction faction = getFaction(paramString);
/* 132 */     if (faction == null) return Collections.emptyList(); 
/* 133 */     ArrayList<Player> arrayList = new ArrayList();
/* 134 */     Class<?> clazz = getClass("com.massivecraft.factions.struct.Role");
/* 135 */     if (paramRole == TeamHandler.Role.ADMIN) {
/* 136 */       for (FPlayer fPlayer : getFPlayersWhereRole(faction, getEnumFromString(clazz, "LEADER")))
/* 137 */         arrayList.add(fPlayer.getPlayer()); 
/* 138 */       for (FPlayer fPlayer : getFPlayersWhereRole(faction, getEnumFromString(clazz, "COLEADER"))) {
/* 139 */         arrayList.add(fPlayer.getPlayer());
/*     */       }
/* 141 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/* 142 */       for (FPlayer fPlayer : getFPlayersWhereRole(faction, getEnumFromString(clazz, "MODERATOR")))
/* 143 */         arrayList.add(fPlayer.getPlayer()); 
/* 144 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/* 145 */       return getOnlinePlayers(paramString);
/* 146 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/*     */     FPlayer fPlayer;
/* 152 */     if (paramObject instanceof String)
/* 153 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 154 */     if (paramObject == null)
/* 155 */       return null; 
/* 156 */     if (paramObject instanceof Player)
/* 157 */     { fPlayer = FPlayers.getInstance().getByPlayer((Player)paramObject); }
/* 158 */     else if (paramObject instanceof OfflinePlayer)
/* 159 */     { fPlayer = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)paramObject); }
/* 160 */     else { return null; }
/* 161 */      return (fPlayer != null && fPlayer.getFaction() != null && Integer.parseInt(fPlayer.getFactionId()) > 0) ? fPlayer.getFactionId() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionCreate(FactionCreateEvent paramFactionCreateEvent) {
/* 168 */     TeamHandlerListener.onTeamCreate(getTeamIdByName(paramFactionCreateEvent.getFactionTag()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionDisband(FactionDisbandEvent paramFactionDisbandEvent) {
/* 173 */     TeamHandlerListener.onTeamDelete(paramFactionDisbandEvent.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFactionRename(FactionRenameEvent paramFactionRenameEvent) {
/* 178 */     TeamHandlerListener.onTeamRename(paramFactionRenameEvent.getFaction().getId());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFPlayerLeave(FPlayerLeaveEvent paramFPlayerLeaveEvent) {
/* 183 */     TeamHandlerListener.onTeamChange(paramFPlayerLeaveEvent.getfPlayer().getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFPlayerJoin(FPlayerJoinEvent paramFPlayerJoinEvent) {
/* 188 */     TeamHandlerListener.onTeamChange(paramFPlayerJoinEvent.getfPlayer().getPlayer());
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\SavageTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */