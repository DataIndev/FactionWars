/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import com.palmergames.bukkit.towny.TownyAPI;
/*     */ import com.palmergames.bukkit.towny.TownyMessaging;
/*     */ import com.palmergames.bukkit.towny.event.NationRemoveTownEvent;
/*     */ import com.palmergames.bukkit.towny.event.NewNationEvent;
/*     */ import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
/*     */ import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
/*     */ import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
/*     */ import com.palmergames.bukkit.towny.object.Nation;
/*     */ import com.palmergames.bukkit.towny.object.Resident;
/*     */ import com.palmergames.bukkit.towny.object.Town;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ 
/*     */ public class TownyTeamHandler extends TeamHandler implements Listener {
/*     */   public TownyTeamHandler(String paramString) {
/*  23 */     super(paramString);
/*     */   }
/*     */   
/*     */   private boolean useNations() {
/*  27 */     return FactionWars.getMainConfig().getConfig().getBoolean("integration.towny.use-nations");
/*     */   }
/*     */   
/*     */   private Town getTown(String paramString) {
/*     */     try {
/*  32 */       return TownyAPI.getInstance().getDataSource().getTown(UUID.fromString(paramString));
/*  33 */     } catch (Exception exception) {
/*     */       try {
/*  35 */         return TownyAPI.getInstance().getDataSource().getTown(paramString);
/*  36 */       } catch (Exception exception1) {
/*  37 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Nation getNation(String paramString) {
/*     */     try {
/*  44 */       return TownyAPI.getInstance().getDataSource().getNation(UUID.fromString(paramString));
/*  45 */     } catch (Exception exception) {
/*     */       try {
/*  47 */         return TownyAPI.getInstance().getDataSource().getNation(paramString);
/*  48 */       } catch (Exception exception1) {
/*  49 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  56 */     if (useNations()) {
/*  57 */       Nation nation = getNation(paramString);
/*  58 */       return (nation != null) ? nation.getName() : null;
/*     */     } 
/*  60 */     Town town = getTown(paramString);
/*  61 */     return (town != null) ? town.getName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  66 */     if (useNations()) {
/*  67 */       Nation nation = getNation(paramString);
/*  68 */       return (nation != null) ? nation.getUuid().toString() : null;
/*     */     } 
/*  70 */     Town town = getTown(paramString);
/*  71 */     return (town != null) ? town.getUuid().toString() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*     */     List<Town> list;
/*  77 */     if (useNations()) {
/*  78 */       Nation nation = getNation(paramString);
/*  79 */       if (nation == null)
/*  80 */         return Collections.emptyList(); 
/*  81 */       list = nation.getTowns();
/*     */     } else {
/*  83 */       Town town = getTown(paramString);
/*  84 */       if (town == null)
/*  85 */         return Collections.emptyList(); 
/*  86 */       list = Collections.singletonList(town);
/*     */     } 
/*  88 */     ArrayList<Player> arrayList = new ArrayList();
/*  89 */     for (Town town : list) {
/*  90 */       for (Resident resident : town.getResidents()) {
/*  91 */         Player player = FactionWars.get().getServer().getPlayer(resident.getName());
/*  92 */         if (player != null && player.isOnline())
/*  93 */           arrayList.add(player); 
/*     */       } 
/*  95 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/* 100 */     if (useNations()) {
/* 101 */       Nation nation1 = getNation(paramString1), nation2 = getNation(paramString2);
/* 102 */       if (nation1 == null || nation2 == null) return null; 
/* 103 */       if (nation1.hasAlly(nation2))
/* 104 */         return TeamHandler.Relation.ALLY; 
/* 105 */       return TeamHandler.Relation.NEUTRAL;
/*     */     } 
/* 107 */     Town town1 = getTown(paramString1), town2 = getTown(paramString2);
/* 108 */     if (town1 == null || town2 == null) return null; 
/* 109 */     if (town1.isAlliedWith(town2))
/* 110 */       return TeamHandler.Relation.ALLY; 
/* 111 */     return TeamHandler.Relation.NEUTRAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/* 116 */     if (useNations()) {
/* 117 */       Nation nation = getNation(paramString);
/* 118 */       return (nation != null) ? nation.getEconomyName() : null;
/*     */     } 
/* 120 */     Town town = getTown(paramString);
/* 121 */     return (town != null) ? town.getEconomyName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/* 126 */     if (useNations()) {
/* 127 */       Nation nation = getNation(paramString1);
/* 128 */       if (nation != null)
/* 129 */         TownyMessaging.sendNationMessage(nation, paramString2); 
/*     */     } else {
/* 131 */       Town town = getTown(paramString1);
/* 132 */       if (town != null) {
/* 133 */         TownyMessaging.sendTownMessage(town, paramString2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/* 139 */     ArrayList<String> arrayList = new ArrayList();
/* 140 */     if (useNations()) {
/* 141 */       for (Nation nation : TownyAPI.getInstance().getDataSource().getNations())
/* 142 */         arrayList.add(nation.getUuid().toString()); 
/*     */     } else {
/* 144 */       for (Town town : TownyAPI.getInstance().getDataSource().getTowns())
/* 145 */         arrayList.add(town.getUuid().toString()); 
/* 146 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/* 151 */     ArrayList<Player> arrayList = new ArrayList();
/* 152 */     if (useNations())
/* 153 */     { Nation nation = getNation(paramString);
/* 154 */       if (nation == null) return Collections.emptyList(); 
/* 155 */       if (paramRole == TeamHandler.Role.ADMIN) {
/* 156 */         if (FactionWars.get().getServer().getPlayer(nation.getCapital().getMayor().getName()) != null)
/* 157 */           arrayList.add(FactionWars.get().getServer().getPlayer(nation.getCapital().getMayor().getName())); 
/* 158 */         for (Resident resident : nation.getAssistants()) {
/* 159 */           Player player = FactionWars.get().getServer().getPlayer(resident.getName());
/* 160 */           if (player != null && player.hasPermission("factionwars.leader"))
/* 161 */             arrayList.add(player); 
/*     */         } 
/* 163 */       } else if (paramRole == TeamHandler.Role.MODERATOR) {
/* 164 */         for (Resident resident : nation.getAssistants()) {
/* 165 */           if (FactionWars.get().getServer().getPlayer(resident.getName()) != null)
/* 166 */             arrayList.add(FactionWars.get().getServer().getPlayer(resident.getName())); 
/*     */         } 
/*     */       }  }
/* 169 */     else { Town town = getTown(paramString);
/* 170 */       if (town == null) return Collections.emptyList(); 
/* 171 */       if (paramRole == TeamHandler.Role.ADMIN) {
/* 172 */         if (FactionWars.get().getServer().getPlayer(town.getMayor().getName()) != null)
/* 173 */           arrayList.add(FactionWars.get().getServer().getPlayer(town.getMayor().getName())); 
/* 174 */         for (Resident resident : town.getAssistants()) {
/* 175 */           Player player = FactionWars.get().getServer().getPlayer(resident.getName());
/* 176 */           if (player != null && player.hasPermission("factionwars.leader"))
/* 177 */             arrayList.add(player); 
/*     */         } 
/* 179 */       } else if (paramRole == TeamHandler.Role.MODERATOR) {
/* 180 */         for (Resident resident : town.getAssistants()) {
/* 181 */           if (FactionWars.get().getServer().getPlayer(resident.getName()) != null)
/* 182 */             arrayList.add(FactionWars.get().getServer().getPlayer(resident.getName())); 
/*     */         } 
/*     */       }  }
/* 185 */      if (paramRole == TeamHandler.Role.NORMAL)
/* 186 */       return getOnlinePlayers(paramString); 
/* 187 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/* 192 */     if (paramObject instanceof Player) {
/* 193 */       paramObject = ((Player)paramObject).getName();
/* 194 */     } else if (paramObject instanceof OfflinePlayer) {
/* 195 */       paramObject = ((OfflinePlayer)paramObject).getName();
/* 196 */     }  if (!(paramObject instanceof String))
/* 197 */       return null; 
/*     */     try {
/* 199 */       if (useNations()) {
/* 200 */         return TownyAPI.getInstance().getDataSource().getResident((String)paramObject).getTown().getNation().getUuid().toString();
/*     */       }
/* 202 */       return TownyAPI.getInstance().getDataSource().getResident((String)paramObject).getTown().getUuid().toString();
/* 203 */     } catch (Exception exception) {
/* 204 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onTownCreate(NewTownEvent paramNewTownEvent) {
/* 211 */     if (!useNations())
/* 212 */       TeamHandlerListener.onTeamCreate(paramNewTownEvent.getTown().getUuid().toString()); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onNationCreate(NewNationEvent paramNewNationEvent) {
/* 217 */     if (useNations())
/* 218 */       TeamHandlerListener.onTeamCreate(paramNewNationEvent.getNation().getUuid().toString()); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTownDelete(PreDeleteTownEvent paramPreDeleteTownEvent) {
/* 223 */     if (!useNations())
/* 224 */       TeamHandlerListener.onTeamDelete(paramPreDeleteTownEvent.getTown().getUuid().toString()); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTownRename(RenameTownEvent paramRenameTownEvent) {
/* 229 */     if (!useNations())
/* 230 */       TeamHandlerListener.onTeamRename(paramRenameTownEvent.getTown().getUuid().toString()); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onNationRename(RenameNationEvent paramRenameNationEvent) {
/* 235 */     if (useNations())
/* 236 */       TeamHandlerListener.onTeamRename(paramRenameNationEvent.getNation().getUuid().toString()); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTownRemoveResident(TownRemoveResidentEvent paramTownRemoveResidentEvent) {
/* 241 */     TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(paramTownRemoveResidentEvent.getResident().getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onTownAddResident(TownAddResidentEvent paramTownAddResidentEvent) {
/* 247 */     TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(paramTownAddResidentEvent.getResident().getName()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onNationAddTown(NationAddTownEvent paramNationAddTownEvent) {
/* 252 */     if (!useNations())
/* 253 */       return;  for (Resident resident : paramNationAddTownEvent.getTown().getResidents())
/* 254 */       TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(resident.getName())); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onNationRemoveTown(NationRemoveTownEvent paramNationRemoveTownEvent) {
/* 259 */     if (!useNations())
/* 260 */       return;  for (Resident resident : paramNationRemoveTownEvent.getTown().getResidents())
/* 261 */       TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(resident.getName())); 
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\TownyTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */