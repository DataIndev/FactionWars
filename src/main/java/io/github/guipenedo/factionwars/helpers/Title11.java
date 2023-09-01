/*     */ package io.github.guipenedo.factionwars.helpers;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Title11
/*     */ {
/*  20 */   private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<>();
/*     */   
/*     */   private static Class<?> packetTitle;
/*     */   
/*     */   private static Class<?> packetActions;
/*     */   
/*     */   private static Class<?> nmsChatSerializer;
/*     */   
/*     */   private static Class<?> chatBaseComponent;
/*     */   private static Field playerConnection;
/*     */   private static Method sendPacket;
/*     */   private static Method methodPlayerGetHandle;
/*  32 */   private String title = "";
/*  33 */   private ChatColor titleColor = ChatColor.WHITE;
/*     */   
/*  35 */   private String subtitle = "";
/*  36 */   private ChatColor subtitleColor = ChatColor.WHITE;
/*     */   
/*  38 */   private int fadeInTime = -1;
/*  39 */   private int stayTime = -1;
/*  40 */   private int fadeOutTime = -1;
/*     */   private boolean ticks = false;
/*     */   
/*     */   public Title11() {
/*  44 */     loadClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Title11(String paramString) {
/*  53 */     this.title = paramString;
/*  54 */     loadClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Title11(String paramString1, String paramString2) {
/*  64 */     this.title = paramString1;
/*  65 */     this.subtitle = paramString2;
/*  66 */     loadClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Title11(Title11 paramTitle11) {
/*  76 */     this.title = paramTitle11.getTitle();
/*  77 */     this.subtitle = paramTitle11.getSubtitle();
/*  78 */     this.titleColor = paramTitle11.getTitleColor();
/*  79 */     this.subtitleColor = paramTitle11.getSubtitleColor();
/*  80 */     this.fadeInTime = paramTitle11.getFadeInTime();
/*  81 */     this.fadeOutTime = paramTitle11.getFadeOutTime();
/*  82 */     this.stayTime = paramTitle11.getStayTime();
/*  83 */     this.ticks = paramTitle11.isTicks();
/*  84 */     loadClasses();
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
/*     */ 
/*     */   
/*     */   public Title11(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
/*  98 */     this.title = paramString1;
/*  99 */     this.subtitle = paramString2;
/* 100 */     this.fadeInTime = paramInt1;
/* 101 */     this.stayTime = paramInt2;
/* 102 */     this.fadeOutTime = paramInt3;
/* 103 */     loadClasses();
/*     */   }
/*     */   
/*     */   private static boolean equalsTypeArray(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 107 */     if (paramArrayOfClass1.length != paramArrayOfClass2.length)
/* 108 */       return false; 
/* 109 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 110 */       if (!paramArrayOfClass1[b].equals(paramArrayOfClass2[b]) && !paramArrayOfClass1[b].isAssignableFrom(paramArrayOfClass2[b]))
/* 111 */         return false; 
/* 112 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadClasses() {
/* 119 */     if (packetTitle == null) {
/* 120 */       packetTitle = getNMSClass("PacketPlayOutTitle");
/* 121 */       packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
/* 122 */       chatBaseComponent = getNMSClass("IChatBaseComponent");
/* 123 */       nmsChatSerializer = getNMSClass("ChatComponentText");
/*     */       
/* 125 */       Class<?> clazz1 = getNMSClass("EntityPlayer");
/* 126 */       Class<?> clazz2 = getNMSClass("PlayerConnection");
/* 127 */       playerConnection = getField(clazz1);
/* 128 */       sendPacket = getMethod(clazz2, new Class[0]);
/* 129 */       Class<?> clazz3 = getOBCClass();
/* 130 */       methodPlayerGetHandle = getMethod("getHandle", clazz3, new Class[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTitle() {
/* 140 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {
/* 149 */     this.title = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSubtitle() {
/* 158 */     return this.subtitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitle(String paramString) {
/* 167 */     this.subtitle = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimingsToTicks() {
/* 174 */     this.ticks = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimingsToSeconds() {
/* 181 */     this.ticks = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 458
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial resetTitle : (Lorg/bukkit/entity/Player;)V
/*     */     //   11: aload_0
/*     */     //   12: aload_1
/*     */     //   13: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   16: astore_2
/*     */     //   17: getstatic io/github/guipenedo/factionwars/helpers/Title11.playerConnection : Ljava/lang/reflect/Field;
/*     */     //   20: aload_2
/*     */     //   21: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: astore_3
/*     */     //   25: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   28: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   31: astore #4
/*     */     //   33: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   36: iconst_5
/*     */     //   37: anewarray java/lang/Class
/*     */     //   40: dup
/*     */     //   41: iconst_0
/*     */     //   42: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   45: aastore
/*     */     //   46: dup
/*     */     //   47: iconst_1
/*     */     //   48: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   51: aastore
/*     */     //   52: dup
/*     */     //   53: iconst_2
/*     */     //   54: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   57: aastore
/*     */     //   58: dup
/*     */     //   59: iconst_3
/*     */     //   60: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   63: aastore
/*     */     //   64: dup
/*     */     //   65: iconst_4
/*     */     //   66: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   69: aastore
/*     */     //   70: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   73: iconst_5
/*     */     //   74: anewarray java/lang/Object
/*     */     //   77: dup
/*     */     //   78: iconst_0
/*     */     //   79: aload #4
/*     */     //   81: iconst_3
/*     */     //   82: aaload
/*     */     //   83: aastore
/*     */     //   84: dup
/*     */     //   85: iconst_1
/*     */     //   86: aconst_null
/*     */     //   87: aastore
/*     */     //   88: dup
/*     */     //   89: iconst_2
/*     */     //   90: aload_0
/*     */     //   91: getfield fadeInTime : I
/*     */     //   94: aload_0
/*     */     //   95: getfield ticks : Z
/*     */     //   98: ifeq -> 105
/*     */     //   101: iconst_1
/*     */     //   102: goto -> 107
/*     */     //   105: bipush #20
/*     */     //   107: imul
/*     */     //   108: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   111: aastore
/*     */     //   112: dup
/*     */     //   113: iconst_3
/*     */     //   114: aload_0
/*     */     //   115: getfield stayTime : I
/*     */     //   118: aload_0
/*     */     //   119: getfield ticks : Z
/*     */     //   122: ifeq -> 129
/*     */     //   125: iconst_1
/*     */     //   126: goto -> 131
/*     */     //   129: bipush #20
/*     */     //   131: imul
/*     */     //   132: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   135: aastore
/*     */     //   136: dup
/*     */     //   137: iconst_4
/*     */     //   138: aload_0
/*     */     //   139: getfield fadeOutTime : I
/*     */     //   142: aload_0
/*     */     //   143: getfield ticks : Z
/*     */     //   146: ifeq -> 153
/*     */     //   149: iconst_1
/*     */     //   150: goto -> 155
/*     */     //   153: bipush #20
/*     */     //   155: imul
/*     */     //   156: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   159: aastore
/*     */     //   160: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   163: astore #5
/*     */     //   165: aload_0
/*     */     //   166: getfield fadeInTime : I
/*     */     //   169: iconst_m1
/*     */     //   170: if_icmpeq -> 206
/*     */     //   173: aload_0
/*     */     //   174: getfield fadeOutTime : I
/*     */     //   177: iconst_m1
/*     */     //   178: if_icmpeq -> 206
/*     */     //   181: aload_0
/*     */     //   182: getfield stayTime : I
/*     */     //   185: iconst_m1
/*     */     //   186: if_icmpeq -> 206
/*     */     //   189: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   192: aload_3
/*     */     //   193: iconst_1
/*     */     //   194: anewarray java/lang/Object
/*     */     //   197: dup
/*     */     //   198: iconst_0
/*     */     //   199: aload #5
/*     */     //   201: aastore
/*     */     //   202: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   205: pop
/*     */     //   206: aload_0
/*     */     //   207: getfield subtitle : Ljava/lang/String;
/*     */     //   210: ldc ''
/*     */     //   212: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   215: ifne -> 334
/*     */     //   218: getstatic io/github/guipenedo/factionwars/helpers/Title11.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   221: iconst_1
/*     */     //   222: anewarray java/lang/Class
/*     */     //   225: dup
/*     */     //   226: iconst_0
/*     */     //   227: ldc java/lang/String
/*     */     //   229: aastore
/*     */     //   230: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   233: iconst_1
/*     */     //   234: anewarray java/lang/Object
/*     */     //   237: dup
/*     */     //   238: iconst_0
/*     */     //   239: new java/lang/StringBuilder
/*     */     //   242: dup
/*     */     //   243: invokespecial <init> : ()V
/*     */     //   246: aload_0
/*     */     //   247: getfield subtitleColor : Lorg/bukkit/ChatColor;
/*     */     //   250: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   253: bipush #38
/*     */     //   255: aload_0
/*     */     //   256: getfield subtitle : Ljava/lang/String;
/*     */     //   259: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   265: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   268: aastore
/*     */     //   269: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   272: astore #6
/*     */     //   274: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   277: iconst_2
/*     */     //   278: anewarray java/lang/Class
/*     */     //   281: dup
/*     */     //   282: iconst_0
/*     */     //   283: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   286: aastore
/*     */     //   287: dup
/*     */     //   288: iconst_1
/*     */     //   289: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   292: aastore
/*     */     //   293: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   296: iconst_2
/*     */     //   297: anewarray java/lang/Object
/*     */     //   300: dup
/*     */     //   301: iconst_0
/*     */     //   302: aload #4
/*     */     //   304: iconst_1
/*     */     //   305: aaload
/*     */     //   306: aastore
/*     */     //   307: dup
/*     */     //   308: iconst_1
/*     */     //   309: aload #6
/*     */     //   311: aastore
/*     */     //   312: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   315: astore #5
/*     */     //   317: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   320: aload_3
/*     */     //   321: iconst_1
/*     */     //   322: anewarray java/lang/Object
/*     */     //   325: dup
/*     */     //   326: iconst_0
/*     */     //   327: aload #5
/*     */     //   329: aastore
/*     */     //   330: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   333: pop
/*     */     //   334: getstatic io/github/guipenedo/factionwars/helpers/Title11.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   337: iconst_1
/*     */     //   338: anewarray java/lang/Class
/*     */     //   341: dup
/*     */     //   342: iconst_0
/*     */     //   343: ldc java/lang/String
/*     */     //   345: aastore
/*     */     //   346: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   349: iconst_1
/*     */     //   350: anewarray java/lang/Object
/*     */     //   353: dup
/*     */     //   354: iconst_0
/*     */     //   355: new java/lang/StringBuilder
/*     */     //   358: dup
/*     */     //   359: invokespecial <init> : ()V
/*     */     //   362: aload_0
/*     */     //   363: getfield titleColor : Lorg/bukkit/ChatColor;
/*     */     //   366: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   369: bipush #38
/*     */     //   371: aload_0
/*     */     //   372: getfield title : Ljava/lang/String;
/*     */     //   375: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   381: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   384: aastore
/*     */     //   385: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   388: astore #6
/*     */     //   390: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   393: iconst_2
/*     */     //   394: anewarray java/lang/Class
/*     */     //   397: dup
/*     */     //   398: iconst_0
/*     */     //   399: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   402: aastore
/*     */     //   403: dup
/*     */     //   404: iconst_1
/*     */     //   405: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   408: aastore
/*     */     //   409: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   412: iconst_2
/*     */     //   413: anewarray java/lang/Object
/*     */     //   416: dup
/*     */     //   417: iconst_0
/*     */     //   418: aload #4
/*     */     //   420: iconst_0
/*     */     //   421: aaload
/*     */     //   422: aastore
/*     */     //   423: dup
/*     */     //   424: iconst_1
/*     */     //   425: aload #6
/*     */     //   427: aastore
/*     */     //   428: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   431: astore #5
/*     */     //   433: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   436: aload_3
/*     */     //   437: iconst_1
/*     */     //   438: anewarray java/lang/Object
/*     */     //   441: dup
/*     */     //   442: iconst_0
/*     */     //   443: aload #5
/*     */     //   445: aastore
/*     */     //   446: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   449: pop
/*     */     //   450: goto -> 458
/*     */     //   453: astore_2
/*     */     //   454: aload_2
/*     */     //   455: invokevirtual printStackTrace : ()V
/*     */     //   458: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #190	-> 0
/*     */     //   #192	-> 6
/*     */     //   #195	-> 11
/*     */     //   #196	-> 17
/*     */     //   #197	-> 25
/*     */     //   #198	-> 33
/*     */     //   #201	-> 108
/*     */     //   #202	-> 132
/*     */     //   #203	-> 156
/*     */     //   #200	-> 160
/*     */     //   #205	-> 165
/*     */     //   #206	-> 189
/*     */     //   #209	-> 206
/*     */     //   #211	-> 218
/*     */     //   #213	-> 259
/*     */     //   #212	-> 269
/*     */     //   #215	-> 274
/*     */     //   #216	-> 312
/*     */     //   #218	-> 317
/*     */     //   #222	-> 334
/*     */     //   #224	-> 375
/*     */     //   #223	-> 385
/*     */     //   #225	-> 390
/*     */     //   #226	-> 428
/*     */     //   #227	-> 433
/*     */     //   #230	-> 450
/*     */     //   #228	-> 453
/*     */     //   #229	-> 454
/*     */     //   #232	-> 458
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   11	450	453	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimes(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 209
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: getstatic io/github/guipenedo/factionwars/helpers/Title11.playerConnection : Ljava/lang/reflect/Field;
/*     */     //   15: aload_2
/*     */     //   16: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   19: astore_3
/*     */     //   20: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   23: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   26: astore #4
/*     */     //   28: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   31: iconst_5
/*     */     //   32: anewarray java/lang/Class
/*     */     //   35: dup
/*     */     //   36: iconst_0
/*     */     //   37: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   40: aastore
/*     */     //   41: dup
/*     */     //   42: iconst_1
/*     */     //   43: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   46: aastore
/*     */     //   47: dup
/*     */     //   48: iconst_2
/*     */     //   49: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   52: aastore
/*     */     //   53: dup
/*     */     //   54: iconst_3
/*     */     //   55: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   58: aastore
/*     */     //   59: dup
/*     */     //   60: iconst_4
/*     */     //   61: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   64: aastore
/*     */     //   65: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   68: iconst_5
/*     */     //   69: anewarray java/lang/Object
/*     */     //   72: dup
/*     */     //   73: iconst_0
/*     */     //   74: aload #4
/*     */     //   76: iconst_3
/*     */     //   77: aaload
/*     */     //   78: aastore
/*     */     //   79: dup
/*     */     //   80: iconst_1
/*     */     //   81: aconst_null
/*     */     //   82: aastore
/*     */     //   83: dup
/*     */     //   84: iconst_2
/*     */     //   85: aload_0
/*     */     //   86: getfield fadeInTime : I
/*     */     //   89: aload_0
/*     */     //   90: getfield ticks : Z
/*     */     //   93: ifeq -> 100
/*     */     //   96: iconst_1
/*     */     //   97: goto -> 102
/*     */     //   100: bipush #20
/*     */     //   102: imul
/*     */     //   103: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   106: aastore
/*     */     //   107: dup
/*     */     //   108: iconst_3
/*     */     //   109: aload_0
/*     */     //   110: getfield stayTime : I
/*     */     //   113: aload_0
/*     */     //   114: getfield ticks : Z
/*     */     //   117: ifeq -> 124
/*     */     //   120: iconst_1
/*     */     //   121: goto -> 126
/*     */     //   124: bipush #20
/*     */     //   126: imul
/*     */     //   127: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   130: aastore
/*     */     //   131: dup
/*     */     //   132: iconst_4
/*     */     //   133: aload_0
/*     */     //   134: getfield fadeOutTime : I
/*     */     //   137: aload_0
/*     */     //   138: getfield ticks : Z
/*     */     //   141: ifeq -> 148
/*     */     //   144: iconst_1
/*     */     //   145: goto -> 150
/*     */     //   148: bipush #20
/*     */     //   150: imul
/*     */     //   151: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   154: aastore
/*     */     //   155: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   158: astore #5
/*     */     //   160: aload_0
/*     */     //   161: getfield fadeInTime : I
/*     */     //   164: iconst_m1
/*     */     //   165: if_icmpeq -> 201
/*     */     //   168: aload_0
/*     */     //   169: getfield fadeOutTime : I
/*     */     //   172: iconst_m1
/*     */     //   173: if_icmpeq -> 201
/*     */     //   176: aload_0
/*     */     //   177: getfield stayTime : I
/*     */     //   180: iconst_m1
/*     */     //   181: if_icmpeq -> 201
/*     */     //   184: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   187: aload_3
/*     */     //   188: iconst_1
/*     */     //   189: anewarray java/lang/Object
/*     */     //   192: dup
/*     */     //   193: iconst_0
/*     */     //   194: aload #5
/*     */     //   196: aastore
/*     */     //   197: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   200: pop
/*     */     //   201: goto -> 209
/*     */     //   204: astore_2
/*     */     //   205: aload_2
/*     */     //   206: invokevirtual printStackTrace : ()V
/*     */     //   209: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #235	-> 0
/*     */     //   #237	-> 6
/*     */     //   #238	-> 12
/*     */     //   #239	-> 20
/*     */     //   #240	-> 28
/*     */     //   #246	-> 103
/*     */     //   #248	-> 127
/*     */     //   #250	-> 151
/*     */     //   #243	-> 155
/*     */     //   #252	-> 160
/*     */     //   #254	-> 184
/*     */     //   #258	-> 201
/*     */     //   #256	-> 204
/*     */     //   #257	-> 205
/*     */     //   #260	-> 209
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	201	204	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 170
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: aload_0
/*     */     //   13: aload_2
/*     */     //   14: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   17: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   20: aload_2
/*     */     //   21: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: astore_3
/*     */     //   25: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   28: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   31: astore #4
/*     */     //   33: aload_0
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   38: iconst_0
/*     */     //   39: anewarray java/lang/Class
/*     */     //   42: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   45: astore #5
/*     */     //   47: getstatic io/github/guipenedo/factionwars/helpers/Title11.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   50: iconst_1
/*     */     //   51: anewarray java/lang/Class
/*     */     //   54: dup
/*     */     //   55: iconst_0
/*     */     //   56: ldc java/lang/String
/*     */     //   58: aastore
/*     */     //   59: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   62: iconst_1
/*     */     //   63: anewarray java/lang/Object
/*     */     //   66: dup
/*     */     //   67: iconst_0
/*     */     //   68: new java/lang/StringBuilder
/*     */     //   71: dup
/*     */     //   72: invokespecial <init> : ()V
/*     */     //   75: aload_0
/*     */     //   76: getfield titleColor : Lorg/bukkit/ChatColor;
/*     */     //   79: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   82: bipush #38
/*     */     //   84: aload_0
/*     */     //   85: getfield title : Ljava/lang/String;
/*     */     //   88: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   94: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   97: aastore
/*     */     //   98: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   101: astore #6
/*     */     //   103: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   106: iconst_2
/*     */     //   107: anewarray java/lang/Class
/*     */     //   110: dup
/*     */     //   111: iconst_0
/*     */     //   112: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   115: aastore
/*     */     //   116: dup
/*     */     //   117: iconst_1
/*     */     //   118: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   121: aastore
/*     */     //   122: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   125: iconst_2
/*     */     //   126: anewarray java/lang/Object
/*     */     //   129: dup
/*     */     //   130: iconst_0
/*     */     //   131: aload #4
/*     */     //   133: iconst_0
/*     */     //   134: aaload
/*     */     //   135: aastore
/*     */     //   136: dup
/*     */     //   137: iconst_1
/*     */     //   138: aload #6
/*     */     //   140: aastore
/*     */     //   141: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   144: astore #7
/*     */     //   146: aload #5
/*     */     //   148: aload_3
/*     */     //   149: iconst_1
/*     */     //   150: anewarray java/lang/Object
/*     */     //   153: dup
/*     */     //   154: iconst_0
/*     */     //   155: aload #7
/*     */     //   157: aastore
/*     */     //   158: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   161: pop
/*     */     //   162: goto -> 170
/*     */     //   165: astore_2
/*     */     //   166: aload_2
/*     */     //   167: invokevirtual printStackTrace : ()V
/*     */     //   170: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #263	-> 0
/*     */     //   #265	-> 6
/*     */     //   #266	-> 12
/*     */     //   #267	-> 21
/*     */     //   #268	-> 25
/*     */     //   #269	-> 33
/*     */     //   #271	-> 47
/*     */     //   #274	-> 88
/*     */     //   #273	-> 98
/*     */     //   #276	-> 103
/*     */     //   #277	-> 122
/*     */     //   #279	-> 141
/*     */     //   #281	-> 146
/*     */     //   #284	-> 162
/*     */     //   #282	-> 165
/*     */     //   #283	-> 166
/*     */     //   #286	-> 170
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	162	165	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateSubtitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 152
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: getstatic io/github/guipenedo/factionwars/helpers/Title11.playerConnection : Ljava/lang/reflect/Field;
/*     */     //   15: aload_2
/*     */     //   16: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   19: astore_3
/*     */     //   20: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   23: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   26: astore #4
/*     */     //   28: getstatic io/github/guipenedo/factionwars/helpers/Title11.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   31: iconst_1
/*     */     //   32: anewarray java/lang/Class
/*     */     //   35: dup
/*     */     //   36: iconst_0
/*     */     //   37: ldc java/lang/String
/*     */     //   39: aastore
/*     */     //   40: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   43: iconst_1
/*     */     //   44: anewarray java/lang/Object
/*     */     //   47: dup
/*     */     //   48: iconst_0
/*     */     //   49: new java/lang/StringBuilder
/*     */     //   52: dup
/*     */     //   53: invokespecial <init> : ()V
/*     */     //   56: aload_0
/*     */     //   57: getfield subtitleColor : Lorg/bukkit/ChatColor;
/*     */     //   60: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   63: bipush #38
/*     */     //   65: aload_0
/*     */     //   66: getfield subtitle : Ljava/lang/String;
/*     */     //   69: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   72: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   75: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   78: aastore
/*     */     //   79: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   82: astore #5
/*     */     //   84: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   87: iconst_2
/*     */     //   88: anewarray java/lang/Class
/*     */     //   91: dup
/*     */     //   92: iconst_0
/*     */     //   93: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   96: aastore
/*     */     //   97: dup
/*     */     //   98: iconst_1
/*     */     //   99: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   102: aastore
/*     */     //   103: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   106: iconst_2
/*     */     //   107: anewarray java/lang/Object
/*     */     //   110: dup
/*     */     //   111: iconst_0
/*     */     //   112: aload #4
/*     */     //   114: iconst_1
/*     */     //   115: aaload
/*     */     //   116: aastore
/*     */     //   117: dup
/*     */     //   118: iconst_1
/*     */     //   119: aload #5
/*     */     //   121: aastore
/*     */     //   122: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   125: astore #6
/*     */     //   127: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   130: aload_3
/*     */     //   131: iconst_1
/*     */     //   132: anewarray java/lang/Object
/*     */     //   135: dup
/*     */     //   136: iconst_0
/*     */     //   137: aload #6
/*     */     //   139: aastore
/*     */     //   140: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   143: pop
/*     */     //   144: goto -> 152
/*     */     //   147: astore_2
/*     */     //   148: aload_2
/*     */     //   149: invokevirtual printStackTrace : ()V
/*     */     //   152: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #289	-> 0
/*     */     //   #291	-> 6
/*     */     //   #292	-> 12
/*     */     //   #293	-> 20
/*     */     //   #294	-> 28
/*     */     //   #297	-> 69
/*     */     //   #296	-> 79
/*     */     //   #299	-> 84
/*     */     //   #300	-> 103
/*     */     //   #302	-> 122
/*     */     //   #304	-> 127
/*     */     //   #307	-> 144
/*     */     //   #305	-> 147
/*     */     //   #306	-> 148
/*     */     //   #309	-> 152
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	144	147	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcast() {
/* 315 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 316 */       send(player);
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
/*     */ 
/*     */   
/*     */   public void clearTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   5: astore_2
/*     */     //   6: getstatic io/github/guipenedo/factionwars/helpers/Title11.playerConnection : Ljava/lang/reflect/Field;
/*     */     //   9: aload_2
/*     */     //   10: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   13: astore_3
/*     */     //   14: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   17: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   20: astore #4
/*     */     //   22: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   25: iconst_2
/*     */     //   26: anewarray java/lang/Class
/*     */     //   29: dup
/*     */     //   30: iconst_0
/*     */     //   31: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   34: aastore
/*     */     //   35: dup
/*     */     //   36: iconst_1
/*     */     //   37: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   40: aastore
/*     */     //   41: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   44: iconst_2
/*     */     //   45: anewarray java/lang/Object
/*     */     //   48: dup
/*     */     //   49: iconst_0
/*     */     //   50: aload #4
/*     */     //   52: iconst_4
/*     */     //   53: aaload
/*     */     //   54: aastore
/*     */     //   55: dup
/*     */     //   56: iconst_1
/*     */     //   57: aconst_null
/*     */     //   58: aastore
/*     */     //   59: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   62: astore #5
/*     */     //   64: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   67: aload_3
/*     */     //   68: iconst_1
/*     */     //   69: anewarray java/lang/Object
/*     */     //   72: dup
/*     */     //   73: iconst_0
/*     */     //   74: aload #5
/*     */     //   76: aastore
/*     */     //   77: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   80: pop
/*     */     //   81: goto -> 89
/*     */     //   84: astore_2
/*     */     //   85: aload_2
/*     */     //   86: invokevirtual printStackTrace : ()V
/*     */     //   89: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #328	-> 0
/*     */     //   #329	-> 6
/*     */     //   #330	-> 14
/*     */     //   #331	-> 22
/*     */     //   #332	-> 59
/*     */     //   #333	-> 64
/*     */     //   #336	-> 81
/*     */     //   #334	-> 84
/*     */     //   #335	-> 85
/*     */     //   #337	-> 89
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	81	84	java/lang/Exception
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
/*     */ 
/*     */   
/*     */   private void resetTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokespecial getHandle : (Lorg/bukkit/entity/Player;)Ljava/lang/Object;
/*     */     //   5: astore_2
/*     */     //   6: getstatic io/github/guipenedo/factionwars/helpers/Title11.playerConnection : Ljava/lang/reflect/Field;
/*     */     //   9: aload_2
/*     */     //   10: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   13: astore_3
/*     */     //   14: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   17: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   20: astore #4
/*     */     //   22: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetTitle : Ljava/lang/Class;
/*     */     //   25: iconst_2
/*     */     //   26: anewarray java/lang/Class
/*     */     //   29: dup
/*     */     //   30: iconst_0
/*     */     //   31: getstatic io/github/guipenedo/factionwars/helpers/Title11.packetActions : Ljava/lang/Class;
/*     */     //   34: aastore
/*     */     //   35: dup
/*     */     //   36: iconst_1
/*     */     //   37: getstatic io/github/guipenedo/factionwars/helpers/Title11.chatBaseComponent : Ljava/lang/Class;
/*     */     //   40: aastore
/*     */     //   41: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   44: iconst_2
/*     */     //   45: anewarray java/lang/Object
/*     */     //   48: dup
/*     */     //   49: iconst_0
/*     */     //   50: aload #4
/*     */     //   52: iconst_5
/*     */     //   53: aaload
/*     */     //   54: aastore
/*     */     //   55: dup
/*     */     //   56: iconst_1
/*     */     //   57: aconst_null
/*     */     //   58: aastore
/*     */     //   59: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   62: astore #5
/*     */     //   64: getstatic io/github/guipenedo/factionwars/helpers/Title11.sendPacket : Ljava/lang/reflect/Method;
/*     */     //   67: aload_3
/*     */     //   68: iconst_1
/*     */     //   69: anewarray java/lang/Object
/*     */     //   72: dup
/*     */     //   73: iconst_0
/*     */     //   74: aload #5
/*     */     //   76: aastore
/*     */     //   77: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   80: pop
/*     */     //   81: goto -> 89
/*     */     //   84: astore_2
/*     */     //   85: aload_2
/*     */     //   86: invokevirtual printStackTrace : ()V
/*     */     //   89: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #347	-> 0
/*     */     //   #348	-> 6
/*     */     //   #349	-> 14
/*     */     //   #350	-> 22
/*     */     //   #351	-> 59
/*     */     //   #352	-> 64
/*     */     //   #355	-> 81
/*     */     //   #353	-> 84
/*     */     //   #354	-> 85
/*     */     //   #356	-> 89
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	81	84	java/lang/Exception
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
/*     */ 
/*     */   
/*     */   private Class<?> getPrimitiveType(Class<?> paramClass) {
/* 359 */     return CORRESPONDING_TYPES
/* 360 */       .getOrDefault(paramClass, paramClass);
/*     */   }
/*     */   
/*     */   private Class<?>[] toPrimitiveTypeArray(Class<?>[] paramArrayOfClass) {
/* 364 */     byte b1 = (paramArrayOfClass != null) ? paramArrayOfClass.length : 0;
/* 365 */     Class[] arrayOfClass = new Class[b1];
/* 366 */     for (byte b2 = 0; b2 < b1; b2++)
/* 367 */       arrayOfClass[b2] = getPrimitiveType(paramArrayOfClass[b2]); 
/* 368 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */   private Object getHandle(Player paramPlayer) {
/*     */     try {
/* 373 */       return methodPlayerGetHandle.invoke(paramPlayer, new Object[0]);
/* 374 */     } catch (Exception exception) {
/* 375 */       exception.printStackTrace();
/* 376 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Method getMethod(String paramString, Class<?> paramClass, Class<?>... paramVarArgs) {
/* 382 */     Class[] arrayOfClass = toPrimitiveTypeArray(paramVarArgs);
/* 383 */     for (Method method : paramClass.getMethods()) {
/* 384 */       Class[] arrayOfClass1 = toPrimitiveTypeArray(method.getParameterTypes());
/* 385 */       if (method.getName().equals(paramString) && equalsTypeArray(arrayOfClass1, arrayOfClass))
/* 386 */         return method; 
/*     */     } 
/* 388 */     return null;
/*     */   }
/*     */   
/*     */   private String getVersion() {
/* 392 */     String str = Bukkit.getServer().getClass().getPackage().getName();
/* 393 */     return str.substring(str.lastIndexOf('.') + 1) + ".";
/*     */   }
/*     */   
/*     */   private Class<?> getNMSClass(String paramString) {
/* 397 */     String str = "net.minecraft.server." + getVersion() + paramString;
/* 398 */     Class<?> clazz = null;
/*     */     try {
/* 400 */       clazz = Class.forName(str);
/* 401 */     } catch (Exception exception) {
/* 402 */       exception.printStackTrace();
/*     */     } 
/* 404 */     return clazz;
/*     */   }
/*     */   
/*     */   private Class<?> getOBCClass() {
/* 408 */     String str = "org.bukkit.craftbukkit." + getVersion() + "entity.CraftPlayer";
/* 409 */     Class<?> clazz = null;
/*     */     try {
/* 411 */       clazz = Class.forName(str);
/* 412 */     } catch (Exception exception) {
/* 413 */       exception.printStackTrace();
/*     */     } 
/* 415 */     return clazz;
/*     */   }
/*     */   
/*     */   private Field getField(Class<?> paramClass) {
/*     */     try {
/* 420 */       Field field = paramClass.getDeclaredField("playerConnection");
/* 421 */       field.setAccessible(true);
/* 422 */       return field;
/* 423 */     } catch (Exception exception) {
/* 424 */       exception.printStackTrace();
/* 425 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Method getMethod(Class<?> paramClass, Class<?>... paramVarArgs) {
/* 430 */     for (Method method : paramClass.getMethods()) {
/* 431 */       if (method.getName().equals("sendPacket") && (paramVarArgs.length == 0 || 
/* 432 */         ClassListEqual(paramVarArgs, method
/* 433 */           .getParameterTypes()))) {
/* 434 */         method.setAccessible(true);
/* 435 */         return method;
/*     */       } 
/* 437 */     }  return null;
/*     */   }
/*     */   
/*     */   private boolean ClassListEqual(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 441 */     boolean bool = true;
/* 442 */     if (paramArrayOfClass1.length != paramArrayOfClass2.length)
/* 443 */       return false; 
/* 444 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 445 */       if (paramArrayOfClass1[b] != paramArrayOfClass2[b]) {
/* 446 */         bool = false; break;
/*     */       } 
/*     */     } 
/* 449 */     return bool;
/*     */   }
/*     */   
/*     */   private ChatColor getTitleColor() {
/* 453 */     return this.titleColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleColor(ChatColor paramChatColor) {
/* 462 */     this.titleColor = paramChatColor;
/*     */   }
/*     */   
/*     */   private ChatColor getSubtitleColor() {
/* 466 */     return this.subtitleColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitleColor(ChatColor paramChatColor) {
/* 475 */     this.subtitleColor = paramChatColor;
/*     */   }
/*     */   
/*     */   private int getFadeInTime() {
/* 479 */     return this.fadeInTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeInTime(int paramInt) {
/* 488 */     this.fadeInTime = paramInt;
/*     */   }
/*     */   
/*     */   private int getFadeOutTime() {
/* 492 */     return this.fadeOutTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeOutTime(int paramInt) {
/* 501 */     this.fadeOutTime = paramInt;
/*     */   }
/*     */   
/*     */   private int getStayTime() {
/* 505 */     return this.stayTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStayTime(int paramInt) {
/* 514 */     this.stayTime = paramInt;
/*     */   }
/*     */   
/*     */   private boolean isTicks() {
/* 518 */     return this.ticks;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Title11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */