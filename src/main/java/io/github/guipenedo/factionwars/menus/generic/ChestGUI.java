/*     */ package io.github.guipenedo.factionwars.menus.generic;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class ChestGUI {
/*     */   Player player;
/*     */   String title;
/*     */   Collection<MenuItem> menuItems;
/*     */   Inventory inv;
/*     */   private Listener listener;
/*     */   private boolean shouldReopen = false;
/*     */   
/*     */   public void setShouldReopen(boolean paramBoolean) {
/*  30 */     this.shouldReopen = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setMenuPlayerQuitEventHandler(MenuPlayerQuitEventHandler paramMenuPlayerQuitEventHandler) {
/*  34 */     this.menuPlayerQuitEventHandler = paramMenuPlayerQuitEventHandler;
/*     */   }
/*     */   
/*  37 */   private MenuPlayerQuitEventHandler menuPlayerQuitEventHandler = null;
/*     */   
/*     */   void setMenuItems(Collection<MenuItem> paramCollection) {
/*  40 */     this.menuItems = Collections.synchronizedCollection(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChestGUI(final Player player, String paramString) {
/*  48 */     this.player = player;
/*  49 */     this.title = paramString;
/*  50 */     this.listener = new Listener() {
/*     */         @EventHandler
/*     */         public void onInventoryClick(InventoryClickEvent param1InventoryClickEvent) {
/*  53 */           if (param1InventoryClickEvent.getWhoClicked() instanceof Player && param1InventoryClickEvent.getInventory().equals(ChestGUI.this.inv)) {
/*  54 */             param1InventoryClickEvent.setCancelled(true);
/*     */             
/*  56 */             ItemStack itemStack = param1InventoryClickEvent.getCurrentItem();
/*  57 */             if (itemStack == null)
/*  58 */               return;  FactionWars.debug("parsing click on " + itemStack.toString());
/*  59 */             for (MenuItem menuItem : ChestGUI.this.menuItems) {
/*  60 */               if (itemStack.hasItemMeta() && menuItem.getItem().hasItemMeta() && itemStack.getItemMeta() instanceof SkullMeta && menuItem.getItem().getItemMeta() instanceof SkullMeta)
/*  61 */               { SkullMeta skullMeta1 = (SkullMeta)itemStack.getItemMeta(), skullMeta2 = (SkullMeta)menuItem.getItem().getItemMeta();
/*  62 */                 if (LegacyUtil.is1_13 ? (
/*  63 */                   skullMeta1.hasOwner() != skullMeta2.hasOwner() || (skullMeta1.hasOwner() && skullMeta1.getOwningPlayer() != null && skullMeta2.getOwningPlayer() != null && skullMeta1.getOwningPlayer().getName() != null && !skullMeta1.getOwningPlayer().getName().equals(skullMeta2.getOwningPlayer().getName()))) : (
/*     */ 
/*     */                   
/*  66 */                   skullMeta1.hasOwner() != skullMeta2.hasOwner() || (skullMeta1.hasOwner() && skullMeta1.getOwner() != null && skullMeta2.getOwner() != null && skullMeta1.getOwner() != null && !skullMeta1.getOwner().equals(skullMeta2.getOwner())))) {
/*     */                   continue;
/*     */                 } }
/*  69 */               else if (!itemStack.equals(menuItem.getItem())) { continue; }
/*  70 */                MenuItem.MenuItemClickEvent menuItemClickEvent = new MenuItem.MenuItemClickEvent(param1InventoryClickEvent.getClick());
/*  71 */               menuItem.getHandler().onMenuItemClick(menuItemClickEvent);
/*     */               
/*  73 */               if (menuItemClickEvent.getWillDestroy()) {
/*  74 */                 ChestGUI.this.destroy();
/*     */               }
/*  76 */               if (menuItemClickEvent.getWillClose()) {
/*  77 */                 param1InventoryClickEvent.getWhoClicked().closeInventory();
/*     */               }
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         @EventHandler
/*     */         public void onInventoryClose(final InventoryCloseEvent event) {
/*  86 */           if (event.getPlayer() instanceof Player) {
/*  87 */             Inventory inventory = event.getInventory();
/*     */             
/*  89 */             if (inventory.equals(ChestGUI.this.inv)) {
/*  90 */               if (ChestGUI.this.shouldReopen) {
/*  91 */                 (new BukkitRunnable()
/*     */                   {
/*     */                     public void run() {
/*  94 */                       if (event.getPlayer() != null && ((Player)event.getPlayer()).isOnline() && ChestGUI.this.menuItems != null)
/*  95 */                         ChestGUI.this.open(); 
/*     */                     }
/*  97 */                   }).runTaskLater((Plugin)FactionWars.get(), 1L);
/*     */               } else {
/*  99 */                 inventory.clear();
/* 100 */                 ChestGUI.this.destroy();
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/*     */         @EventHandler
/*     */         public void onPlayerQuit(PlayerQuitEvent param1PlayerQuitEvent) {
/* 108 */           if (param1PlayerQuitEvent.getPlayer().equals(player)) {
/* 109 */             ChestGUI.this.destroy();
/* 110 */             if (ChestGUI.this.menuPlayerQuitEventHandler != null) {
/* 111 */               ChestGUI.this.menuPlayerQuitEventHandler.onMenuPlayerQuit();
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 116 */     Bukkit.getPluginManager().registerEvents(this.listener, (Plugin)FactionWars.get());
/*     */   }
/*     */   
/*     */   public void open() {
/* 120 */     LegacyUtil.playChestSound(this.player);
/* 121 */     int i = (int)Math.ceil(this.menuItems.size() / 9.0D) * 9;
/* 122 */     this.inv = Bukkit.createInventory((InventoryHolder)this.player, i, this.title);
/* 123 */     this.inv.setContents(MenuItem.getContents(this.menuItems));
/* 124 */     this.player.openInventory(this.inv);
/*     */   } public static interface MenuPlayerQuitEventHandler {
/*     */     void onMenuPlayerQuit(); }
/*     */   void destroy() {
/* 128 */     this.player = null;
/* 129 */     this.menuItems = null;
/*     */     
/* 131 */     HandlerList.unregisterAll(this.listener);
/*     */     
/* 133 */     this.listener = null;
/*     */   }
/*     */   
/*     */   public void setTitle(String paramString) {
/* 137 */     this.title = paramString;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\ChestGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */