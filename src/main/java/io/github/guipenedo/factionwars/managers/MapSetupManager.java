/*    */ package io.github.guipenedo.factionwars.managers;
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class MapSetupManager {
/*    */   private static MapSetupManager instance;
/*    */   
/*    */   public static MapSetupManager get() {
/* 12 */     if (instance == null)
/* 13 */       instance = new MapSetupManager(); 
/* 14 */     return instance;
/*    */   }
/*    */   
/*    */   public void setup(Player paramPlayer, WarMap paramWarMap) {
/* 18 */     if (!FactionWars.getMapsConfig().getConfig().contains("maps." + paramWarMap.getId() + ".gamemode")) {
/* 19 */       paramPlayer.sendMessage("§6You need to define the map's gamemode");
/* 20 */       paramPlayer.sendMessage("§6You can do so by typing §c/fw setgamemode " + paramWarMap.getId() + " <gamemode>");
/* 21 */       paramPlayer.sendMessage("§6To view available gamemodes type §c/fw listgamemodes");
/* 22 */       paramPlayer.sendMessage("§6You can create your own gamemode on the gamemodes.yml file!");
/*    */       return;
/*    */     } 
/* 25 */     if (!paramWarMap.getLocations().containsKey("l1") || ((ArrayList)paramWarMap.getLocations().get("l1")).size() == 0) {
/* 26 */       paramPlayer.sendMessage("§6This map has no " + Util.getTeam1Color() + "team 1§6 spawn points!");
/* 27 */       paramPlayer.sendMessage("§6Add them using §c/fw setlocation " + paramWarMap.getId() + " l1");
/* 28 */       paramPlayer.sendMessage("§6You may add multiple spawnpoints");
/*    */       return;
/*    */     } 
/* 31 */     if (!paramWarMap.getLocations().containsKey("l2") || ((ArrayList)paramWarMap.getLocations().get("l2")).size() == 0) {
/* 32 */       paramPlayer.sendMessage("§6This map has no " + Util.getTeam2Color() + "team 2§6 spawn points!");
/* 33 */       paramPlayer.sendMessage("§6Add them using §c/fw setlocation " + paramWarMap.getId() + " l2");
/* 34 */       paramPlayer.sendMessage("§6You may add multiple spawnpoints");
/*    */       return;
/*    */     } 
/* 37 */     if (paramWarMap.isSetup()) {
/* 38 */       paramPlayer.sendMessage("§aMap setup is complete!");
/*    */     } else {
/* 40 */       for (String str : paramWarMap.getManager().getLocations(paramWarMap).keySet()) {
/* 41 */         if (!paramWarMap.getLocations().containsKey(str) || ((ArrayList)paramWarMap.getLocations().get(str)).size() == 0) {
/* 42 */           for (String str1 : ((String)paramWarMap.getManager().getLocations(paramWarMap).get(str)).split("\n"))
/* 43 */             paramPlayer.sendMessage("§6" + str1); 
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   } public boolean isLocationValid(WarMap paramWarMap, String paramString) {
/* 49 */     return (paramString.equals("l1") || paramString.equals("l2") || paramWarMap.getManager().getLocations(paramWarMap).keySet().contains(paramString.toLowerCase()));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\MapSetupManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */