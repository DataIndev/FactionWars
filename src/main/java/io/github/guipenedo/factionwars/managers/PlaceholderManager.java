/*    */ package io.github.guipenedo.factionwars.managers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.models.FactionData;
/*    */ import me.clip.placeholderapi.expansion.PlaceholderExpansion;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlaceholderManager
/*    */   extends PlaceholderExpansion
/*    */ {
/*    */   public String getIdentifier() {
/* 12 */     return "facwars";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAuthor() {
/* 17 */     return String.join(", ", FactionWars.get().getDescription().getAuthors());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 22 */     return FactionWars.get().getDescription().getVersion();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String onPlaceholderRequest(Player paramPlayer, String paramString) {
/* 28 */     if (paramPlayer == null) {
/* 29 */       return "";
/*    */     }
/*    */     
/* 32 */     String str = FactionWars.getHandler().getPlayerTeam(paramPlayer);
/*    */     
/* 34 */     if (str == null) {
/* 35 */       return "";
/*    */     }
/* 37 */     FactionData factionData = FactionData.getFaction(str);
/* 38 */     if (factionData == null) {
/* 39 */       return "0";
/*    */     }
/*    */     
/* 42 */     switch (paramString) {
/*    */       case "wins":
/* 44 */         return String.valueOf(factionData.getWins());
/*    */       case "losses":
/* 46 */         return String.valueOf(factionData.getLosts());
/*    */       case "rank":
/* 48 */         return String.valueOf(factionData.getRank());
/*    */       case "kills":
/* 50 */         return String.valueOf(factionData.getKills());
/*    */       case "deaths":
/* 52 */         return String.valueOf(factionData.getDeaths());
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\managers\PlaceholderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */