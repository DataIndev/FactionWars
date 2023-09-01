/*    */ package io.github.guipenedo.factionwars.menus;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.generic.ChestMenu;
/*    */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*    */ import io.github.guipenedo.factionwars.models.Kit;
/*    */ import io.github.guipenedo.factionwars.models.PlayerData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KitSelector
/*    */   extends ChestMenu {
/*    */   public KitSelector(Player paramPlayer) {
/* 18 */     super(paramPlayer, Util.getMessage("kit.menu-title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean openMenu() {
/* 23 */     for (Iterator<Kit> iterator = Kit.getKits().iterator(); iterator.hasNext(); ) { Kit kit = iterator.next();
/*    */       
/* 25 */       String str = "§" + (kit.canBeSelectedBy(this.player) ? (kit.isFree() ? "a" : "6") : "c") + Util.substring16(kit.getName());
/*    */       
/* 27 */       ItemBuilder itemBuilder = new ItemBuilder(kit.getItem(), str);
/* 28 */       if (!kit.isFree())
/* 29 */         itemBuilder.addLore("§6Price: " + kit.getPrice()); 
/* 30 */       itemBuilder.addLore(kit.getItem());
/* 31 */       itemBuilder.glow = true;
/* 32 */       this.items.add(new MenuItem(itemBuilder, paramMenuItemClickEvent -> checkSelectKit(paramKit))); }
/*    */     
/* 34 */     openGUI();
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   private void checkSelectKit(Kit paramKit) {
/* 39 */     if (!paramKit.canBeSelectedBy(this.player)) {
/* 40 */       this.player.sendMessage(Util.getMessage("kit.no-permission"));
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     if (!paramKit.isFree() && FactionWars.getEcon().getBalance(this.player.getUniqueId()) < paramKit.getPrice()) {
/* 45 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 46 */       hashMap.put("coins", Double.valueOf(paramKit.getPrice() - FactionWars.getEcon().getBalance(this.player.getUniqueId())));
/* 47 */       this.player.sendMessage(Util.getMessage("kit.no-money", hashMap));
/*    */       return;
/*    */     } 
/* 50 */     selectKit(paramKit);
/*    */   }
/*    */   
/*    */   public static Kit getDefaultKit(Player paramPlayer) {
/* 54 */     ArrayList<Kit> arrayList = new ArrayList();
/* 55 */     for (Kit kit : Kit.getKits()) {
/* 56 */       if (kit.getPrice() <= 0.0D) {
/* 57 */         if (kit.canBeSelectedBy(paramPlayer))
/* 58 */           return kit; 
/* 59 */         arrayList.add(kit);
/*    */       } 
/*    */     } 
/* 62 */     return (arrayList.size() > 0) ? arrayList.get(0) : null;
/*    */   }
/*    */   
/*    */   private void selectKit(Kit paramKit) {
/* 66 */     PlayerData playerData = PlayerData.getPlayerData(this.player.getUniqueId());
/* 67 */     if (playerData != null) {
/* 68 */       playerData.setKit(paramKit.getId());
/* 69 */       this.player.sendMessage("§aKit: §e§l" + paramKit.getName());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\KitSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */