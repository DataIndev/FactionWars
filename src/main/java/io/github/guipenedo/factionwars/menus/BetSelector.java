/*    */ package io.github.guipenedo.factionwars.menus;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*    */ import io.github.guipenedo.factionwars.menus.generic.NumericGUI;
/*    */ import io.github.guipenedo.factionwars.menus.generic.NumericMenu;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BetSelector
/*    */   extends NumericMenu {
/*    */   private PlayerSelection playerSelection;
/*    */   
/*    */   public BetSelector(Player paramPlayer) {
/* 18 */     super(paramPlayer, Util.getMessage("menus.selector.bet-value"));
/* 19 */     setCancelItem(new MenuItem(new ItemBuilder(new ItemStack(LegacyUtil.CANCEL_ITEM), Util.getPlainMessage("menus.selector.cancel")), paramMenuItemClickEvent -> PlayerSelection.getPlayerSelections().remove(this.playerSelection)));
/* 20 */     setConfirmItem(new MenuItem(new ItemBuilder(LegacyUtil.LIME_WOOL_ITEMSTACK, Util.getPlainMessage("menus.selector.finish")), paramMenuItemClickEvent -> {
/*    */             double d = ((NumericGUI)this.gui).getValue();
/*    */             if (Util.validBet(paramPlayer, d)) {
/*    */               this.playerSelection.setBet(Double.valueOf(d));
/*    */               Util.openNext(paramPlayer);
/*    */             } else {
/*    */               paramMenuItemClickEvent.setWillClose(false);
/*    */               paramMenuItemClickEvent.setWillDestroy(false);
/*    */             } 
/*    */           }));
/* 30 */     this.gui.setShouldReopen(true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean openMenu() {
/* 35 */     this.playerSelection = PlayerSelection.getBySelector(this.player.getUniqueId());
/* 36 */     if (this.playerSelection == null) return false; 
/* 37 */     ((NumericGUI)this.gui).setValue(this.playerSelection.getBet().doubleValue());
/* 38 */     this.gui.setMenuPlayerQuitEventHandler(() -> PlayerSelection.getPlayerSelections().remove(this.playerSelection));
/* 39 */     openGUI();
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\BetSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */