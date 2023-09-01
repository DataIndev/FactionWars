/*    */ package io.github.guipenedo.factionwars.handler;
/*    */ 
/*    */ import com.gmail.nossr50.api.ChatAPI;
/*    */ import com.gmail.nossr50.api.PartyAPI;
/*    */ import com.gmail.nossr50.datatypes.party.Party;
/*    */ import com.gmail.nossr50.events.party.McMMOPartyChangeEvent;
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class McMMOTeamHandler extends TeamHandler implements Listener {
/*    */   public McMMOTeamHandler(String paramString) {
/* 21 */     super(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTeamName(String paramString) {
/* 26 */     return paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTeamIdByName(String paramString) {
/* 31 */     return paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Player> getOnlinePlayers(String paramString) {
/* 36 */     return PartyAPI.getOnlineMembers(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/* 41 */     if (PartyAPI.getAllyName(paramString2) != null && paramString1.equals(PartyAPI.getAllyName(paramString2)))
/* 42 */       return TeamHandler.Relation.ALLY; 
/* 43 */     return TeamHandler.Relation.NEUTRAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBankName(String paramString) {
/* 48 */     return PartyAPI.getPartyLeader(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String paramString1, String paramString2) {
/* 53 */     ChatAPI.sendPartyChat((Plugin)FactionWars.get(), "Wars", paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getAllTeams() {
/* 58 */     ArrayList<String> arrayList = new ArrayList();
/* 59 */     for (Party party : PartyAPI.getParties())
/* 60 */       arrayList.add(party.getName()); 
/* 61 */     return arrayList;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/* 66 */     if (paramRole == TeamHandler.Role.ADMIN)
/* 67 */       return Collections.singletonList(FactionWars.get().getServer().getPlayer(PartyAPI.getPartyLeader(paramString))); 
/* 68 */     if (paramRole == TeamHandler.Role.NORMAL)
/* 69 */       return getOnlinePlayers(paramString); 
/* 70 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPlayerTeam(Object paramObject) {
/* 75 */     if (paramObject instanceof String)
/* 76 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 77 */     if (paramObject instanceof Player) {
/*    */       try {
/* 79 */         return PartyAPI.getPartyName((Player)paramObject);
/* 80 */       } catch (Exception exception) {
/* 81 */         return null;
/*    */       } 
/*    */     }
/* 84 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(ignoreCancelled = true)
/*    */   public void onMcMMOPartyChangeEvent(final McMMOPartyChangeEvent e) {
/* 90 */     if (FactionData.getFaction(e.getNewParty()) == null)
/* 91 */       TeamHandlerListener.onTeamCreate(e.getNewParty()); 
/* 92 */     TeamHandlerListener.onTeamChange(e.getPlayer());
/* 93 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 96 */           if (e.getOldParty() != null && PartyAPI.getPartyLeader(e.getOldParty()) == null)
/* 97 */             TeamHandlerListener.onTeamDelete(e.getOldParty()); 
/*    */         }
/* 99 */       }).runTaskLater((Plugin)FactionWars.get(), 1000L);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\McMMOTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */