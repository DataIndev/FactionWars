/*     */ package io.github.guipenedo.factionwars.helpers;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.ServicePriority;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ 
/*     */ public class Metrics {
/*     */   static {
/*  35 */     if (System.getProperty("bstats.relocatecheck") == null || !System.getProperty("bstats.relocatecheck").equals("false")) {
/*     */       
/*  37 */       String str1 = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115, 46, 98, 117, 107, 107, 105, 116 });
/*     */       
/*  39 */       String str2 = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
/*     */       
/*  41 */       if (Metrics.class.getPackage().getName().equals(str1) || Metrics.class.getPackage().getName().equals(str2)) {
/*  42 */         throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int B_STATS_VERSION = 1;
/*     */ 
/*     */   
/*     */   public static boolean debugging = false;
/*     */ 
/*     */   
/*     */   private static final String URL = "https://bStats.org/submitData/bukkit";
/*     */ 
/*     */   
/*     */   private boolean enabled;
/*     */ 
/*     */   
/*     */   private static boolean logFailedRequests;
/*     */ 
/*     */   
/*     */   private static boolean logSentData;
/*     */ 
/*     */   
/*     */   private static boolean logResponseStatusText;
/*     */ 
/*     */   
/*     */   private static String serverUUID;
/*     */ 
/*     */   
/*     */   private final Plugin plugin;
/*     */   
/*  75 */   private final List<CustomChart> charts = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Metrics(Plugin paramPlugin) {
/*  83 */     if (paramPlugin == null) {
/*  84 */       throw new IllegalArgumentException("Plugin cannot be null!");
/*     */     }
/*  86 */     this.plugin = paramPlugin;
/*     */ 
/*     */     
/*  89 */     File file1 = new File(paramPlugin.getDataFolder().getParentFile(), "bStats");
/*  90 */     File file2 = new File(file1, "config.yml");
/*  91 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file2);
/*     */ 
/*     */     
/*  94 */     if (!yamlConfiguration.isSet("serverUuid")) {
/*     */ 
/*     */       
/*  97 */       yamlConfiguration.addDefault("enabled", Boolean.valueOf(true));
/*     */       
/*  99 */       yamlConfiguration.addDefault("serverUuid", UUID.randomUUID().toString());
/*     */       
/* 101 */       yamlConfiguration.addDefault("logFailedRequests", Boolean.valueOf(false));
/*     */       
/* 103 */       yamlConfiguration.addDefault("logSentData", Boolean.valueOf(false));
/*     */       
/* 105 */       yamlConfiguration.addDefault("logResponseStatusText", Boolean.valueOf(false));
/*     */ 
/*     */       
/* 108 */       yamlConfiguration.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)")
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         .copyDefaults(true);
/*     */       try {
/* 115 */         yamlConfiguration.save(file2);
/* 116 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     this.enabled = yamlConfiguration.getBoolean("enabled", true);
/* 122 */     serverUUID = yamlConfiguration.getString("serverUuid");
/* 123 */     logFailedRequests = yamlConfiguration.getBoolean("logFailedRequests", false);
/* 124 */     logSentData = yamlConfiguration.getBoolean("logSentData", false);
/* 125 */     logResponseStatusText = yamlConfiguration.getBoolean("logResponseStatusText", false);
/* 126 */     debugging = FactionWars.getMainConfig().getConfig().contains("debugging");
/*     */ 
/*     */     
/* 129 */     if (this.enabled) {
/* 130 */       boolean bool = false;
/*     */       
/* 132 */       for (Class clazz : Bukkit.getServicesManager().getKnownServices()) {
/*     */         try {
/* 134 */           clazz.getField("B_STATS_VERSION");
/* 135 */           bool = true;
/*     */           break;
/* 137 */         } catch (NoSuchFieldException noSuchFieldException) {}
/*     */       } 
/*     */ 
/*     */       
/* 141 */       Bukkit.getServicesManager().register(Metrics.class, this, paramPlugin, ServicePriority.Normal);
/* 142 */       if (!bool)
/*     */       {
/* 144 */         startSubmitting();
/*     */       }
/*     */     } 
/* 147 */     runAsyncTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 156 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCustomChart(CustomChart paramCustomChart) {
/* 165 */     if (paramCustomChart == null) {
/* 166 */       throw new IllegalArgumentException("Chart cannot be null!");
/*     */     }
/* 168 */     this.charts.add(paramCustomChart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startSubmitting() {
/* 175 */     final Timer timer = new Timer(true);
/* 176 */     timer.scheduleAtFixedRate(new TimerTask()
/*     */         {
/*     */           public void run() {
/* 179 */             if (!Metrics.this.plugin.isEnabled()) {
/* 180 */               timer.cancel();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 185 */             Bukkit.getScheduler().runTask(Metrics.this.plugin, () -> Metrics.this.submitData());
/*     */           }
/*     */         }300000L, 1800000L);
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
/*     */   public JSONObject getPluginData() {
/* 200 */     JSONObject jSONObject = new JSONObject();
/*     */     
/* 202 */     String str1 = this.plugin.getDescription().getName();
/* 203 */     String str2 = this.plugin.getDescription().getVersion();
/*     */     
/* 205 */     jSONObject.put("pluginName", str1);
/* 206 */     jSONObject.put("pluginVersion", str2);
/* 207 */     JSONArray jSONArray = new JSONArray();
/* 208 */     for (CustomChart customChart : this.charts) {
/*     */       
/* 210 */       JSONObject jSONObject1 = customChart.getRequestJsonObject();
/* 211 */       if (jSONObject1 == null) {
/*     */         continue;
/*     */       }
/* 214 */       jSONArray.add(jSONObject1);
/*     */     } 
/* 216 */     jSONObject.put("customCharts", jSONArray);
/*     */     
/* 218 */     return jSONObject;
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
/*     */   private JSONObject getServerData() {
/*     */     int i;
/*     */     try {
/* 232 */       Method method = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", new Class[0]);
/*     */ 
/*     */       
/* 235 */       i = method.getReturnType().equals(Collection.class) ? ((Collection)method.invoke(Bukkit.getServer(), new Object[0])).size() : ((Player[])method.invoke(Bukkit.getServer(), new Object[0])).length;
/* 236 */     } catch (Exception exception) {
/* 237 */       i = Bukkit.getOnlinePlayers().size();
/*     */     } 
/* 239 */     boolean bool = Bukkit.getOnlineMode() ? true : false;
/* 240 */     String str1 = Bukkit.getVersion();
/*     */ 
/*     */     
/* 243 */     String str2 = System.getProperty("java.version");
/* 244 */     String str3 = System.getProperty("os.name");
/* 245 */     String str4 = System.getProperty("os.arch");
/* 246 */     String str5 = System.getProperty("os.version");
/* 247 */     int j = Runtime.getRuntime().availableProcessors();
/*     */     
/* 249 */     JSONObject jSONObject = new JSONObject();
/*     */     
/* 251 */     jSONObject.put("serverUUID", serverUUID);
/*     */     
/* 253 */     jSONObject.put("playerAmount", Integer.valueOf(i));
/* 254 */     jSONObject.put("onlineMode", Integer.valueOf(bool));
/* 255 */     jSONObject.put("bukkitVersion", str1);
/*     */     
/* 257 */     jSONObject.put("javaVersion", str2);
/* 258 */     jSONObject.put("osName", str3);
/* 259 */     jSONObject.put("osArch", str4);
/* 260 */     jSONObject.put("osVersion", str5);
/* 261 */     jSONObject.put("coreCount", Integer.valueOf(j));
/*     */     
/* 263 */     return jSONObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void submitData() {
/* 270 */     JSONObject jSONObject = getServerData();
/*     */     
/* 272 */     JSONArray jSONArray = new JSONArray();
/*     */     
/* 274 */     for (Class clazz : Bukkit.getServicesManager().getKnownServices()) {
/*     */       try {
/* 276 */         clazz.getField("B_STATS_VERSION");
/*     */         
/* 278 */         for (RegisteredServiceProvider registeredServiceProvider : Bukkit.getServicesManager().getRegistrations(clazz)) {
/*     */           try {
/* 280 */             jSONArray.add(registeredServiceProvider.getService().getMethod("getPluginData", new Class[0]).invoke(registeredServiceProvider.getProvider(), new Object[0]));
/* 281 */           } catch (NullPointerException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException nullPointerException) {}
/*     */         }
/*     */       
/* 284 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*     */     } 
/*     */ 
/*     */     
/* 288 */     jSONObject.put("plugins", jSONArray);
/*     */ 
/*     */     
/* 291 */     (new Thread(() -> {
/*     */           
/*     */           try {
/*     */             sendData(this.plugin, paramJSONObject);
/* 295 */           } catch (Exception exception) {
/*     */             
/*     */             if (logFailedRequests) {
/*     */               this.plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + this.plugin.getName(), exception);
/*     */             }
/*     */           } 
/* 301 */         })).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sendData(Plugin paramPlugin, JSONObject paramJSONObject) {
/* 312 */     if (paramJSONObject == null) {
/* 313 */       throw new IllegalArgumentException("Data cannot be null!");
/*     */     }
/* 315 */     if (Bukkit.isPrimaryThread()) {
/* 316 */       throw new IllegalAccessException("This method must not be called from the main thread!");
/*     */     }
/* 318 */     if (logSentData) {
/* 319 */       paramPlugin.getLogger().info("Sending data to bStats: " + paramJSONObject.toString());
/*     */     }
/* 321 */     HttpsURLConnection httpsURLConnection = (HttpsURLConnection)(new URL("https://bStats.org/submitData/bukkit")).openConnection();
/*     */ 
/*     */     
/* 324 */     byte[] arrayOfByte = compress(paramJSONObject.toString());
/*     */ 
/*     */     
/* 327 */     httpsURLConnection.setRequestMethod("POST");
/* 328 */     httpsURLConnection.addRequestProperty("Accept", "application/json");
/* 329 */     httpsURLConnection.addRequestProperty("Connection", "close");
/* 330 */     httpsURLConnection.addRequestProperty("Content-Encoding", "gzip");
/* 331 */     httpsURLConnection.addRequestProperty("Content-Length", String.valueOf(arrayOfByte.length));
/* 332 */     httpsURLConnection.setRequestProperty("Content-Type", "application/json");
/* 333 */     httpsURLConnection.setRequestProperty("User-Agent", "MC-Server/1");
/*     */ 
/*     */     
/* 336 */     httpsURLConnection.setDoOutput(true);
/* 337 */     DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
/* 338 */     dataOutputStream.write(arrayOfByte);
/* 339 */     dataOutputStream.flush();
/* 340 */     dataOutputStream.close();
/*     */     
/* 342 */     InputStream inputStream = httpsURLConnection.getInputStream();
/* 343 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*     */     
/* 345 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     String str;
/* 347 */     while ((str = bufferedReader.readLine()) != null) {
/* 348 */       stringBuilder.append(str);
/*     */     }
/* 350 */     bufferedReader.close();
/* 351 */     if (logResponseStatusText) {
/* 352 */       paramPlugin.getLogger().info("Sent data to bStats and received response: " + stringBuilder.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void runAsyncTask() {
/* 357 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)FactionWars.get(), () -> {
/*     */         
/*     */         });
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
/*     */   private static byte[] compress(String paramString) {
/* 380 */     if (paramString == null) {
/* 381 */       return null;
/*     */     }
/* 383 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 384 */     GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
/* 385 */     gZIPOutputStream.write(paramString.getBytes(StandardCharsets.UTF_8));
/* 386 */     gZIPOutputStream.close();
/* 387 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class CustomChart
/*     */   {
/*     */     final String chartId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CustomChart(String param1String) {
/* 404 */       if (param1String == null || param1String.isEmpty()) {
/* 405 */         throw new IllegalArgumentException("ChartId cannot be null or empty!");
/*     */       }
/* 407 */       this.chartId = param1String;
/*     */     }
/*     */     
/*     */     private JSONObject getRequestJsonObject() {
/* 411 */       JSONObject jSONObject = new JSONObject();
/* 412 */       jSONObject.put("chartId", this.chartId);
/*     */       try {
/* 414 */         JSONObject jSONObject1 = getChartData();
/* 415 */         if (jSONObject1 == null)
/*     */         {
/* 417 */           return null;
/*     */         }
/* 419 */         jSONObject.put("data", jSONObject1);
/* 420 */       } catch (Throwable throwable) {
/* 421 */         if (Metrics.logFailedRequests) {
/* 422 */           Bukkit.getLogger().log(Level.WARNING, "Failed to get data for custom chart with id " + this.chartId, throwable);
/*     */         }
/* 424 */         return null;
/*     */       } 
/* 426 */       return jSONObject;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract JSONObject getChartData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimplePie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<String> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimplePie(String param1String, Callable<String> param1Callable) {
/* 447 */       super(param1String);
/* 448 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 453 */       JSONObject jSONObject = new JSONObject();
/* 454 */       String str = this.callable.call();
/* 455 */       if (str == null || str.isEmpty())
/*     */       {
/* 457 */         return null;
/*     */       }
/* 459 */       jSONObject.put("value", str);
/* 460 */       return jSONObject;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedPie(String param1String, Callable<Map<String, Integer>> param1Callable) {
/* 478 */       super(param1String);
/* 479 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 484 */       JSONObject jSONObject1 = new JSONObject();
/* 485 */       JSONObject jSONObject2 = new JSONObject();
/* 486 */       Map map = this.callable.call();
/* 487 */       if (map == null || map.isEmpty())
/*     */       {
/* 489 */         return null;
/*     */       }
/* 491 */       boolean bool = true;
/* 492 */       for (Map.Entry entry : map.entrySet()) {
/* 493 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/* 496 */         bool = false;
/* 497 */         jSONObject2.put(entry.getKey(), entry.getValue());
/*     */       } 
/* 499 */       if (bool)
/*     */       {
/* 501 */         return null;
/*     */       }
/* 503 */       jSONObject1.put("values", jSONObject2);
/* 504 */       return jSONObject1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DrilldownPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Map<String, Integer>>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DrilldownPie(String param1String, Callable<Map<String, Map<String, Integer>>> param1Callable) {
/* 522 */       super(param1String);
/* 523 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     public JSONObject getChartData() {
/* 528 */       JSONObject jSONObject1 = new JSONObject();
/* 529 */       JSONObject jSONObject2 = new JSONObject();
/* 530 */       Map map = this.callable.call();
/* 531 */       if (map == null || map.isEmpty())
/*     */       {
/* 533 */         return null;
/*     */       }
/* 535 */       boolean bool = true;
/* 536 */       for (Map.Entry entry : map.entrySet()) {
/* 537 */         JSONObject jSONObject = new JSONObject();
/* 538 */         boolean bool1 = true;
/* 539 */         for (Map.Entry entry1 : ((Map)map.get(entry.getKey())).entrySet()) {
/* 540 */           jSONObject.put(entry1.getKey(), entry1.getValue());
/* 541 */           bool1 = false;
/*     */         } 
/* 543 */         if (!bool1) {
/* 544 */           bool = false;
/* 545 */           jSONObject2.put(entry.getKey(), jSONObject);
/*     */         } 
/*     */       } 
/* 548 */       if (bool)
/*     */       {
/* 550 */         return null;
/*     */       }
/* 552 */       jSONObject1.put("values", jSONObject2);
/* 553 */       return jSONObject1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SingleLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Integer> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SingleLineChart(String param1String, Callable<Integer> param1Callable) {
/* 571 */       super(param1String);
/* 572 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 577 */       JSONObject jSONObject = new JSONObject();
/* 578 */       int i = ((Integer)this.callable.call()).intValue();
/* 579 */       if (i == 0)
/*     */       {
/* 581 */         return null;
/*     */       }
/* 583 */       jSONObject.put("value", Integer.valueOf(i));
/* 584 */       return jSONObject;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MultiLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MultiLineChart(String param1String, Callable<Map<String, Integer>> param1Callable) {
/* 603 */       super(param1String);
/* 604 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 609 */       JSONObject jSONObject1 = new JSONObject();
/* 610 */       JSONObject jSONObject2 = new JSONObject();
/* 611 */       Map map = this.callable.call();
/* 612 */       if (map == null || map.isEmpty())
/*     */       {
/* 614 */         return null;
/*     */       }
/* 616 */       boolean bool = true;
/* 617 */       for (Map.Entry entry : map.entrySet()) {
/* 618 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/* 621 */         bool = false;
/* 622 */         jSONObject2.put(entry.getKey(), entry.getValue());
/*     */       } 
/* 624 */       if (bool)
/*     */       {
/* 626 */         return null;
/*     */       }
/* 628 */       jSONObject1.put("values", jSONObject2);
/* 629 */       return jSONObject1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleBarChart(String param1String, Callable<Map<String, Integer>> param1Callable) {
/* 648 */       super(param1String);
/* 649 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 654 */       JSONObject jSONObject1 = new JSONObject();
/* 655 */       JSONObject jSONObject2 = new JSONObject();
/* 656 */       Map map = this.callable.call();
/* 657 */       if (map == null || map.isEmpty())
/*     */       {
/* 659 */         return null;
/*     */       }
/* 661 */       for (Map.Entry entry : map.entrySet()) {
/* 662 */         JSONArray jSONArray = new JSONArray();
/* 663 */         jSONArray.add(entry.getValue());
/* 664 */         jSONObject2.put(entry.getKey(), jSONArray);
/*     */       } 
/* 666 */       jSONObject1.put("values", jSONObject2);
/* 667 */       return jSONObject1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, int[]>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedBarChart(String param1String, Callable<Map<String, int[]>> param1Callable) {
/* 686 */       super(param1String);
/* 687 */       this.callable = param1Callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JSONObject getChartData() {
/* 692 */       JSONObject jSONObject1 = new JSONObject();
/* 693 */       JSONObject jSONObject2 = new JSONObject();
/* 694 */       Map map = this.callable.call();
/* 695 */       if (map == null || map.isEmpty())
/*     */       {
/* 697 */         return null;
/*     */       }
/* 699 */       boolean bool = true;
/* 700 */       for (Map.Entry entry : map.entrySet()) {
/* 701 */         if (((int[])entry.getValue()).length == 0) {
/*     */           continue;
/*     */         }
/* 704 */         bool = false;
/* 705 */         JSONArray jSONArray = new JSONArray();
/* 706 */         for (int i : (int[])entry.getValue()) {
/* 707 */           jSONArray.add(Integer.valueOf(i));
/*     */         }
/* 709 */         jSONObject2.put(entry.getKey(), jSONArray);
/*     */       } 
/* 711 */       if (bool)
/*     */       {
/* 713 */         return null;
/*     */       }
/* 715 */       jSONObject1.put("values", jSONObject2);
/* 716 */       return jSONObject1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Metrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */