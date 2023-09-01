/*    */ package io.github.guipenedo.factionwars.commands.wars;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.commands.WarsSubCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.PlayerData;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarsToggleCommand
/*    */   extends WarsSubCommand
/*    */ {
/*    */   public WarsToggleCommand() {
/* 18 */     this.aliases.add("toggle");
/* 19 */     this.aliases.add("opt");
/*    */     
/* 21 */     this.helpText = Util.getMessage("commands.toggle");
/*    */   }
/*    */ 
/*    */   
/*    */   public void perform(Player paramPlayer, String paramString, String... paramVarArgs) {
/* 26 */     if (!paramPlayer.hasPermission("factionwars.toggle") && !paramPlayer.hasPermission("factionwars.admin.*")) {
/* 27 */       paramPlayer.sendMessage(Util.getMessage("error.no-permission"));
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     UUID uUID = paramPlayer.getUniqueId();
/* 32 */     if (PlayerData.getOptedOut().contains(uUID)) {
/* 33 */       PlayerData.getOptedOut().remove(uUID);
/* 34 */       paramPlayer.sendMessage(Util.getMessage("toggle-command.off"));
/*    */     } else {
/* 36 */       PlayerData.getOptedOut().add(uUID);
/* 37 */       paramPlayer.sendMessage(Util.getMessage("toggle-command.on"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\wars\WarsToggleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */