/*     */ package io.github.guipenedo.factionwars.menus.generic;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*     */ import io.github.guipenedo.factionwars.helpers.ReflectionUtil;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class AnvilGUI
/*     */ {
/*     */   private Player player;
/*     */   private String title;
/*     */   private static Class<?> BlockPosition;
/*     */   private static Class<?> PacketPlayOutOpenWindow;
/*     */   
/*     */   public String getTitle() {
/*  30 */     return this.title;
/*     */   }
/*     */   private static Class<?> ContainerAnvil; private static Class<?> ChatMessage; private static Class<?> EntityHuman; private Class<?> ContainerAccess; private Class<?> Containers;
/*     */   public void setTitle(String paramString) {
/*  34 */     this.title = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();
/*     */   private Inventory inv;
/*     */   private Listener listener;
/*     */   
/*     */   private void loadClasses() {
/*  50 */     BlockPosition = ReflectionUtil.getNMSClass("BlockPosition");
/*  51 */     PacketPlayOutOpenWindow = ReflectionUtil.getNMSClass("PacketPlayOutOpenWindow");
/*  52 */     ContainerAnvil = ReflectionUtil.getNMSClass("ContainerAnvil");
/*  53 */     EntityHuman = ReflectionUtil.getNMSClass("EntityHuman");
/*  54 */     ChatMessage = ReflectionUtil.getNMSClass("ChatMessage");
/*     */     
/*  56 */     if (LegacyUtil.is1_14()) {
/*  57 */       this.ContainerAccess = ReflectionUtil.getNMSClass("ContainerAccess");
/*  58 */       this.Containers = ReflectionUtil.getNMSClass("Containers");
/*     */     } 
/*     */   }
/*     */   
/*     */   public AnvilGUI(final Player player, final AnvilClickEventHandler handler, String paramString) {
/*  63 */     loadClasses();
/*  64 */     this.player = player;
/*  65 */     this.title = paramString;
/*     */     
/*  67 */     this.listener = new Listener() {
/*     */         @EventHandler
/*     */         public void onInventoryClick(InventoryClickEvent param1InventoryClickEvent) {
/*  70 */           if (param1InventoryClickEvent.getWhoClicked() instanceof Player)
/*     */           {
/*  72 */             if (param1InventoryClickEvent.getInventory().equals(AnvilGUI.this.inv)) {
/*  73 */               param1InventoryClickEvent.setCancelled(true);
/*     */               
/*  75 */               ItemStack itemStack = param1InventoryClickEvent.getCurrentItem();
/*  76 */               int i = param1InventoryClickEvent.getRawSlot();
/*  77 */               String str = "";
/*     */               
/*  79 */               if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta() != null) {
/*  80 */                 ItemMeta itemMeta = itemStack.getItemMeta();
/*     */                 
/*  82 */                 if (itemMeta.hasDisplayName()) {
/*  83 */                   str = itemMeta.getDisplayName();
/*     */                 }
/*     */               } 
/*  86 */               AnvilGUI.AnvilClickEvent anvilClickEvent = new AnvilGUI.AnvilClickEvent(AnvilGUI.AnvilSlot.bySlot(i), str);
/*     */               
/*  88 */               handler.onAnvilClick(anvilClickEvent);
/*     */               
/*  90 */               if (anvilClickEvent.getWillClose()) {
/*  91 */                 param1InventoryClickEvent.getWhoClicked().closeInventory();
/*     */               }
/*  93 */               if (anvilClickEvent.getWillDestroy())
/*  94 */                 AnvilGUI.this.destroy(); 
/*     */             } 
/*     */           }
/*     */         }
/*     */         
/*     */         @EventHandler
/*     */         public void onInventoryClose(InventoryCloseEvent param1InventoryCloseEvent) {
/* 101 */           if (param1InventoryCloseEvent.getPlayer() instanceof Player) {
/* 102 */             Inventory inventory = param1InventoryCloseEvent.getInventory();
/* 103 */             if (inventory.equals(AnvilGUI.this.inv)) {
/* 104 */               player.setLevel(player.getLevel() - 1);
/* 105 */               inventory.clear();
/* 106 */               AnvilGUI.this.destroy();
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         @EventHandler
/*     */         public void onPlayerQuit(PlayerQuitEvent param1PlayerQuitEvent) {
/* 113 */           if (param1PlayerQuitEvent.getPlayer().equals(AnvilGUI.this.getPlayer())) {
/* 114 */             player.setLevel(player.getLevel() - 1);
/* 115 */             AnvilGUI.this.destroy();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 120 */     Bukkit.getPluginManager().registerEvents(this.listener, (Plugin)FactionWars.get());
/*     */   }
/*     */   
/*     */   private Player getPlayer() {
/* 124 */     return this.player;
/*     */   }
/*     */   
/*     */   public void setSlot(AnvilSlot paramAnvilSlot, ItemStack paramItemStack) {
/* 128 */     this.items.put(paramAnvilSlot, paramItemStack);
/*     */   }
/*     */   
/*     */   public void open() {
/* 132 */     this.player.setLevel(this.player.getLevel() + 1);
/*     */     try {
/*     */       Object object2, object6;
/* 135 */       Object object1 = ReflectionUtil.getHandle(this.player);
/*     */ 
/*     */ 
/*     */       
/* 139 */       int i = ((Integer)ReflectionUtil.invokeMethod("nextContainerCounter", object1)).intValue();
/*     */       
/* 141 */       if (LegacyUtil.is1_14()) {
/* 142 */         Method method1 = ReflectionUtil.getMethod(this.ContainerAccess, "at", new Class[] { ReflectionUtil.getNMSClass("World"), BlockPosition });
/* 143 */         object2 = ContainerAnvil.getConstructor(new Class[] { int.class, ReflectionUtil.getNMSClass("PlayerInventory"), this.ContainerAccess }).newInstance(new Object[] { Integer.valueOf(i), ReflectionUtil.getPlayerField(this.player, "inventory"), method1.invoke(this.ContainerAccess, new Object[] { ReflectionUtil.getPlayerField(this.player, "world"), BlockPosition.getConstructor(new Class[] { int.class, int.class, int.class }).newInstance(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) }) }) });
/*     */       } else {
/* 145 */         object2 = ContainerAnvil.getConstructor(new Class[] { ReflectionUtil.getNMSClass("PlayerInventory"), ReflectionUtil.getNMSClass("World"), BlockPosition, EntityHuman }).newInstance(new Object[] { ReflectionUtil.getPlayerField(this.player, "inventory"), ReflectionUtil.getPlayerField(this.player, "world"), BlockPosition.getConstructor(new Class[] { int.class, int.class, int.class }).newInstance(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) }), object1 });
/* 146 */       }  ReflectionUtil.getField(ReflectionUtil.getNMSClass("Container"), "checkReachable").set(object2, Boolean.valueOf(false));
/*     */ 
/*     */       
/* 149 */       Object object3 = ReflectionUtil.invokeMethod("getBukkitView", object2);
/* 150 */       this.inv = (Inventory)ReflectionUtil.invokeMethod("getTopInventory", object3);
/*     */       
/* 152 */       for (AnvilSlot anvilSlot : this.items.keySet()) {
/* 153 */         this.inv.setItem(anvilSlot.getSlot(), this.items.get(anvilSlot));
/*     */       }
/*     */       
/* 156 */       Object object4 = ChatMessage.getConstructor(new Class[] { String.class, Object[].class }).newInstance(new Object[] { getTitle(), new Object[0] });
/* 157 */       Object object5 = ReflectionUtil.getPlayerField(this.player, "playerConnection");
/*     */       
/* 159 */       if (LegacyUtil.is1_14()) {
/* 160 */         object6 = PacketPlayOutOpenWindow.getConstructor(new Class[] { int.class, this.Containers, ReflectionUtil.getNMSClass("IChatBaseComponent") }).newInstance(new Object[] { Integer.valueOf(i), ReflectionUtil.getField(this.Containers, "ANVIL").get(this.Containers), object4 });
/*     */       } else {
/* 162 */         object6 = PacketPlayOutOpenWindow.getConstructor(new Class[] { int.class, String.class, ReflectionUtil.getNMSClass("IChatBaseComponent"), int.class }).newInstance(new Object[] { Integer.valueOf(i), "minecraft:anvil", object4, Integer.valueOf(0) });
/*     */       } 
/*     */       
/* 165 */       Method method = ReflectionUtil.getMethod(object5.getClass(), "sendPacket", new Class[] { ReflectionUtil.getNMSClass("Packet") });
/* 166 */       method.invoke(object5, new Object[] { object6 });
/*     */ 
/*     */       
/* 169 */       Field field = ReflectionUtil.getField(EntityHuman, "activeContainer");
/* 170 */       if (field != null) {
/* 171 */         field.set(object1, object2);
/*     */ 
/*     */         
/* 174 */         if (!LegacyUtil.is1_14()) {
/* 175 */           ReflectionUtil.getField(ReflectionUtil.getNMSClass("Container"), "windowId").set(field.get(object1), Integer.valueOf(i));
/*     */         }
/*     */         
/* 178 */         ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("Container"), "addSlotListener", new Class[] { ReflectionUtil.getNMSClass("ICrafting") }).invoke(field.get(object1), new Object[] { object1 });
/*     */       } 
/* 180 */     } catch (Exception exception) {
/* 181 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroy() {
/* 186 */     this.player = null;
/* 187 */     this.items = null;
/* 188 */     this.title = null;
/*     */     
/* 190 */     HandlerList.unregisterAll(this.listener);
/*     */     
/* 192 */     this.listener = null;
/*     */   }
/*     */   
/*     */   public enum AnvilSlot {
/* 196 */     INPUT_LEFT(0),
/* 197 */     INPUT_RIGHT(1),
/* 198 */     OUTPUT(2);
/*     */     
/*     */     private final int slot;
/*     */     
/*     */     AnvilSlot(int param1Int1) {
/* 203 */       this.slot = param1Int1;
/*     */     }
/*     */     
/*     */     static AnvilSlot bySlot(int param1Int) {
/* 207 */       for (AnvilSlot anvilSlot : values()) {
/* 208 */         if (anvilSlot.getSlot() == param1Int)
/* 209 */           return anvilSlot; 
/*     */       } 
/* 211 */       return null;
/*     */     }
/*     */     
/*     */     int getSlot() {
/* 215 */       return this.slot;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface AnvilClickEventHandler
/*     */   {
/*     */     void onAnvilClick(AnvilGUI.AnvilClickEvent param1AnvilClickEvent);
/*     */   }
/*     */   
/*     */   public class AnvilClickEvent
/*     */   {
/*     */     private final AnvilGUI.AnvilSlot slot;
/*     */     private final String name;
/*     */     private boolean close = true;
/*     */     private boolean destroy = true;
/*     */     
/*     */     AnvilClickEvent(AnvilGUI.AnvilSlot param1AnvilSlot, String param1String) {
/* 232 */       this.slot = param1AnvilSlot;
/* 233 */       this.name = param1String;
/*     */     }
/*     */     
/*     */     public AnvilGUI.AnvilSlot getSlot() {
/* 237 */       return this.slot;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 241 */       return this.name;
/*     */     }
/*     */     
/*     */     boolean getWillClose() {
/* 245 */       return this.close;
/*     */     }
/*     */     
/*     */     public void setWillClose(boolean param1Boolean) {
/* 249 */       this.close = param1Boolean;
/*     */     }
/*     */     
/*     */     boolean getWillDestroy() {
/* 253 */       return this.destroy;
/*     */     }
/*     */     
/*     */     public void setWillDestroy(boolean param1Boolean) {
/* 257 */       this.destroy = param1Boolean;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\generic\AnvilGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */