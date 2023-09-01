/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.MainMenu;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsGUICommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsGUICommand() {
/* 16 */     this.aliases.add("gui");
/* 17 */     this.aliases.add("g");
/* 18 */     this.aliases.add("gui");
/* 19 */     this.aliases.add("m");
/*    */     
/* 21 */     this.helpText = Util.getMessage("commands.gui");
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 26 */     if (!paramPlayer.hasPermission("factionwars.player.*") && !paramPlayer.hasPermission("factionwars.player.gui")) {
/* 27 */       paramPlayer.sendMessage(Util.getMessage("error.no-permission"));
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     MainMenu mainMenu = new MainMenu(paramPlayer);
/* 32 */     mainMenu.open();
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsGUICommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */