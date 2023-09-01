/*    */ package io.github.guipenedo.factionwars.menus.generic;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class PaginatedMenu
/*    */   extends ChestMenu {
/*  8 */   protected ArrayList<MenuItem> settingsItems = new ArrayList<>();
/*    */   
/*    */   protected PaginatedMenu(Player paramPlayer, String paramString) {
/* 11 */     super(paramPlayer, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ChestGUI initializeGUI() {
/* 16 */     return new PaginatedGUI(this.player, this.title);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void openGUI() {
/* 21 */     ((PaginatedGUI)this.gui).setSettingsItems(this.settingsItems);
/* 22 */     super.openGUI();
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\PaginatedMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */