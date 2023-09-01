/*    */ package io.github.guipenedo.factionwars.commands;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.defaults.BukkitCommand;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class WarsCommand
/*    */   extends BukkitCommand {
/*    */   public void addSubCommand(WarsSubCommand paramWarsSubCommand) {
/* 15 */     this.commands.add(paramWarsSubCommand);
/*    */   }
/*    */   private ArrayList<WarsSubCommand> commands;
/*    */   public WarsCommand(String paramString) {
/* 19 */     super(paramString);
/*    */ 
/*    */     
/* 22 */     this.commands = new ArrayList<>();
/*    */   }
/*    */   
/*    */   public boolean execute(@NotNull CommandSender paramCommandSender, @NotNull String paramString, String[] paramArrayOfString) {
/* 26 */     if (paramArrayOfString.length > 0) {
/* 27 */       if (!(paramCommandSender instanceof Player)) {
/* 28 */         return false;
/*    */       }
/* 30 */       if (Util.runMapChecks(paramCommandSender)) {
/* 31 */         return true;
/*    */       }
/*    */       
/* 34 */       WarsSubCommand warsSubCommand = null;
/* 35 */       for (WarsSubCommand warsSubCommand1 : this.commands) {
/* 36 */         if (warsSubCommand1.aliases.contains(paramArrayOfString[0].toLowerCase()))
/* 37 */           warsSubCommand = warsSubCommand1; 
/* 38 */       }  if (warsSubCommand == null) {
/* 39 */         showHelp(paramCommandSender);
/* 40 */         return true;
/*    */       } 
/*    */       
/* 43 */       Player player = (Player)paramCommandSender;
/*    */       
/* 45 */       if (warsSubCommand.needsFaction && FactionWars.getHandler().getPlayerTeam(player) == null) {
/* 46 */         player.sendMessage(Util.getMessage("error.no-faction"));
/* 47 */         return true;
/*    */       } 
/*    */       
/* 50 */       if (paramArrayOfString.length - 1 < warsSubCommand.requiredArgs.size()) {
/* 51 */         showHelp(paramCommandSender);
/* 52 */         return true;
/*    */       } 
/*    */       
/* 55 */       warsSubCommand.perform(player, FactionWars.getHandler().getPlayerTeam(player), Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length));
/*    */     } else {
/* 57 */       showHelp(paramCommandSender);
/* 58 */     }  return false;
/*    */   }
/*    */   
/*    */   private void showHelp(CommandSender paramCommandSender) {
/* 62 */     paramCommandSender.sendMessage(Util.getMessage("commands.help"));
/* 63 */     for (WarsSubCommand warsSubCommand : this.commands) {
/* 64 */       StringBuilder stringBuilder = new StringBuilder();
/* 65 */       for (String str : warsSubCommand.requiredArgs)
/* 66 */         stringBuilder.append(" [").append(formatTeam(str)).append("]"); 
/* 67 */       for (String str : warsSubCommand.optionalArgs.keySet())
/* 68 */         stringBuilder.append(" <").append(formatTeam(str)).append("=").append(warsSubCommand.optionalArgs.get(str)).append(">"); 
/* 69 */       paramCommandSender.sendMessage("§3/" + getName() + " " + (String)warsSubCommand.aliases.get(0) + " " + stringBuilder.toString() + " §c- " + formatTeam(warsSubCommand.helpText));
/*    */     } 
/*    */   }
/*    */   
/*    */   private String formatTeam(String paramString) {
/* 74 */     return paramString.replace("team", Util.getPlainMessage("commands.team-string"));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\WarsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */