/*    */ package io.github.guipenedo.factionwars.helpers;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TitleUpdater
/*    */ {
/*    */   private static Method getHandle;
/*    */   private static Method sendPacket;
/*    */   private static Field activeContainerField;
/*    */   private static Field windowIdField;
/*    */   
/*    */   static {
/*    */     try {
/* 19 */       getHandle = ReflectionUtil.getMethod(ReflectionUtil.getOBCClass("entity.CraftPlayer"), "getHandle", new Class[0]);
/* 20 */       chatMessageConstructor = ReflectionUtil.getNMSClass("ChatMessage").getConstructor(new Class[] { String.class, Object[].class });
/* 21 */       Class<?> clazz = ReflectionUtil.getNMSClass("EntityPlayer");
/* 22 */       activeContainerField = ReflectionUtil.getField(clazz, "activeContainer");
/* 23 */       windowIdField = ReflectionUtil.getField(ReflectionUtil.getNMSClass("Container"), "windowId");
/* 24 */       playerConnectionField = ReflectionUtil.getField(clazz, "playerConnection");
/* 25 */       if (LegacyUtil.is1_14()) {
/* 26 */         Containers = ReflectionUtil.getNMSClass("Containers");
/* 27 */         packetPlayOutOpenWindowConstructor = ReflectionUtil.getConstructor(ReflectionUtil.getNMSClass("PacketPlayOutOpenWindow"), new Class[] { int.class, Containers, ReflectionUtil.getNMSClass("IChatBaseComponent") });
/*    */       } else {
/* 29 */         packetPlayOutOpenWindowConstructor = ReflectionUtil.getConstructor(ReflectionUtil.getNMSClass("PacketPlayOutOpenWindow"), new Class[] { int.class, String.class, ReflectionUtil.getNMSClass("IChatBaseComponent"), int.class });
/* 30 */       }  sendPacket = ReflectionUtil.getNMSClass("PlayerConnection").getMethod("sendPacket", new Class[] { ReflectionUtil.getNMSClass("Packet") });
/* 31 */     } catch (SecurityException|NoSuchMethodException securityException) {
/* 32 */       securityException.printStackTrace();
/*    */     } 
/*    */   }
/*    */   private static Field playerConnectionField; private static Constructor<?> chatMessageConstructor; private static Constructor<?> packetPlayOutOpenWindowConstructor; private static Class<?> Containers;
/*    */   public static void update(Player paramPlayer, String paramString) {
/*    */     try {
/* 38 */       Object object5, object1 = getHandle.invoke(paramPlayer, new Object[0]);
/* 39 */       Object object2 = chatMessageConstructor.newInstance(new Object[] { paramString, new Object[0] });
/* 40 */       Object object3 = activeContainerField.get(object1);
/* 41 */       Object object4 = windowIdField.get(object3);
/*    */ 
/*    */       
/* 44 */       if (LegacyUtil.is1_14()) {
/* 45 */         object5 = packetPlayOutOpenWindowConstructor.newInstance(new Object[] { object4, ReflectionUtil.getField(Containers, "GENERIC_9X6").get(Containers), object2 });
/*    */       } else {
/* 47 */         object5 = packetPlayOutOpenWindowConstructor.newInstance(new Object[] { object4, "minecraft:chest", object2, Integer.valueOf(paramPlayer.getOpenInventory().getTopInventory().getSize()) });
/* 48 */       }  Object object6 = playerConnectionField.get(object1);
/* 49 */       sendPacket.invoke(object6, new Object[] { object5 });
/* 50 */     } catch (IllegalArgumentException|IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException illegalArgumentException) {
/* 51 */       illegalArgumentException.printStackTrace();
/*    */     } 
/* 53 */     paramPlayer.updateInventory();
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\TitleUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */