/*    */ package io.github.guipenedo.factionwars.menus.generic;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class NumericMenu
/*    */   extends ChestMenu {
/*    */   protected NumericMenu(Player paramPlayer, String paramString) {
/*  9 */     super(paramPlayer, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ChestGUI initializeGUI() {
/* 14 */     return new NumericGUI(this.player, this.title);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void openGUI() {
/* 19 */     Collection<MenuItem> collection = this.gui.menuItems;
/* 20 */     super.openGUI();
/* 21 */     this.gui.setMenuItems(collection);
/*    */   }
/*    */   
/*    */   protected void setConfirmItem(MenuItem paramMenuItem) {
/* 25 */     ((NumericGUI)this.gui).setConfirm(paramMenuItem);
/*    */   }
/*    */   
/*    */   protected void setCancelItem(MenuItem paramMenuItem) {
/* 29 */     ((NumericGUI)this.gui).setCancel(paramMenuItem);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\NumericMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */