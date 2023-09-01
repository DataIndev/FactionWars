/*     */ package io.github.guipenedo.factionwars.handler;
/*     */ import com.songoda.kingdoms.constants.Rank;
/*     */ import com.songoda.kingdoms.constants.kingdom.Kingdom;
/*     */ import com.songoda.kingdoms.constants.player.KingdomPlayer;
/*     */ import com.songoda.kingdoms.constants.player.OfflineKingdomPlayer;
/*     */ import com.songoda.kingdoms.events.KingdomCreateEvent;
/*     */ import com.songoda.kingdoms.events.KingdomDeleteEvent;
/*     */ import com.songoda.kingdoms.events.KingdomMemberJoinEvent;
/*     */ import com.songoda.kingdoms.events.KingdomMemberLeaveEvent;
/*     */ import com.songoda.kingdoms.events.KingdomNexusGUIOpenEvent;
/*     */ import com.songoda.kingdoms.manager.game.GameManagement;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.api.TeamHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class KingdomsTeamHandler extends TeamHandler implements Listener {
/*  27 */   private ItemStack nexusButton = new ItemStack(Material.DIAMOND_SWORD);
/*     */   
/*     */   public KingdomsTeamHandler(String paramString) {
/*  30 */     super(paramString);
/*  31 */     ItemMeta itemMeta = this.nexusButton.getItemMeta();
/*  32 */     if (itemMeta != null)
/*  33 */       itemMeta.setDisplayName("§bKingdom Wars"); 
/*  34 */     this.nexusButton.setItemMeta(itemMeta);
/*     */   }
/*     */   
/*     */   private Kingdom getKingdom(String paramString) {
/*  38 */     return GameManagement.getKingdomManager().getOrLoadKingdom(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName(String paramString) {
/*  43 */     Kingdom kingdom = getKingdom(paramString);
/*  44 */     return (kingdom != null) ? kingdom.getKingdomName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamIdByName(String paramString) {
/*  49 */     Kingdom kingdom = getKingdom(paramString);
/*  50 */     return (kingdom == null) ? null : kingdom.getKingdomUuid().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getOnlinePlayers(String paramString) {
/*  55 */     Kingdom kingdom = getKingdom(paramString);
/*  56 */     if (kingdom == null)
/*  57 */       return Collections.emptyList(); 
/*  58 */     ArrayList<Player> arrayList = new ArrayList();
/*  59 */     for (KingdomPlayer kingdomPlayer : kingdom.getOnlineMembers()) {
/*  60 */       if (kingdomPlayer.getPlayer() != null && kingdomPlayer.getPlayer().isOnline())
/*  61 */         arrayList.add(kingdomPlayer.getPlayer()); 
/*  62 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public TeamHandler.Relation getRelationBetween(String paramString1, String paramString2) {
/*  67 */     Kingdom kingdom1 = getKingdom(paramString1), kingdom2 = getKingdom(paramString2);
/*  68 */     if (kingdom1 == null || kingdom2 == null) return null; 
/*  69 */     if (kingdom1.isAllianceWith(kingdom2))
/*  70 */       return TeamHandler.Relation.ALLY; 
/*  71 */     return TeamHandler.Relation.NEUTRAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBankName(String paramString) {
/*  76 */     Kingdom kingdom = getKingdom(paramString);
/*  77 */     if (kingdom == null) return null; 
/*  78 */     return FactionWars.get().getServer().getOfflinePlayer(kingdom.getKing()).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String paramString1, String paramString2) {
/*  83 */     Kingdom kingdom = getKingdom(paramString1);
/*  84 */     if (kingdom != null) {
/*  85 */       kingdom.sendAnnouncement(null, paramString2, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllTeams() {
/*  90 */     ArrayList<String> arrayList = new ArrayList();
/*  91 */     for (UUID uUID : GameManagement.getKingdomManager().getKingdomList().keySet())
/*  92 */       arrayList.add(uUID.toString()); 
/*  93 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Player> getMembersWithRole(String paramString, TeamHandler.Role paramRole) {
/*  98 */     Kingdom kingdom = getKingdom(paramString);
/*  99 */     if (kingdom == null) return Collections.emptyList(); 
/* 100 */     ArrayList<Player> arrayList = new ArrayList();
/* 101 */     if (paramRole == TeamHandler.Role.ADMIN && FactionWars.get().getServer().getPlayer(kingdom.getKing()) != null) {
/* 102 */       arrayList.add(FactionWars.get().getServer().getPlayer(kingdom.getKing()));
/* 103 */     } else if (paramRole == TeamHandler.Role.MODERATOR) {
/* 104 */       for (KingdomPlayer kingdomPlayer : kingdom.getOnlineMembers())
/* 105 */       { if (kingdomPlayer.getRank() == Rank.MODS)
/* 106 */           arrayList.add(kingdomPlayer.getPlayer());  } 
/* 107 */     } else if (paramRole == TeamHandler.Role.NORMAL) {
/* 108 */       return getOnlinePlayers(paramString);
/* 109 */     }  return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerTeam(Object paramObject) {
/* 114 */     if (paramObject instanceof String)
/* 115 */       paramObject = FactionWars.get().getServer().getPlayer((String)paramObject); 
/* 116 */     if (paramObject == null)
/* 117 */       return null; 
/* 118 */     if (paramObject instanceof Player) {
/* 119 */       KingdomPlayer kingdomPlayer = GameManagement.getPlayerManager().getSession((Player)paramObject);
/* 120 */       return (kingdomPlayer != null && kingdomPlayer.getKingdomUuid() != null) ? kingdomPlayer.getKingdomUuid().toString() : null;
/*     */     } 
/* 122 */     if (paramObject instanceof OfflinePlayer) {
/* 123 */       OfflineKingdomPlayer offlineKingdomPlayer = GameManagement.getPlayerManager().getOfflineKingdomPlayer((OfflinePlayer)paramObject);
/* 124 */       return (offlineKingdomPlayer != null && offlineKingdomPlayer.getKingdomUuid() != null) ? offlineKingdomPlayer.getKingdomUuid().toString() : null;
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onKingdomMemberJoin(KingdomMemberJoinEvent paramKingdomMemberJoinEvent) {
/* 132 */     TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(paramKingdomMemberJoinEvent.getKp().getUuid()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKingdomMemberLeave(KingdomMemberLeaveEvent paramKingdomMemberLeaveEvent) {
/* 137 */     TeamHandlerListener.onTeamChange(FactionWars.get().getServer().getPlayer(paramKingdomMemberLeaveEvent.getKp().getUuid()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKingdomCreate(KingdomCreateEvent paramKingdomCreateEvent) {
/* 142 */     TeamHandlerListener.onTeamCreate(paramKingdomCreateEvent.getKingdom().getKingdomUuid().toString());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKingdomDelete(KingdomDeleteEvent paramKingdomDeleteEvent) {
/* 147 */     TeamHandlerListener.onTeamDelete(paramKingdomDeleteEvent.getKingdom().getKingdomUuid().toString());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onItemClick(InventoryClickEvent paramInventoryClickEvent) {
/* 152 */     if (paramInventoryClickEvent.getClickedInventory() != null && paramInventoryClickEvent.getCurrentItem() != null && paramInventoryClickEvent.getCurrentItem().equals(this.nexusButton))
/* 153 */       (new MainMenu((Player)paramInventoryClickEvent.getWhoClicked())).open(); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKingdomNexusGUIOpen(KingdomNexusGUIOpenEvent paramKingdomNexusGUIOpenEvent) {
/* 158 */     paramKingdomNexusGUIOpenEvent.getGui().getInventory().addItem(new ItemStack[] { this.nexusButton });
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\handler\KingdomsTeamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */