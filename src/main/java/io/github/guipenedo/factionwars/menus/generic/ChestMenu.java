/*    */ package io.github.guipenedo.factionwars.menus.generic;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class ChestMenu
/*    */   extends Menu {
/*  8 */   protected ArrayList<MenuItem> items = new ArrayList<>();
/*    */   protected ChestGUI gui;
/*    */   
/*    */   protected ChestMenu(Player paramPlayer, String paramString) {
/* 12 */     super(paramPlayer, paramString);
/* 13 */     this.gui = initializeGUI();
/*    */   }
/*    */   
/*    */   protected ChestGUI initializeGUI() {
/* 17 */     return new ChestGUI(this.player, this.title);
/*    */   }
/*    */ 
/*    */   
/*    */   public void open() {
/* 22 */     if (!openMenu())
/* 23 */       this.gui.destroy(); 
/*    */   }
/*    */   
/*    */   protected abstract boolean openMenu();
/*    */   
/*    */   protected void openGUI() {
/* 29 */     this.gui.setTitle(this.title);
/* 30 */     this.gui.setMenuItems(this.items);
/* 31 */     this.gui.open();
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\ChestMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */