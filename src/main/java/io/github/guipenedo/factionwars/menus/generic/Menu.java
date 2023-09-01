/*    */ package io.github.guipenedo.factionwars.menus.generic;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class Menu {
/*    */   protected Player player;
/*    */   protected String title;
/*    */   
/*    */   Menu(Player paramPlayer, String paramString) {
/* 10 */     this.player = paramPlayer;
/* 11 */     this.title = paramString;
/*    */   }
/*    */   public boolean hasPlayer() {
/* 14 */     return (this.player != null);
/*    */   }
/*    */   
/*    */   public abstract void open();
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\Menu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */