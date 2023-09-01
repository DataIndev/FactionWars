/*    */ package io.github.guipenedo.factionwars.managers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.WarMap;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class SettingsManager
/*    */ {
/*    */   private static Object get(String paramString, WarMap paramWarMap) {
/* 11 */     if (paramWarMap.getGamemode().getConfig().contains("game-settings") && paramWarMap.getGamemode().getConfig().getConfigurationSection("game-settings").contains(paramString))
/* 12 */       return paramWarMap.getGamemode().getConfig().get("game-settings." + paramString); 
/* 13 */     return FactionWars.getMainConfig().getConfig().get("game-settings." + paramString);
/*    */   }
/*    */   
/*    */   public static List<String> getStringList(String paramString, WarMap paramWarMap) {
/* 17 */     if (paramWarMap.getGamemode().getConfig().contains("game-settings") && paramWarMap.getGamemode().getConfig().getConfigurationSection("game-settings").contains(paramString))
/* 18 */       return paramWarMap.getGamemode().getConfig().getStringList("game-settings." + paramString); 
/* 19 */     return FactionWars.getMainConfig().getConfig().getStringList("game-settings." + paramString);
/*    */   }
/*    */   
/*    */   private static String getString(String paramString, WarMap paramWarMap) {
/* 23 */     return String.valueOf(get(paramString, paramWarMap));
/*    */   }
/*    */   
/*    */   public static int getInt(String paramString, WarMap paramWarMap) {
/* 27 */     return Integer.parseInt(getString(paramString, paramWarMap));
/*    */   }
/*    */   
/*    */   public static double getDouble(String paramString, WarMap paramWarMap) {
/* 31 */     return Double.parseDouble(getString(paramString, paramWarMap));
/*    */   }
/*    */   
/*    */   public static boolean getBool(String paramString, WarMap paramWarMap) {
/* 35 */     return Boolean.parseBoolean(getString(paramString, paramWarMap));
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\SettingsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */