/*     */ package io.github.guipenedo.factionwars.menus.generic;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import io.github.guipenedo.factionwars.helpers.TitleUpdater;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class NumericGUI extends ChestGUI {
/*     */   public double getValue() {
/*  19 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(double paramDouble) {
/*  23 */     this.value = paramDouble;
/*     */   }
/*     */   
/*  26 */   private double value = 0.0D;
/*  27 */   private MenuItem[] numKeys = new MenuItem[10];
/*     */   
/*     */   private MenuItem delete;
/*     */   
/*     */   public void setCancel(MenuItem paramMenuItem) {
/*  32 */     this.cancel = paramMenuItem;
/*  33 */     this.menuItems.add(paramMenuItem);
/*     */   }
/*     */   private MenuItem cancel; private MenuItem confirm;
/*     */   public void setConfirm(MenuItem paramMenuItem) {
/*  37 */     this.confirm = paramMenuItem;
/*  38 */     this.menuItems.add(paramMenuItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MenuItem getKey(int paramInt) {
/*  44 */     ItemStack itemStack = (paramInt != 0) ? (new ItemBuilder(new ItemStack(Material.STONE_BUTTON, paramInt), "&6" + paramInt)).build() : (new ItemBuilder(new ItemStack(Material.EGG), "&60")).build();
/*  45 */     return new MenuItem(itemStack, paramMenuItemClickEvent -> {
/*     */           this.value *= 10.0D;
/*     */           this.value += paramInt;
/*     */           TitleUpdater.update(this.player, getTitle(this.value));
/*     */           paramMenuItemClickEvent.setWillClose(false);
/*     */           paramMenuItemClickEvent.setWillDestroy(false);
/*     */         });
/*     */   }
/*     */   
/*     */   NumericGUI(Player paramPlayer, String paramString) {
/*  55 */     super(paramPlayer, paramString);
/*  56 */     this.menuItems = new ArrayList<>();
/*  57 */     for (byte b = 0; b <= 9; b++) {
/*  58 */       this.numKeys[b] = getKey(b);
/*  59 */       this.menuItems.add(this.numKeys[b]);
/*     */     } 
/*  61 */     this.delete = new MenuItem((new ItemBuilder(new ItemStack(Material.SHEARS), "&4<-")).build(), paramMenuItemClickEvent -> {
/*     */           this.value = (this.value - this.value % 10.0D) / 10.0D;
/*     */           TitleUpdater.update(paramPlayer, getTitle(this.value));
/*     */           paramMenuItemClickEvent.setWillClose(false);
/*     */           paramMenuItemClickEvent.setWillDestroy(false);
/*     */         });
/*  67 */     this.menuItems.add(this.delete);
/*     */   }
/*     */ 
/*     */   
/*     */   void destroy() {
/*  72 */     this.delete = null;
/*     */     
/*  74 */     this.confirm = null;
/*     */     
/*  76 */     this.cancel = null;
/*     */     
/*  78 */     this.numKeys = null;
/*  79 */     super.destroy();
/*     */   }
/*     */   
/*     */   public static String getTitle(double paramDouble) {
/*  83 */     NumberFormat numberFormat = DecimalFormat.getInstance();
/*  84 */     numberFormat.setMaximumFractionDigits(0);
/*  85 */     String str = Util.getPlainMessage("menus.selector.bet-value", Collections.singletonMap("value", numberFormat.format(paramDouble)));
/*  86 */     if (str.length() > 22)
/*  87 */       str = str.substring(0, 22); 
/*  88 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public void open() {
/*  93 */     this.inv = Bukkit.createInventory((InventoryHolder)this.player, 54, getTitle(this.value));
/*     */     
/*  95 */     if (this.numKeys == null)
/*     */       return; 
/*  97 */     byte b1 = 3;
/*  98 */     for (byte b2 = 1; b2 <= 9; b2++) {
/*  99 */       this.inv.setItem(b1 + b2 - 1, this.numKeys[b2].getItem());
/* 100 */       if (b2 % 3 == 0)
/* 101 */         b1 += 6; 
/*     */     } 
/* 103 */     this.inv.setItem(b1 + 10, this.numKeys[0].getItem());
/* 104 */     this.inv.setItem(40, this.delete.getItem());
/* 105 */     this.inv.setItem(48, this.cancel.getItem());
/* 106 */     this.inv.setItem(50, this.confirm.getItem());
/*     */     
/* 108 */     LegacyUtil.playChestSound(this.player);
/* 109 */     this.player.openInventory(this.inv);
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\NumericGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */