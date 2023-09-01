/*    */ package io.github.guipenedo.factionwars.helpers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.attribute.Attribute;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.material.Wool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyUtil
/*    */ {
/* 41 */   private static String version = Bukkit.getServer().getVersion().split("\\(MC: ")[1].split("\\)")[0];
/*    */   
/* 43 */   public static boolean is1_7 = (version.equals("1.7") || version.matches("1.7.*"));
/* 44 */   public static boolean is1_8 = (version.equals("1.8") || version.matches("1.8.*"));
/* 45 */   public static boolean is1_13 = (!version.matches("(^|.*[^.\\d])1\\.1[0-2]([^\\d].*|$)") && !version.matches("(^|.*[^.\\d])1\\.[0-9]([^\\d].*|$)"));
/* 46 */   public static final Material HEAD = Material.valueOf(is1_13 ? "PLAYER_HEAD" : "SKULL_ITEM");
/* 47 */   public static final Material BOOK_AND_QUILL = Material.valueOf(is1_13 ? "WRITABLE_BOOK" : "BOOK_AND_QUILL");
/* 48 */   public static final Material WATCH = Material.valueOf(is1_13 ? "CLOCK" : "WATCH");
/* 49 */   public static final Material CANCEL_ITEM = Material.valueOf(is1_7 ? "BED" : "BARRIER");
/* 50 */   public static final Material STAINED_GLASS_PANE = Material.valueOf(is1_13 ? "WHITE_STAINED_GLASS_PANE" : "STAINED_GLASS_PANE");
/* 51 */   public static ItemStack LIME_WOOL_ITEMSTACK = !is1_13 ? (new Wool(DyeColor.LIME)).toItemStack(1) : new ItemStack(Material.valueOf("LIME_WOOL"));
/*    */   
/*    */   public static boolean is1_14() {
/*    */     
/* 55 */     try { Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + "ContainerAccess");
/* 56 */       return true; }
/* 57 */     catch (Exception exception) { return false; }
/*    */   
/*    */   }
/*    */   public static void playChestSound(Player paramPlayer) {
/*    */     Sound sound;
/*    */     try {
/* 63 */       sound = Sound.valueOf("CHEST_OPEN");
/* 64 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 65 */       sound = Sound.valueOf("BLOCK_CHEST_OPEN");
/*    */     } 
/*    */     try {
/* 68 */       paramPlayer.playSound(paramPlayer.getLocation(), sound, 1.0F, 1.0F);
/* 69 */     } catch (Exception exception) {}
/*    */   }
/*    */   
/*    */   public static void sendTitle(Player paramPlayer, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
/* 73 */     if (FactionWars.getMainConfig().getConfig().getBoolean("titles.enabled")) {
/* 74 */       if (ReflectionUtil.getVersion().contains("1_8") || ReflectionUtil.getVersion().contains("1_9") || ReflectionUtil.getVersion().contains("1_10")) {
/* 75 */         Title title = new Title(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
/* 76 */         title.setTimingsToTicks();
/* 77 */         title.send(paramPlayer);
/* 78 */       } else if (ReflectionUtil.getVersion().contains("1_11")) {
/* 79 */         Title11 title11 = new Title11(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
/* 80 */         title11.setTimingsToTicks();
/* 81 */         title11.send(paramPlayer);
/*    */       } else {
/* 83 */         paramPlayer.sendTitle(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static void maxHealth(Player paramPlayer) {
/* 89 */     if (!is1_8 && !is1_7) {
/* 90 */       paramPlayer.setHealth(paramPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
/*    */     } else {
/* 92 */       paramPlayer.setHealth(paramPlayer.getMaxHealth());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\LegacyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */