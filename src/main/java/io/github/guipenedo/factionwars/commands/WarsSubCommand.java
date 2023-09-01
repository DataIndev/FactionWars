/*    */ package io.github.guipenedo.factionwars.commands;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class WarsSubCommand
/*    */ {
/*  9 */   public ArrayList<String> aliases = new ArrayList<>();
/* 10 */   protected HashMap<String, String> optionalArgs = new HashMap<>();
/* 11 */   protected ArrayList<String> requiredArgs = new ArrayList<>();
/* 12 */   public String helpText = "";
/*    */   protected boolean needsFaction = false;
/*    */   
/*    */   public abstract void perform(Player paramPlayer, String paramString, String... paramVarArgs);
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\WarsSubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */