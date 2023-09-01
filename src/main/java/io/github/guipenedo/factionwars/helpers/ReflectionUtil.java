/*     */ package io.github.guipenedo.factionwars.helpers;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionUtil
/*     */ {
/*     */   private static String versionString;
/*  23 */   private static Map<String, Class<?>> loadedNMSClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private static Map<String, Class<?>> loadedOBCClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static Map<Class<?>, Map<String, Method>> loadedMethods = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static Map<Class<?>, Map<String, Field>> loadedFields = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/*  46 */     if (versionString == null) {
/*  47 */       String str = Bukkit.getServer().getClass().getPackage().getName();
/*  48 */       versionString = str.substring(str.lastIndexOf('.') + 1) + ".";
/*     */     } 
/*     */     
/*  51 */     return versionString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getNMSClass(String paramString) {
/*     */     Class<?> clazz;
/*  61 */     if (loadedNMSClasses.containsKey(paramString)) {
/*  62 */       return loadedNMSClasses.get(paramString);
/*     */     }
/*     */     
/*  65 */     String str = "net.minecraft.server." + getVersion() + paramString;
/*     */ 
/*     */     
/*     */     try {
/*  69 */       clazz = Class.forName(str);
/*  70 */     } catch (Throwable throwable) {
/*  71 */       throwable.printStackTrace();
/*  72 */       return loadedNMSClasses.put(paramString, null);
/*     */     } 
/*     */     
/*  75 */     loadedNMSClasses.put(paramString, clazz);
/*  76 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Class<?> getOBCClass(String paramString) {
/*     */     Class<?> clazz;
/*  86 */     if (loadedOBCClasses.containsKey(paramString)) {
/*  87 */       return loadedOBCClasses.get(paramString);
/*     */     }
/*     */     
/*  90 */     String str = "org.bukkit.craftbukkit." + getVersion() + paramString;
/*     */ 
/*     */     
/*     */     try {
/*  94 */       clazz = Class.forName(str);
/*  95 */     } catch (Throwable throwable) {
/*  96 */       throwable.printStackTrace();
/*  97 */       loadedOBCClasses.put(paramString, null);
/*  98 */       return null;
/*     */     } 
/*     */     
/* 101 */     loadedOBCClasses.put(paramString, clazz);
/* 102 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getConnection(Player paramPlayer) {
/* 112 */     Method method = getMethod(paramPlayer.getClass(), "getHandle", new Class[0]);
/*     */     
/* 114 */     if (method != null) {
/*     */       try {
/* 116 */         Object object = method.invoke(paramPlayer, new Object[0]);
/* 117 */         Field field = getField(object.getClass(), "playerConnection");
/* 118 */         return field.get(object);
/* 119 */       } catch (Exception exception) {
/* 120 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Constructor<?> getConstructor(Class<?> paramClass, Class<?>... paramVarArgs) {
/*     */     try {
/* 136 */       return paramClass.getConstructor(paramVarArgs);
/* 137 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 138 */       return null;
/*     */     } 
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
/*     */   public static Method getMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
/* 151 */     if (!loadedMethods.containsKey(paramClass)) {
/* 152 */       loadedMethods.put(paramClass, new HashMap<>());
/*     */     }
/*     */     
/* 155 */     Map<String, Method> map = loadedMethods.get(paramClass);
/*     */     
/* 157 */     if (map.containsKey(paramString)) {
/* 158 */       return (Method)map.get(paramString);
/*     */     }
/*     */     
/*     */     try {
/* 162 */       Method method = paramClass.getMethod(paramString, paramVarArgs);
/* 163 */       map.put(paramString, method);
/* 164 */       loadedMethods.put(paramClass, map);
/* 165 */       return method;
/* 166 */     } catch (Exception exception) {
/* 167 */       exception.printStackTrace();
/* 168 */       map.put(paramString, null);
/* 169 */       loadedMethods.put(paramClass, map);
/* 170 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object invokeMethod(String paramString, Object paramObject) {
/*     */     try {
/* 176 */       return getMethod(paramObject.getClass(), paramString, new Class[0]).invoke(paramObject, new Object[0]);
/* 177 */     } catch (Exception exception) {
/* 178 */       exception.printStackTrace();
/* 179 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object invokeMethodWithArgs(String paramString, Object paramObject, Object... paramVarArgs) {
/*     */     try {
/* 185 */       Class[] arrayOfClass = new Class[paramVarArgs.length];
/* 186 */       for (byte b = 0; b < paramVarArgs.length; b++)
/* 187 */         arrayOfClass[b] = paramVarArgs[b].getClass(); 
/* 188 */       return getMethod(paramObject.getClass(), paramString, arrayOfClass).invoke(paramObject, paramVarArgs);
/* 189 */     } catch (Exception exception) {
/* 190 */       exception.printStackTrace();
/* 191 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getHandle(Object paramObject) {
/*     */     try {
/* 197 */       return getMethod(paramObject.getClass(), "getHandle", new Class[0]).invoke(paramObject, new Object[0]);
/* 198 */     } catch (Exception exception) {
/* 199 */       exception.printStackTrace();
/* 200 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getPlayerField(Player paramPlayer, String paramString) {
/* 205 */     Object object = getHandle(paramPlayer);
/* 206 */     Field field = object.getClass().getField(paramString);
/* 207 */     return field.get(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getField(Class<?> paramClass, String paramString) {
/* 218 */     if (!loadedFields.containsKey(paramClass)) {
/* 219 */       loadedFields.put(paramClass, new HashMap<>());
/*     */     }
/*     */     
/* 222 */     Map<String, Field> map = loadedFields.get(paramClass);
/*     */     
/* 224 */     if (map.containsKey(paramString) || map.containsKey("12345")) {
/* 225 */       return (Field)map.get(paramString);
/*     */     }
/*     */     
/*     */     try {
/* 229 */       Field field = paramClass.getField(paramString);
/* 230 */       map.put(paramString, field);
/* 231 */       loadedFields.put(paramClass, map);
/* 232 */       return field;
/* 233 */     } catch (Exception exception) {
/* 234 */       exception.printStackTrace();
/* 235 */       map.put(paramString, null);
/* 236 */       loadedFields.put(paramClass, map);
/* 237 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\ReflectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */