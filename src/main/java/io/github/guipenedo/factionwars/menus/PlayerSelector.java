/*     */ package io.github.guipenedo.factionwars.menus;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.managers.InvitesManager;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.menus.generic.AnvilGUI;
/*     */ import io.github.guipenedo.factionwars.menus.generic.ChestGUI;
/*     */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*     */ import io.github.guipenedo.factionwars.menus.generic.NumericGUI;
/*     */ import io.github.guipenedo.factionwars.menus.generic.PaginatedMenu;
/*     */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class PlayerSelector extends PaginatedMenu {
/*     */   public PlayerSelector(Player paramPlayer) {
/*  29 */     super(paramPlayer, null);
/*  30 */     if (FactionWars.getMainConfig().getConfig().getInt("selection.maximum-per-faction") == 0)
/*     */       return; 
/*  32 */     this.playerSelection = PlayerSelection.getBySelector(paramPlayer.getUniqueId());
/*  33 */     this.gui.setShouldReopen(true);
/*  34 */     this.gui.setMenuPlayerQuitEventHandler(() -> PlayerSelection.getPlayerSelections().remove(this.playerSelection));
/*     */   }
/*     */   private PlayerSelection playerSelection;
/*     */   private void updateHeads() {
/*  38 */     this.items.clear();
/*  39 */     ItemBuilder itemBuilder = new ItemBuilder();
/*  40 */     for (Iterator<UUID> iterator = this.playerSelection.getOnlinePlayers().iterator(); iterator.hasNext(); ) { UUID uUID = iterator.next();
/*  41 */       String str = Bukkit.getOfflinePlayer(uUID).getName();
/*  42 */       this.items.add(new MenuItem(itemBuilder.buildHead(str), paramMenuItemClickEvent -> {
/*     */               FactionWars.debug("selecting " + paramString);
/*     */               if (!selectPlayer(paramString)) {
/*     */                 paramMenuItemClickEvent.setWillDestroy(false);
/*     */                 paramMenuItemClickEvent.setWillClose(false);
/*     */               } 
/*     */             })); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean selectPlayer(String paramString) {
/*  54 */     Player player = FactionWars.get().getServer().getPlayer(paramString);
/*  55 */     if (player == null || player.getUniqueId().equals(this.player.getUniqueId()) || !this.playerSelection.getOnlinePlayers().contains(player.getUniqueId()) || this.playerSelection.getPlayersSelected().contains(player.getUniqueId())) {
/*  56 */       this.player.sendMessage(Util.getMessage("menus.selector.player-offline"));
/*  57 */       FactionWars.debug("player offline");
/*  58 */       return false;
/*     */     } 
/*  60 */     this.player.sendMessage(Util.getMessage("menus.selector.invited", Util.getVars(new Object[] { "target", player.getName() })));
/*  61 */     player.sendMessage(Util.getMessage("menus.selector.selected"));
/*  62 */     this.playerSelection.getPlayersSelected().add(player.getUniqueId());
/*  63 */     if (this.playerSelection.getLimit() > -1 && this.playerSelection.getPlayersSelected().size() >= this.playerSelection.getLimit()) {
/*  64 */       return finish();
/*     */     }
/*  66 */     return update();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean openMenu() {
/*  71 */     if (this.playerSelection == null) {
/*  72 */       return false;
/*     */     }
/*  74 */     if (this.playerSelection.getOnlinePlayers().size() == 0 || this.playerSelection.getLimit() == 0) {
/*  75 */       FactionWars.debug("no onlineplayers or limit reached");
/*  76 */       finish();
/*  77 */       return false;
/*     */     } 
/*     */     
/*  80 */     updateHeads();
/*     */     
/*  82 */     if (FactionWars.getMainConfig().getConfig().getBoolean("selection.anvilgui-enabled"))
/*  83 */       this.settingsItems.add(new MenuItem(new ItemBuilder(new ItemStack(LegacyUtil.BOOK_AND_QUILL), Util.getPlainMessage("menus.selector.book")), paramMenuItemClickEvent -> {
/*     */               paramMenuItemClickEvent.setWillClose(false);
/*     */               paramMenuItemClickEvent.setWillDestroy(false);
/*     */               BukkitRunnable bukkitRunnable = new BukkitRunnable()
/*     */                 {
/*     */                   public void run() {
/*  89 */                     PlayerSelector.this.gui.open();
/*     */                   }
/*     */                 };
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               bukkitRunnable.runTaskLater((Plugin)FactionWars.get(), 240L);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               AnvilGUI anvilGUI = new AnvilGUI(this.player, (), Util.getPlainMessage("menus.selector.nametag"));
/*     */ 
/*     */ 
/*     */               
/*     */               anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, (new ItemBuilder(new ItemStack(Material.NAME_TAG), Util.getPlainMessage("menus.selector.nametag"))).build());
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/*     */                 anvilGUI.open();
/* 111 */               } catch (Exception exception) {
/*     */                 exception.printStackTrace();
/*     */               } 
/*     */             })); 
/* 115 */     this.settingsItems.add(new MenuItem(new ItemBuilder(new ItemStack(LegacyUtil.WATCH), Util.getPlainMessage("menus.selector.refresh")), paramMenuItemClickEvent -> {
/*     */             if (!update()) {
/*     */               paramMenuItemClickEvent.setWillDestroy(false);
/*     */               paramMenuItemClickEvent.setWillClose(false);
/*     */             } 
/*     */           }));
/* 121 */     this.settingsItems.add(new MenuItem(new ItemBuilder(this.playerSelection.getGamemode().getItem(), Util.getPlainMessage("menus.selector.gamemode"), new String[] { Util.getPlainMessage("gamemode.selected", Util.getVars(new Object[] { "gamemode", this.playerSelection.getGamemode().getName() })), this.playerSelection.isLocked() ? Util.getPlainMessage("gamemode.locked") : Util.getPlainMessage("gamemode.unlocked") }), paramMenuItemClickEvent -> {
/*     */             paramMenuItemClickEvent.setWillClose(false);
/*     */             if (!this.playerSelection.isLocked()) {
/*     */               FactionWars.debug("Gamemode is not locked");
/*     */               GamemodeSelector gamemodeSelector = new GamemodeSelector(this.player);
/*     */               gamemodeSelector.open();
/*     */             } else {
/*     */               FactionWars.debug("Gamemode is locked");
/*     */               paramMenuItemClickEvent.setWillDestroy(false);
/*     */             } 
/*     */           }));
/* 132 */     if (FactionWars.getEcon() != null)
/* 133 */       this.settingsItems.add(new MenuItem(new ItemBuilder(new ItemStack(Material.GOLD_INGOT), NumericGUI.getTitle(this.playerSelection.getBet().doubleValue()), new String[] { this.playerSelection.isLocked() ? Util.getPlainMessage("gamemode.locked") : Util.getPlainMessage("gamemode.unlocked") }), paramMenuItemClickEvent -> {
/*     */               paramMenuItemClickEvent.setWillClose(false);
/*     */               if (!this.playerSelection.isLocked()) {
/*     */                 FactionWars.debug("Bet is not locked");
/*     */                 BetSelector betSelector = new BetSelector(this.player);
/*     */                 betSelector.open();
/*     */               } else {
/*     */                 FactionWars.debug("Gamemode is locked");
/*     */                 paramMenuItemClickEvent.setWillDestroy(false);
/*     */               } 
/*     */             })); 
/* 144 */     this.settingsItems.add(new MenuItem(new ItemBuilder(new ItemStack(LegacyUtil.CANCEL_ITEM), Util.getPlainMessage("menus.selector.cancel")), paramMenuItemClickEvent -> PlayerSelection.getPlayerSelections().remove(this.playerSelection)));
/* 145 */     PlayerSelection playerSelection = PlayerSelection.getByFactions(this.playerSelection.getTo(), this.playerSelection.getFrom());
/* 146 */     if (this.playerSelection.getAllPlayersSelected().size() >= Util.getMinPlayers() && (!FactionWars.getMainConfig().getConfig().getBoolean("selection.equal-number") || playerSelection == null || !playerSelection.isConfirmed() || this.playerSelection.getAllPlayersSelected().size() == playerSelection.getAllPlayersSelected().size()))
/* 147 */       this.settingsItems.add(new MenuItem(new ItemBuilder(LegacyUtil.LIME_WOOL_ITEMSTACK, Util.getPlainMessage("menus.selector.finish")), paramMenuItemClickEvent -> {
/*     */               if (!finish())
/*     */                 paramMenuItemClickEvent.setWillDestroy(false); 
/*     */             })); 
/* 151 */     this.title = getTitle();
/* 152 */     openGUI();
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean update() {
/* 158 */     FactionWars.debug("updating");
/* 159 */     if (this.playerSelection == null) return true; 
/* 160 */     this.playerSelection.updateOnlinePlayers();
/* 161 */     if (this.playerSelection.getOnlinePlayers().size() == 0) {
/* 162 */       return finish();
/*     */     }
/* 164 */     updateHeads();
/* 165 */     openGUI();
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean finish() {
/* 171 */     FactionWars.debug("finishing");
/* 172 */     if (this.playerSelection == null) return true; 
/* 173 */     FactionWars.get().getServer().getPlayer(this.playerSelection.getPlayerSelecting()).closeInventory();
/*     */     
/* 175 */     ArrayList<UUID> arrayList = new ArrayList();
/* 176 */     for (UUID uUID : this.playerSelection.getPlayersSelected()) {
/* 177 */       if (FactionWars.get().getServer().getPlayer(uUID) == null)
/* 178 */         arrayList.add(uUID); 
/*     */     } 
/* 180 */     if (!arrayList.isEmpty()) {
/* 181 */       this.playerSelection.getPlayersSelected().removeAll(arrayList);
/* 182 */       this.player.sendMessage(Util.getMessage("menus.selector.selected-offline", Util.getVars(new Object[] { "number", Integer.valueOf(arrayList.size()) })));
/* 183 */       update();
/* 184 */       return false;
/*     */     } 
/*     */     
/* 187 */     if (!Util.validBet(this.player, this.playerSelection.getBet().doubleValue())) {
/* 188 */       return false;
/*     */     }
/* 190 */     this.playerSelection.setConfirmed(true);
/* 191 */     this.playerSelection.getOnlinePlayers().clear();
/* 192 */     PlayerSelection playerSelection = PlayerSelection.getByFactions(this.playerSelection.getTo(), this.playerSelection.getFrom());
/* 193 */     if (playerSelection != null) {
/*     */       
/* 195 */       if (playerSelection.isConfirmed()) {
/*     */         
/* 197 */         WarMap warMap = MatchManager.get().findFreeArena(playerSelection.getGamemode());
/* 198 */         if (warMap == null) {
/* 199 */           this.player.sendMessage(Util.getMessage("error.no-arenas"));
/* 200 */           PlayerSelection.getPlayerSelections().remove(this.playerSelection);
/* 201 */           return true;
/*     */         } 
/* 203 */         HashMap<Object, Object> hashMap = new HashMap<>();
/* 204 */         hashMap.put("senderfaction", FactionWars.getHandler().getTeamName(this.playerSelection.getFrom()));
/* 205 */         hashMap.put("targetfaction", FactionWars.getHandler().getTeamName(this.playerSelection.getTo()));
/* 206 */         hashMap.put("gamemode", this.playerSelection.getGamemode().getName());
/* 207 */         hashMap.put("bet", this.playerSelection.getBet());
/* 208 */         FactionWars.getHandler().sendMessage(this.playerSelection.getFrom(), Util.getMessage("accept.self", hashMap));
/* 209 */         FactionWars.getHandler().sendMessage(this.playerSelection.getTo(), Util.getMessage("accept.target", hashMap));
/* 210 */         MatchManager.get().start(this.playerSelection, playerSelection, warMap);
/* 211 */         return true;
/*     */       } 
/* 213 */       PlayerSelection.getPlayerSelections().remove(this.playerSelection);
/* 214 */       return true;
/*     */     } 
/*     */     
/* 217 */     InvitesManager.get().confirmInvite(this.playerSelection.getFrom(), this.playerSelection.getTo());
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   private String getTitle() {
/* 222 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 223 */     hashMap.put("number", (this.playerSelection.getLimit() > -1) ? Integer.valueOf(this.playerSelection.getLimit() - this.playerSelection.getPlayersSelected().size()) : "0");
/* 224 */     String str = Util.getPlainMessage("menus.selector.title", hashMap);
/* 225 */     if (str.length() > 22)
/* 226 */       str = str.substring(0, 22); 
/* 227 */     return str;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\PlayerSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */