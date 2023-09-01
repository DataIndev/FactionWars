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
/*     */ 
/*     */ 
/*     */ 
/*     */ class Title
/*     */ {
/*     */   private static Class<?> packetTitle;
/*     */   private static Class<?> packetActions;
/*     */   private static Class<?> nmsChatSerializer;
/*     */   private static Class<?> chatBaseComponent;
/*  27 */   private String title = "";
/*  28 */   private ChatColor titleColor = ChatColor.WHITE;
/*     */   
/*  30 */   private String subtitle = "";
/*  31 */   private ChatColor subtitleColor = ChatColor.WHITE;
/*     */   
/*  33 */   private int fadeInTime = -1;
/*  34 */   private int stayTime = -1;
/*  35 */   private int fadeOutTime = -1;
/*     */   
/*     */   private boolean ticks = false;
/*  38 */   private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<>();
/*     */   
/*     */   public Title() {
/*  41 */     loadClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Title(String paramString) {
/*  51 */     this.title = paramString;
/*  52 */     loadClasses();
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
/*     */   public Title(String paramString1, String paramString2) {
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
/*     */   
/*     */   public Title(Title paramTitle) {
/*  77 */     this.title = paramTitle.getTitle();
/*  78 */     this.subtitle = paramTitle.getSubtitle();
/*  79 */     this.titleColor = paramTitle.getTitleColor();
/*  80 */     this.subtitleColor = paramTitle.getSubtitleColor();
/*  81 */     this.fadeInTime = paramTitle.getFadeInTime();
/*  82 */     this.fadeOutTime = paramTitle.getFadeOutTime();
/*  83 */     this.stayTime = paramTitle.getStayTime();
/*  84 */     this.ticks = paramTitle.isTicks();
/*  85 */     loadClasses();
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
/*     */   Title(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3) {
/* 104 */     this.title = paramString1;
/* 105 */     this.subtitle = paramString2;
/* 106 */     this.fadeInTime = paramInt1;
/* 107 */     this.stayTime = paramInt2;
/* 108 */     this.fadeOutTime = paramInt3;
/* 109 */     loadClasses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadClasses() {
/* 116 */     if (packetTitle == null) {
/* 117 */       packetTitle = getNMSClass("PacketPlayOutTitle");
/* 118 */       packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
/* 119 */       chatBaseComponent = getNMSClass("IChatBaseComponent");
/* 120 */       nmsChatSerializer = getNMSClass("ChatComponentText");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {
/* 131 */     this.title = paramString;
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
/*     */   
/*     */   public void setSubtitle(String paramString) {
/* 150 */     this.subtitle = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSubtitle() {
/* 159 */     return this.subtitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleColor(ChatColor paramChatColor) {
/* 169 */     this.titleColor = paramChatColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubtitleColor(ChatColor paramChatColor) {
/* 179 */     this.subtitleColor = paramChatColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeInTime(int paramInt) {
/* 189 */     this.fadeInTime = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFadeOutTime(int paramInt) {
/* 199 */     this.fadeOutTime = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStayTime(int paramInt) {
/* 209 */     this.stayTime = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTimingsToTicks() {
/* 216 */     this.ticks = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimingsToSeconds() {
/* 223 */     this.ticks = false;
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
/*     */ 
/*     */   
/*     */   void send(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 434
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial resetTitle : (Lorg/bukkit/entity/Player;)V
/*     */     //   11: aload_0
/*     */     //   12: aload_1
/*     */     //   13: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   16: astore_2
/*     */     //   17: aload_0
/*     */     //   18: aload_2
/*     */     //   19: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   22: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   25: aload_2
/*     */     //   26: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   29: astore_3
/*     */     //   30: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   33: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   36: astore #4
/*     */     //   38: aload_0
/*     */     //   39: aload_3
/*     */     //   40: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   43: iconst_0
/*     */     //   44: anewarray java/lang/Class
/*     */     //   47: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   50: astore #5
/*     */     //   52: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   55: iconst_5
/*     */     //   56: anewarray java/lang/Class
/*     */     //   59: dup
/*     */     //   60: iconst_0
/*     */     //   61: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   64: aastore
/*     */     //   65: dup
/*     */     //   66: iconst_1
/*     */     //   67: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   70: aastore
/*     */     //   71: dup
/*     */     //   72: iconst_2
/*     */     //   73: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   76: aastore
/*     */     //   77: dup
/*     */     //   78: iconst_3
/*     */     //   79: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   82: aastore
/*     */     //   83: dup
/*     */     //   84: iconst_4
/*     */     //   85: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   88: aastore
/*     */     //   89: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   92: iconst_5
/*     */     //   93: anewarray java/lang/Object
/*     */     //   96: dup
/*     */     //   97: iconst_0
/*     */     //   98: aload #4
/*     */     //   100: iconst_2
/*     */     //   101: aaload
/*     */     //   102: aastore
/*     */     //   103: dup
/*     */     //   104: iconst_1
/*     */     //   105: aconst_null
/*     */     //   106: aastore
/*     */     //   107: dup
/*     */     //   108: iconst_2
/*     */     //   109: aload_0
/*     */     //   110: getfield fadeInTime : I
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
/*     */     //   132: iconst_3
/*     */     //   133: aload_0
/*     */     //   134: getfield stayTime : I
/*     */     //   137: aload_0
/*     */     //   138: getfield ticks : Z
/*     */     //   141: ifeq -> 148
/*     */     //   144: iconst_1
/*     */     //   145: goto -> 150
/*     */     //   148: bipush #20
/*     */     //   150: imul
/*     */     //   151: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   154: aastore
/*     */     //   155: dup
/*     */     //   156: iconst_4
/*     */     //   157: aload_0
/*     */     //   158: getfield fadeOutTime : I
/*     */     //   161: aload_0
/*     */     //   162: getfield ticks : Z
/*     */     //   165: ifeq -> 172
/*     */     //   168: iconst_1
/*     */     //   169: goto -> 174
/*     */     //   172: bipush #20
/*     */     //   174: imul
/*     */     //   175: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   178: aastore
/*     */     //   179: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   182: astore #6
/*     */     //   184: aload_0
/*     */     //   185: getfield fadeInTime : I
/*     */     //   188: iconst_m1
/*     */     //   189: if_icmpeq -> 224
/*     */     //   192: aload_0
/*     */     //   193: getfield fadeOutTime : I
/*     */     //   196: iconst_m1
/*     */     //   197: if_icmpeq -> 224
/*     */     //   200: aload_0
/*     */     //   201: getfield stayTime : I
/*     */     //   204: iconst_m1
/*     */     //   205: if_icmpeq -> 224
/*     */     //   208: aload #5
/*     */     //   210: aload_3
/*     */     //   211: iconst_1
/*     */     //   212: anewarray java/lang/Object
/*     */     //   215: dup
/*     */     //   216: iconst_0
/*     */     //   217: aload #6
/*     */     //   219: aastore
/*     */     //   220: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   223: pop
/*     */     //   224: getstatic io/github/guipenedo/factionwars/helpers/Title.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   227: iconst_1
/*     */     //   228: anewarray java/lang/Class
/*     */     //   231: dup
/*     */     //   232: iconst_0
/*     */     //   233: ldc java/lang/String
/*     */     //   235: aastore
/*     */     //   236: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   239: iconst_1
/*     */     //   240: anewarray java/lang/Object
/*     */     //   243: dup
/*     */     //   244: iconst_0
/*     */     //   245: bipush #38
/*     */     //   247: aload_0
/*     */     //   248: getfield title : Ljava/lang/String;
/*     */     //   251: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   254: aastore
/*     */     //   255: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   258: astore #7
/*     */     //   260: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   263: iconst_2
/*     */     //   264: anewarray java/lang/Class
/*     */     //   267: dup
/*     */     //   268: iconst_0
/*     */     //   269: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   272: aastore
/*     */     //   273: dup
/*     */     //   274: iconst_1
/*     */     //   275: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   278: aastore
/*     */     //   279: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   282: iconst_2
/*     */     //   283: anewarray java/lang/Object
/*     */     //   286: dup
/*     */     //   287: iconst_0
/*     */     //   288: aload #4
/*     */     //   290: iconst_0
/*     */     //   291: aaload
/*     */     //   292: aastore
/*     */     //   293: dup
/*     */     //   294: iconst_1
/*     */     //   295: aload #7
/*     */     //   297: aastore
/*     */     //   298: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   301: astore #6
/*     */     //   303: aload #5
/*     */     //   305: aload_3
/*     */     //   306: iconst_1
/*     */     //   307: anewarray java/lang/Object
/*     */     //   310: dup
/*     */     //   311: iconst_0
/*     */     //   312: aload #6
/*     */     //   314: aastore
/*     */     //   315: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   318: pop
/*     */     //   319: aload_0
/*     */     //   320: getfield subtitle : Ljava/lang/String;
/*     */     //   323: ldc ''
/*     */     //   325: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   328: ifne -> 426
/*     */     //   331: getstatic io/github/guipenedo/factionwars/helpers/Title.nmsChatSerializer : Ljava/lang/Class;
/*     */     //   334: iconst_1
/*     */     //   335: anewarray java/lang/Class
/*     */     //   338: dup
/*     */     //   339: iconst_0
/*     */     //   340: ldc java/lang/String
/*     */     //   342: aastore
/*     */     //   343: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   346: iconst_1
/*     */     //   347: anewarray java/lang/Object
/*     */     //   350: dup
/*     */     //   351: iconst_0
/*     */     //   352: bipush #38
/*     */     //   354: aload_0
/*     */     //   355: getfield subtitle : Ljava/lang/String;
/*     */     //   358: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   361: aastore
/*     */     //   362: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   365: astore #7
/*     */     //   367: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   370: iconst_2
/*     */     //   371: anewarray java/lang/Class
/*     */     //   374: dup
/*     */     //   375: iconst_0
/*     */     //   376: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   379: aastore
/*     */     //   380: dup
/*     */     //   381: iconst_1
/*     */     //   382: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   385: aastore
/*     */     //   386: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   389: iconst_2
/*     */     //   390: anewarray java/lang/Object
/*     */     //   393: dup
/*     */     //   394: iconst_0
/*     */     //   395: aload #4
/*     */     //   397: iconst_1
/*     */     //   398: aaload
/*     */     //   399: aastore
/*     */     //   400: dup
/*     */     //   401: iconst_1
/*     */     //   402: aload #7
/*     */     //   404: aastore
/*     */     //   405: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   408: astore #6
/*     */     //   410: aload #5
/*     */     //   412: aload_3
/*     */     //   413: iconst_1
/*     */     //   414: anewarray java/lang/Object
/*     */     //   417: dup
/*     */     //   418: iconst_0
/*     */     //   419: aload #6
/*     */     //   421: aastore
/*     */     //   422: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   425: pop
/*     */     //   426: goto -> 434
/*     */     //   429: astore_2
/*     */     //   430: aload_2
/*     */     //   431: invokevirtual printStackTrace : ()V
/*     */     //   434: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #233	-> 0
/*     */     //   #235	-> 6
/*     */     //   #238	-> 11
/*     */     //   #239	-> 17
/*     */     //   #240	-> 26
/*     */     //   #241	-> 30
/*     */     //   #242	-> 38
/*     */     //   #244	-> 52
/*     */     //   #247	-> 127
/*     */     //   #248	-> 151
/*     */     //   #249	-> 175
/*     */     //   #246	-> 179
/*     */     //   #251	-> 184
/*     */     //   #252	-> 208
/*     */     //   #255	-> 224
/*     */     //   #257	-> 251
/*     */     //   #256	-> 255
/*     */     //   #258	-> 260
/*     */     //   #259	-> 298
/*     */     //   #260	-> 303
/*     */     //   #261	-> 319
/*     */     //   #263	-> 331
/*     */     //   #265	-> 358
/*     */     //   #264	-> 362
/*     */     //   #267	-> 367
/*     */     //   #268	-> 405
/*     */     //   #270	-> 410
/*     */     //   #274	-> 426
/*     */     //   #272	-> 429
/*     */     //   #273	-> 430
/*     */     //   #276	-> 434
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   11	426	429	java/lang/Exception
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
/*     */ 
/*     */   
/*     */   public void updateTimes(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 227
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: aload_0
/*     */     //   13: aload_2
/*     */     //   14: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   17: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   20: aload_2
/*     */     //   21: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: astore_3
/*     */     //   25: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   28: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   31: astore #4
/*     */     //   33: aload_0
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   38: iconst_0
/*     */     //   39: anewarray java/lang/Class
/*     */     //   42: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   45: astore #5
/*     */     //   47: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   50: iconst_5
/*     */     //   51: anewarray java/lang/Class
/*     */     //   54: dup
/*     */     //   55: iconst_0
/*     */     //   56: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   59: aastore
/*     */     //   60: dup
/*     */     //   61: iconst_1
/*     */     //   62: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   65: aastore
/*     */     //   66: dup
/*     */     //   67: iconst_2
/*     */     //   68: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   71: aastore
/*     */     //   72: dup
/*     */     //   73: iconst_3
/*     */     //   74: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   77: aastore
/*     */     //   78: dup
/*     */     //   79: iconst_4
/*     */     //   80: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
/*     */     //   83: aastore
/*     */     //   84: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   87: iconst_5
/*     */     //   88: anewarray java/lang/Object
/*     */     //   91: dup
/*     */     //   92: iconst_0
/*     */     //   93: aload #4
/*     */     //   95: iconst_2
/*     */     //   96: aaload
/*     */     //   97: aastore
/*     */     //   98: dup
/*     */     //   99: iconst_1
/*     */     //   100: aconst_null
/*     */     //   101: aastore
/*     */     //   102: dup
/*     */     //   103: iconst_2
/*     */     //   104: aload_0
/*     */     //   105: getfield fadeInTime : I
/*     */     //   108: aload_0
/*     */     //   109: getfield ticks : Z
/*     */     //   112: ifeq -> 119
/*     */     //   115: iconst_1
/*     */     //   116: goto -> 121
/*     */     //   119: bipush #20
/*     */     //   121: imul
/*     */     //   122: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   125: aastore
/*     */     //   126: dup
/*     */     //   127: iconst_3
/*     */     //   128: aload_0
/*     */     //   129: getfield stayTime : I
/*     */     //   132: aload_0
/*     */     //   133: getfield ticks : Z
/*     */     //   136: ifeq -> 143
/*     */     //   139: iconst_1
/*     */     //   140: goto -> 145
/*     */     //   143: bipush #20
/*     */     //   145: imul
/*     */     //   146: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   149: aastore
/*     */     //   150: dup
/*     */     //   151: iconst_4
/*     */     //   152: aload_0
/*     */     //   153: getfield fadeOutTime : I
/*     */     //   156: aload_0
/*     */     //   157: getfield ticks : Z
/*     */     //   160: ifeq -> 167
/*     */     //   163: iconst_1
/*     */     //   164: goto -> 169
/*     */     //   167: bipush #20
/*     */     //   169: imul
/*     */     //   170: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   173: aastore
/*     */     //   174: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   177: astore #6
/*     */     //   179: aload_0
/*     */     //   180: getfield fadeInTime : I
/*     */     //   183: iconst_m1
/*     */     //   184: if_icmpeq -> 219
/*     */     //   187: aload_0
/*     */     //   188: getfield fadeOutTime : I
/*     */     //   191: iconst_m1
/*     */     //   192: if_icmpeq -> 219
/*     */     //   195: aload_0
/*     */     //   196: getfield stayTime : I
/*     */     //   199: iconst_m1
/*     */     //   200: if_icmpeq -> 219
/*     */     //   203: aload #5
/*     */     //   205: aload_3
/*     */     //   206: iconst_1
/*     */     //   207: anewarray java/lang/Object
/*     */     //   210: dup
/*     */     //   211: iconst_0
/*     */     //   212: aload #6
/*     */     //   214: aastore
/*     */     //   215: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   218: pop
/*     */     //   219: goto -> 227
/*     */     //   222: astore_2
/*     */     //   223: aload_2
/*     */     //   224: invokevirtual printStackTrace : ()V
/*     */     //   227: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #279	-> 0
/*     */     //   #281	-> 6
/*     */     //   #282	-> 12
/*     */     //   #283	-> 21
/*     */     //   #284	-> 25
/*     */     //   #285	-> 33
/*     */     //   #287	-> 47
/*     */     //   #293	-> 122
/*     */     //   #295	-> 146
/*     */     //   #297	-> 170
/*     */     //   #290	-> 174
/*     */     //   #299	-> 179
/*     */     //   #301	-> 203
/*     */     //   #305	-> 219
/*     */     //   #303	-> 222
/*     */     //   #304	-> 223
/*     */     //   #307	-> 227
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	219	222	java/lang/Exception
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
/*     */   
/*     */   public void updateTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 150
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: aload_0
/*     */     //   13: aload_2
/*     */     //   14: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   17: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   20: aload_2
/*     */     //   21: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: astore_3
/*     */     //   25: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   28: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   31: astore #4
/*     */     //   33: aload_0
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   38: iconst_0
/*     */     //   39: anewarray java/lang/Class
/*     */     //   42: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   45: astore #5
/*     */     //   47: getstatic io/github/guipenedo/factionwars/helpers/Title.nmsChatSerializer : Ljava/lang/Class;
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
/*     */     //   68: bipush #38
/*     */     //   70: aload_0
/*     */     //   71: getfield title : Ljava/lang/String;
/*     */     //   74: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   77: aastore
/*     */     //   78: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   81: astore #6
/*     */     //   83: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   86: iconst_2
/*     */     //   87: anewarray java/lang/Class
/*     */     //   90: dup
/*     */     //   91: iconst_0
/*     */     //   92: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   95: aastore
/*     */     //   96: dup
/*     */     //   97: iconst_1
/*     */     //   98: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   101: aastore
/*     */     //   102: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   105: iconst_2
/*     */     //   106: anewarray java/lang/Object
/*     */     //   109: dup
/*     */     //   110: iconst_0
/*     */     //   111: aload #4
/*     */     //   113: iconst_0
/*     */     //   114: aaload
/*     */     //   115: aastore
/*     */     //   116: dup
/*     */     //   117: iconst_1
/*     */     //   118: aload #6
/*     */     //   120: aastore
/*     */     //   121: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   124: astore #7
/*     */     //   126: aload #5
/*     */     //   128: aload_3
/*     */     //   129: iconst_1
/*     */     //   130: anewarray java/lang/Object
/*     */     //   133: dup
/*     */     //   134: iconst_0
/*     */     //   135: aload #7
/*     */     //   137: aastore
/*     */     //   138: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   141: pop
/*     */     //   142: goto -> 150
/*     */     //   145: astore_2
/*     */     //   146: aload_2
/*     */     //   147: invokevirtual printStackTrace : ()V
/*     */     //   150: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #310	-> 0
/*     */     //   #312	-> 6
/*     */     //   #313	-> 12
/*     */     //   #314	-> 21
/*     */     //   #315	-> 25
/*     */     //   #316	-> 33
/*     */     //   #318	-> 47
/*     */     //   #321	-> 74
/*     */     //   #320	-> 78
/*     */     //   #323	-> 83
/*     */     //   #324	-> 102
/*     */     //   #326	-> 121
/*     */     //   #328	-> 126
/*     */     //   #331	-> 142
/*     */     //   #329	-> 145
/*     */     //   #330	-> 146
/*     */     //   #333	-> 150
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	142	145	java/lang/Exception
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
/*     */   
/*     */   public void updateSubtitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   3: ifnull -> 150
/*     */     //   6: aload_0
/*     */     //   7: aload_1
/*     */     //   8: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   11: astore_2
/*     */     //   12: aload_0
/*     */     //   13: aload_2
/*     */     //   14: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   17: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   20: aload_2
/*     */     //   21: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: astore_3
/*     */     //   25: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   28: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   31: astore #4
/*     */     //   33: aload_0
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   38: iconst_0
/*     */     //   39: anewarray java/lang/Class
/*     */     //   42: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   45: astore #5
/*     */     //   47: getstatic io/github/guipenedo/factionwars/helpers/Title.nmsChatSerializer : Ljava/lang/Class;
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
/*     */     //   68: bipush #38
/*     */     //   70: aload_0
/*     */     //   71: getfield subtitle : Ljava/lang/String;
/*     */     //   74: invokestatic translateAlternateColorCodes : (CLjava/lang/String;)Ljava/lang/String;
/*     */     //   77: aastore
/*     */     //   78: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   81: astore #6
/*     */     //   83: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   86: iconst_2
/*     */     //   87: anewarray java/lang/Class
/*     */     //   90: dup
/*     */     //   91: iconst_0
/*     */     //   92: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   95: aastore
/*     */     //   96: dup
/*     */     //   97: iconst_1
/*     */     //   98: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   101: aastore
/*     */     //   102: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   105: iconst_2
/*     */     //   106: anewarray java/lang/Object
/*     */     //   109: dup
/*     */     //   110: iconst_0
/*     */     //   111: aload #4
/*     */     //   113: iconst_1
/*     */     //   114: aaload
/*     */     //   115: aastore
/*     */     //   116: dup
/*     */     //   117: iconst_1
/*     */     //   118: aload #6
/*     */     //   120: aastore
/*     */     //   121: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   124: astore #7
/*     */     //   126: aload #5
/*     */     //   128: aload_3
/*     */     //   129: iconst_1
/*     */     //   130: anewarray java/lang/Object
/*     */     //   133: dup
/*     */     //   134: iconst_0
/*     */     //   135: aload #7
/*     */     //   137: aastore
/*     */     //   138: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   141: pop
/*     */     //   142: goto -> 150
/*     */     //   145: astore_2
/*     */     //   146: aload_2
/*     */     //   147: invokevirtual printStackTrace : ()V
/*     */     //   150: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #336	-> 0
/*     */     //   #338	-> 6
/*     */     //   #339	-> 12
/*     */     //   #340	-> 21
/*     */     //   #341	-> 25
/*     */     //   #342	-> 33
/*     */     //   #344	-> 47
/*     */     //   #347	-> 74
/*     */     //   #346	-> 78
/*     */     //   #349	-> 83
/*     */     //   #350	-> 102
/*     */     //   #352	-> 121
/*     */     //   #354	-> 126
/*     */     //   #357	-> 142
/*     */     //   #355	-> 145
/*     */     //   #356	-> 146
/*     */     //   #359	-> 150
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	142	145	java/lang/Exception
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
/*     */   
/*     */   public void broadcast() {
/* 365 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 366 */       send(player);
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
/*     */ 
/*     */   
/*     */   public void clearTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   5: astore_2
/*     */     //   6: aload_0
/*     */     //   7: aload_2
/*     */     //   8: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   11: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   14: aload_2
/*     */     //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   18: astore_3
/*     */     //   19: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   22: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   25: astore #4
/*     */     //   27: aload_0
/*     */     //   28: aload_3
/*     */     //   29: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   32: iconst_0
/*     */     //   33: anewarray java/lang/Class
/*     */     //   36: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   39: astore #5
/*     */     //   41: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   44: iconst_2
/*     */     //   45: anewarray java/lang/Class
/*     */     //   48: dup
/*     */     //   49: iconst_0
/*     */     //   50: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   53: aastore
/*     */     //   54: dup
/*     */     //   55: iconst_1
/*     */     //   56: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   59: aastore
/*     */     //   60: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   63: iconst_2
/*     */     //   64: anewarray java/lang/Object
/*     */     //   67: dup
/*     */     //   68: iconst_0
/*     */     //   69: aload #4
/*     */     //   71: iconst_3
/*     */     //   72: aaload
/*     */     //   73: aastore
/*     */     //   74: dup
/*     */     //   75: iconst_1
/*     */     //   76: aconst_null
/*     */     //   77: aastore
/*     */     //   78: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   81: astore #6
/*     */     //   83: aload #5
/*     */     //   85: aload_3
/*     */     //   86: iconst_1
/*     */     //   87: anewarray java/lang/Object
/*     */     //   90: dup
/*     */     //   91: iconst_0
/*     */     //   92: aload #6
/*     */     //   94: aastore
/*     */     //   95: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   98: pop
/*     */     //   99: goto -> 107
/*     */     //   102: astore_2
/*     */     //   103: aload_2
/*     */     //   104: invokevirtual printStackTrace : ()V
/*     */     //   107: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #379	-> 0
/*     */     //   #380	-> 6
/*     */     //   #381	-> 15
/*     */     //   #382	-> 19
/*     */     //   #383	-> 27
/*     */     //   #384	-> 41
/*     */     //   #385	-> 78
/*     */     //   #386	-> 83
/*     */     //   #389	-> 99
/*     */     //   #387	-> 102
/*     */     //   #388	-> 103
/*     */     //   #390	-> 107
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	99	102	java/lang/Exception
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
/*     */   private void resetTitle(Player paramPlayer) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokespecial getHandle : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   5: astore_2
/*     */     //   6: aload_0
/*     */     //   7: aload_2
/*     */     //   8: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   11: invokespecial getField : (Ljava/lang/Class;)Ljava/lang/reflect/Field;
/*     */     //   14: aload_2
/*     */     //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   18: astore_3
/*     */     //   19: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   22: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*     */     //   25: astore #4
/*     */     //   27: aload_0
/*     */     //   28: aload_3
/*     */     //   29: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   32: iconst_0
/*     */     //   33: anewarray java/lang/Class
/*     */     //   36: invokespecial getMethod : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
/*     */     //   39: astore #5
/*     */     //   41: getstatic io/github/guipenedo/factionwars/helpers/Title.packetTitle : Ljava/lang/Class;
/*     */     //   44: iconst_2
/*     */     //   45: anewarray java/lang/Class
/*     */     //   48: dup
/*     */     //   49: iconst_0
/*     */     //   50: getstatic io/github/guipenedo/factionwars/helpers/Title.packetActions : Ljava/lang/Class;
/*     */     //   53: aastore
/*     */     //   54: dup
/*     */     //   55: iconst_1
/*     */     //   56: getstatic io/github/guipenedo/factionwars/helpers/Title.chatBaseComponent : Ljava/lang/Class;
/*     */     //   59: aastore
/*     */     //   60: invokevirtual getConstructor : ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*     */     //   63: iconst_2
/*     */     //   64: anewarray java/lang/Object
/*     */     //   67: dup
/*     */     //   68: iconst_0
/*     */     //   69: aload #4
/*     */     //   71: iconst_4
/*     */     //   72: aaload
/*     */     //   73: aastore
/*     */     //   74: dup
/*     */     //   75: iconst_1
/*     */     //   76: aconst_null
/*     */     //   77: aastore
/*     */     //   78: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   81: astore #6
/*     */     //   83: aload #5
/*     */     //   85: aload_3
/*     */     //   86: iconst_1
/*     */     //   87: anewarray java/lang/Object
/*     */     //   90: dup
/*     */     //   91: iconst_0
/*     */     //   92: aload #6
/*     */     //   94: aastore
/*     */     //   95: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   98: pop
/*     */     //   99: goto -> 107
/*     */     //   102: astore_2
/*     */     //   103: aload_2
/*     */     //   104: invokevirtual printStackTrace : ()V
/*     */     //   107: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #401	-> 0
/*     */     //   #402	-> 6
/*     */     //   #403	-> 15
/*     */     //   #404	-> 19
/*     */     //   #405	-> 27
/*     */     //   #406	-> 41
/*     */     //   #407	-> 78
/*     */     //   #408	-> 83
/*     */     //   #411	-> 99
/*     */     //   #409	-> 102
/*     */     //   #410	-> 103
/*     */     //   #412	-> 107
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	99	102	java/lang/Exception
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
/*     */   private Class<?> getPrimitiveType(Class<?> paramClass) {
/* 415 */     return CORRESPONDING_TYPES
/* 416 */       .getOrDefault(paramClass, paramClass);
/*     */   }
/*     */   
/*     */   private Class<?>[] toPrimitiveTypeArray(Class<?>[] paramArrayOfClass) {
/* 420 */     byte b1 = (paramArrayOfClass != null) ? paramArrayOfClass.length : 0;
/* 421 */     Class[] arrayOfClass = new Class[b1];
/* 422 */     for (byte b2 = 0; b2 < b1; b2++)
/* 423 */       arrayOfClass[b2] = getPrimitiveType(paramArrayOfClass[b2]); 
/* 424 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */   private static boolean equalsTypeArray(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 428 */     if (paramArrayOfClass1.length != paramArrayOfClass2.length)
/* 429 */       return false; 
/* 430 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 431 */       if (!paramArrayOfClass1[b].equals(paramArrayOfClass2[b]) && !paramArrayOfClass1[b].isAssignableFrom(paramArrayOfClass2[b]))
/* 432 */         return false; 
/* 433 */     }  return true;
/*     */   }
/*     */   
/*     */   private Object getHandle(Object paramObject) {
/*     */     try {
/* 438 */       return getMethod("getHandle", paramObject.getClass(), new Class[0]).invoke(paramObject, new Object[0]);
/* 439 */     } catch (Exception exception) {
/* 440 */       exception.printStackTrace();
/* 441 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Method getMethod(String paramString, Class<?> paramClass, Class<?>... paramVarArgs) {
/* 447 */     Class[] arrayOfClass = toPrimitiveTypeArray(paramVarArgs);
/* 448 */     for (Method method : paramClass.getMethods()) {
/* 449 */       Class[] arrayOfClass1 = toPrimitiveTypeArray(method.getParameterTypes());
/* 450 */       if (method.getName().equals(paramString) && equalsTypeArray(arrayOfClass1, arrayOfClass))
/* 451 */         return method; 
/*     */     } 
/* 453 */     return null;
/*     */   }
/*     */   
/*     */   private String getVersion() {
/* 457 */     String str = Bukkit.getServer().getClass().getPackage().getName();
/* 458 */     return str.substring(str.lastIndexOf('.') + 1) + ".";
/*     */   }
/*     */   
/*     */   private Class<?> getNMSClass(String paramString) {
/* 462 */     String str = "net.minecraft.server." + getVersion() + paramString;
/* 463 */     Class<?> clazz = null;
/*     */     try {
/* 465 */       clazz = Class.forName(str);
/* 466 */     } catch (Exception exception) {
/* 467 */       exception.printStackTrace();
/*     */     } 
/* 469 */     return clazz;
/*     */   }
/*     */   
/*     */   private Field getField(Class<?> paramClass) {
/*     */     try {
/* 474 */       Field field = paramClass.getDeclaredField("playerConnection");
/* 475 */       field.setAccessible(true);
/* 476 */       return field;
/* 477 */     } catch (Exception exception) {
/* 478 */       exception.printStackTrace();
/* 479 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Method getMethod(Class<?> paramClass, Class<?>... paramVarArgs) {
/* 484 */     for (Method method : paramClass.getMethods()) {
/* 485 */       if (method.getName().equals("sendPacket") && (paramVarArgs.length == 0 || 
/* 486 */         ClassListEqual(paramVarArgs, method
/* 487 */           .getParameterTypes()))) {
/* 488 */         method.setAccessible(true);
/* 489 */         return method;
/*     */       } 
/* 491 */     }  return null;
/*     */   }
/*     */   
/*     */   private boolean ClassListEqual(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 495 */     boolean bool = true;
/* 496 */     if (paramArrayOfClass1.length != paramArrayOfClass2.length)
/* 497 */       return false; 
/* 498 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 499 */       if (paramArrayOfClass1[b] != paramArrayOfClass2[b]) {
/* 500 */         bool = false; break;
/*     */       } 
/*     */     } 
/* 503 */     return bool;
/*     */   }
/*     */   
/*     */   private ChatColor getTitleColor() {
/* 507 */     return this.titleColor;
/*     */   }
/*     */   
/*     */   private ChatColor getSubtitleColor() {
/* 511 */     return this.subtitleColor;
/*     */   }
/*     */   
/*     */   private int getFadeInTime() {
/* 515 */     return this.fadeInTime;
/*     */   }
/*     */   
/*     */   private int getFadeOutTime() {
/* 519 */     return this.fadeOutTime;
/*     */   }
/*     */   
/*     */   private int getStayTime() {
/* 523 */     return this.stayTime;
/*     */   }
/*     */   
/*     */   private boolean isTicks() {
/* 527 */     return this.ticks;
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Title.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */