/*    */ package io.github.guipenedo.factionwars.menus;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.generic.ChestMenu;
/*    */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*    */ import io.github.guipenedo.factionwars.models.Gamemode;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class GamemodeSelector
/*    */   extends ChestMenu
/*    */ {
/*    */   public GamemodeSelector(Player paramPlayer) {
/* 17 */     super(paramPlayer, Util.getMessage("gamemode.menu-title"));
/* 18 */     this.gui.setShouldReopen(true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean openMenu() {
/* 23 */     PlayerSelection playerSelection = PlayerSelection.getBySelector(this.player.getUniqueId());
/* 24 */     FactionWars.debug("GM is selection null: " + ((playerSelection == null) ? 1 : 0));
/* 25 */     if (playerSelection == null) return false; 
/* 26 */     Map map = Gamemode.getSetupGamemodes();
/* 27 */     if (map.isEmpty()) {
/* 28 */       this.player.sendMessage(Util.getMessage("error.no-gamemodes"));
/* 29 */       PlayerSelection.getPlayerSelections().remove(playerSelection);
/* 30 */       return false;
/*    */     } 
/* 32 */     if (map.size() == 1) {
/* 33 */       FactionWars.debug("GM setting gamemode (1)");
/* 34 */       selectGamemode(map.values().iterator().next());
/* 35 */       return false;
/*    */     } 
/* 37 */     for (Gamemode gamemode : map.values()) {
/*    */       
/* 39 */       String str = "§" + (gamemode.canBeSelectedBy(this.player) ? "a" : "c") + Util.substring16(gamemode.getName());
/* 40 */       ItemBuilder itemBuilder = new ItemBuilder(gamemode.getItem(), str);
/* 41 */       itemBuilder.addLore(gamemode.getItem());
/* 42 */       if (playerSelection.getGamemode().equals(gamemode))
/* 43 */         itemBuilder.glow = true; 
/* 44 */       this.items.add(new MenuItem(itemBuilder, paramMenuItemClickEvent -> selectGamemode(paramGamemode)));
/*    */     } 
/*    */     
/* 47 */     this.gui.setMenuPlayerQuitEventHandler(() -> PlayerSelection.getPlayerSelections().remove(paramPlayerSelection));
/* 48 */     openGUI();
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   private void selectGamemode(Gamemode paramGamemode) {
/* 53 */     PlayerSelection playerSelection = PlayerSelection.getBySelector(this.player.getUniqueId());
/* 54 */     if (playerSelection == null || paramGamemode == null)
/* 55 */       return;  if (!paramGamemode.canBeSelectedBy(this.player)) {
/* 56 */       this.player.sendMessage(Util.getMessage("gamemode.no-permission"));
/*    */       return;
/*    */     } 
/* 59 */     playerSelection.setGamemode(paramGamemode);
/* 60 */     Util.openNext(this.player);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\GamemodeSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */