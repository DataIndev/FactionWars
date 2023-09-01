/*     */ package io.github.guipenedo.factionwars.menus.generic;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class PaginatedGUI extends ChestGUI {
/*     */   private Collection<MenuItem> settingsItems;
/*  22 */   private int page = 1; private Listener pagesListener;
/*  23 */   private final int LEFT_ARROW = 36, RIGHT_ARROW = 44;
/*     */   
/*     */   void setSettingsItems(Collection<MenuItem> paramCollection) {
/*  26 */     this.settingsItems = paramCollection;
/*     */   }
/*     */   
/*     */   PaginatedGUI(Player paramPlayer, String paramString) {
/*  30 */     super(paramPlayer, paramString);
/*  31 */     this.pagesListener = new Listener() {
/*     */         @EventHandler
/*     */         public void onInventoryClick(InventoryClickEvent param1InventoryClickEvent) {
/*  34 */           if (param1InventoryClickEvent.getWhoClicked() instanceof Player && param1InventoryClickEvent.getInventory().equals(PaginatedGUI.this.inv)) {
/*  35 */             param1InventoryClickEvent.setCancelled(true);
/*     */             
/*  37 */             int i = param1InventoryClickEvent.getSlot();
/*     */             
/*  39 */             if (i == 36 && PaginatedGUI.this.page > 1) {
/*  40 */               PaginatedGUI.this.page--;
/*  41 */               PaginatedGUI.this.open();
/*     */               
/*     */               return;
/*     */             } 
/*  45 */             if (i == 44 && PaginatedGUI.this.page < PaginatedGUI.this.getMaxPages()) {
/*  46 */               PaginatedGUI.this.page++;
/*  47 */               PaginatedGUI.this.open();
/*     */               
/*     */               return;
/*     */             } 
/*  51 */             ItemStack itemStack = param1InventoryClickEvent.getCurrentItem();
/*  52 */             if (PaginatedGUI.this.settingsItems != null && itemStack != null)
/*  53 */               for (MenuItem menuItem : PaginatedGUI.this.settingsItems) {
/*  54 */                 if (!itemStack.equals(menuItem.getItem()))
/*  55 */                   continue;  MenuItem.MenuItemClickEvent menuItemClickEvent = new MenuItem.MenuItemClickEvent(param1InventoryClickEvent.getClick());
/*  56 */                 menuItem.getHandler().onMenuItemClick(menuItemClickEvent);
/*     */                 
/*  58 */                 if (menuItemClickEvent.getWillDestroy()) {
/*  59 */                   PaginatedGUI.this.destroy();
/*     */                 }
/*  61 */                 if (menuItemClickEvent.getWillClose()) {
/*  62 */                   param1InventoryClickEvent.getWhoClicked().closeInventory();
/*     */                 }
/*     */                 return;
/*     */               }  
/*     */           } 
/*     */         }
/*     */       };
/*  69 */     Bukkit.getPluginManager().registerEvents(this.pagesListener, (Plugin)FactionWars.get());
/*     */   }
/*     */ 
/*     */   
/*     */   void destroy() {
/*  74 */     HandlerList.unregisterAll(this.pagesListener);
/*     */     
/*  76 */     this.settingsItems = null;
/*     */     
/*  78 */     this.pagesListener = null;
/*     */     
/*  80 */     super.destroy();
/*     */   }
/*     */   
/*     */   private int getMaxPages() {
/*  84 */     return (int)Math.ceil(this.menuItems.size() / 21.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void open() {
/*  89 */     if (this.menuItems == null || this.settingsItems == null)
/*  90 */       return;  int i = (this.page - 1) * 21;
/*  91 */     if (i >= this.menuItems.size()) {
/*  92 */       this.page = 1;
/*  93 */       i = 0;
/*     */     } 
/*     */     
/*  96 */     this.inv = Bukkit.createInventory((InventoryHolder)this.player, 54, this.title);
/*     */     
/*  98 */     MenuItem[] arrayOfMenuItem = this.menuItems.<MenuItem>toArray(new MenuItem[0]);
/*  99 */     int j = 10;
/* 100 */     for (int k = i; k < i + 21 && k < arrayOfMenuItem.length; k++, j++) {
/* 101 */       this.inv.setItem(j, arrayOfMenuItem[k].getItem());
/* 102 */       if ((k + 1) % 7 == 0)
/* 103 */         j += 2; 
/*     */     } 
/* 105 */     HashMap hashMap = Util.getVars(new Object[] { "prevpage", Integer.valueOf(this.page - 1), "nextpage", Integer.valueOf(this.page + 1) });
/* 106 */     if (this.page > 1)
/* 107 */       this.inv.setItem(36, (new ItemBuilder(new ItemStack(Material.ARROW), Util.getPlainMessage("menus.selector.prev-page", hashMap))).build()); 
/* 108 */     if (this.page < getMaxPages()) {
/* 109 */       this.inv.setItem(44, (new ItemBuilder(new ItemStack(Material.ARROW), Util.getPlainMessage("menus.selector.next-page", hashMap))).build());
/*     */     }
/*     */     
/* 112 */     arrayOfMenuItem = this.settingsItems.<MenuItem>toArray(new MenuItem[0]);
/* 113 */     j = 45 + Math.max((9 - arrayOfMenuItem.length) / 2, 0);
/* 114 */     for (byte b = 0; b < 7 && b < arrayOfMenuItem.length; b++, j++)
/* 115 */       this.inv.setItem(j, arrayOfMenuItem[b].getItem()); 
/* 116 */     LegacyUtil.playChestSound(this.player);
/* 117 */     this.player.openInventory(this.inv);
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\PaginatedGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */