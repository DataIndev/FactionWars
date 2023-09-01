/*    */ package io.github.guipenedo.factionwars.helpers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemFlag;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.inventory.meta.SkullMeta;
/*    */ 
/*    */ public class ItemBuilder {
/*    */   public boolean glow;
/*    */   private ItemStack item;
/*    */   private String name;
/*    */   private List<String> lore;
/*    */   
/*    */   public ItemBuilder(Material paramMaterial, String paramString, List<String> paramList, boolean paramBoolean) {
/* 21 */     this(new ItemStack(paramMaterial), paramString, paramList, paramBoolean);
/*    */   }
/*    */   
/*    */   public ItemBuilder(ItemStack paramItemStack, String paramString, List<String> paramList, boolean paramBoolean) {
/* 25 */     this.item = (paramItemStack == null) ? null : paramItemStack.clone();
/* 26 */     this.name = paramString.replaceAll("&", "§");
/* 27 */     ArrayList<String> arrayList = new ArrayList();
/* 28 */     if (paramList != null)
/* 29 */       for (String str : paramList)
/* 30 */         arrayList.add(str.replaceAll("&", "§"));  
/* 31 */     this.lore = arrayList;
/* 32 */     this.glow = paramBoolean;
/*    */   }
/*    */   
/*    */   public ItemBuilder() {
/* 36 */     this(null, "");
/*    */   }
/*    */   
/*    */   public ItemBuilder(ItemStack paramItemStack, String paramString) {
/* 40 */     this(paramItemStack, paramString, (List<String>)null, false);
/*    */   }
/*    */   
/*    */   public ItemBuilder(ItemStack paramItemStack, String paramString, String... paramVarArgs) {
/* 44 */     this(paramItemStack, paramString, Arrays.asList(paramVarArgs), false);
/*    */   }
/*    */   
/*    */   public ItemBuilder copy() {
/* 48 */     return new ItemBuilder(this.item, this.name, this.lore, this.glow);
/*    */   }
/*    */   
/*    */   public void addLore(ItemStack paramItemStack) {
/* 52 */     if (paramItemStack.hasItemMeta() && paramItemStack.getItemMeta().hasLore())
/* 53 */       for (String str : paramItemStack.getItemMeta().getLore())
/* 54 */         addLore(str);  
/*    */   }
/*    */   
/*    */   public void addLore(String paramString) {
/* 58 */     if (paramString != null)
/* 59 */       this.lore.add(paramString); 
/*    */   }
/*    */   
/*    */   public ItemStack buildHead(String paramString) {
/*    */     SkullMeta skullMeta;
/* 64 */     this.name = paramString;
/*    */     
/* 66 */     if (LegacyUtil.is1_13) {
/* 67 */       this.item = new ItemStack(LegacyUtil.HEAD);
/* 68 */       skullMeta = (SkullMeta)this.item.getItemMeta();
/* 69 */       skullMeta.setOwningPlayer(FactionWars.get().getServer().getOfflinePlayer(paramString));
/*    */     } else {
/* 71 */       this.item = new ItemStack(LegacyUtil.HEAD, 1, (short)3);
/* 72 */       skullMeta = (SkullMeta)this.item.getItemMeta();
/* 73 */       skullMeta.setOwner(paramString);
/*    */     } 
/* 75 */     this.item.setItemMeta((ItemMeta)skullMeta);
/* 76 */     return build();
/*    */   }
/*    */   
/*    */   public ItemStack build() {
/* 80 */     ItemMeta itemMeta = this.item.getItemMeta();
/* 81 */     if (itemMeta != null) {
/* 82 */       if (this.name != null) {
/* 83 */         itemMeta.setDisplayName(this.name);
/*    */       }
/* 85 */       if (this.lore != null)
/* 86 */         itemMeta.setLore(this.lore); 
/* 87 */       if (this.glow && !LegacyUtil.is1_7)
/*    */         try {
/* 89 */           itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
/* 90 */         } catch (Exception exception) {} 
/* 91 */       this.item.setItemMeta(itemMeta);
/* 92 */       if (this.glow)
/* 93 */         this.item.addUnsafeEnchantment(Enchantment.LUCK, 1); 
/*    */     } 
/* 95 */     return this.item;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */