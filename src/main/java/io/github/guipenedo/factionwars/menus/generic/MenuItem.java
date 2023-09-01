/*    */ package io.github.guipenedo.factionwars.menus.generic;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.event.inventory.ClickType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class MenuItem
/*    */ {
/*    */   private ItemStack item;
/*    */   private MenuItemClickHandler handler;
/*    */   
/*    */   public MenuItem(ItemStack paramItemStack, MenuItemClickHandler paramMenuItemClickHandler) {
/* 15 */     this.item = paramItemStack;
/* 16 */     this.handler = paramMenuItemClickHandler;
/*    */   }
/*    */   
/*    */   public MenuItem(ItemBuilder paramItemBuilder, MenuItemClickHandler paramMenuItemClickHandler) {
/* 20 */     this.item = paramItemBuilder.build();
/* 21 */     this.handler = paramMenuItemClickHandler;
/*    */   }
/*    */   
/*    */   static ItemStack[] getContents(Collection<MenuItem> paramCollection) {
/* 25 */     ArrayList<ItemStack> arrayList = new ArrayList();
/* 26 */     for (MenuItem menuItem : paramCollection)
/* 27 */       arrayList.add(menuItem.getItem()); 
/* 28 */     return arrayList.<ItemStack>toArray(new ItemStack[0]);
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 32 */     return this.item;
/*    */   }
/*    */   
/*    */   public MenuItemClickHandler getHandler() {
/* 36 */     return this.handler;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class MenuItemClickEvent
/*    */   {
/*    */     private boolean close = true;
/*    */     
/*    */     private boolean destroy = true;
/*    */     
/*    */     private ClickType clickType;
/*    */     
/*    */     MenuItemClickEvent(ClickType param1ClickType) {
/* 49 */       this.clickType = param1ClickType;
/*    */     }
/*    */     
/*    */     public ClickType getClickType() {
/* 53 */       return this.clickType;
/*    */     }
/*    */     
/*    */     boolean getWillClose() {
/* 57 */       return this.close;
/*    */     }
/*    */     
/*    */     public void setWillClose(boolean param1Boolean) {
/* 61 */       this.close = param1Boolean;
/*    */     }
/*    */     
/*    */     boolean getWillDestroy() {
/* 65 */       return this.destroy;
/*    */     }
/*    */     
/*    */     public void setWillDestroy(boolean param1Boolean) {
/* 69 */       this.destroy = param1Boolean;
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface MenuItemClickHandler {
/*    */     void onMenuItemClick(MenuItem.MenuItemClickEvent param1MenuItemClickEvent);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\MenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */