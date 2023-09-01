/*    */ package io.github.guipenedo.factionwars.models;
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.helpers.LegacyUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class Config {
/*    */   private FileConfiguration config;
/*    */   
/*    */   public Config(String paramString) {
/* 16 */     this.filename = paramString;
/* 17 */     reloadConfig();
/*    */   }
/*    */   private final String filename; private boolean reloadDefaults = false;
/*    */   public void loadDefaults() {
/* 21 */     this.reloadDefaults = true;
/* 22 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(getDefaults()));
/* 23 */     boolean bool = false;
/* 24 */     FileConfiguration fileConfiguration = getConfig();
/* 25 */     for (String str : yamlConfiguration.getKeys(true)) {
/* 26 */       if (!fileConfiguration.contains(str)) {
/* 27 */         FactionWars.get().getLogger().info("Setting " + str + " not found! Adding with value: " + yamlConfiguration.get(str));
/* 28 */         bool = true;
/* 29 */         fileConfiguration.set(str, yamlConfiguration.get(str));
/*    */       } 
/*    */     } 
/* 32 */     if (bool)
/* 33 */       saveConfig(); 
/*    */   }
/*    */   
/*    */   public boolean contains(String paramString) {
/* 37 */     return getConfig().contains(paramString);
/*    */   }
/*    */   
/*    */   private InputStream getDefaults() {
/* 41 */     return FactionWars.get().getResource((LegacyUtil.is1_13 && FactionWars.get().getResource(this.filename + ".1_13") != null) ? (this.filename + ".1_13") : this.filename);
/*    */   }
/*    */   
/*    */   private void reloadConfig() {
/* 45 */     File file = getFile();
/* 46 */     if (!file.exists()) {
/* 47 */       file.getParentFile().mkdirs();
/*    */       try {
/* 49 */         if (file.createNewFile())
/* 50 */         { InputStream inputStream = getDefaults();
/* 51 */           if (inputStream != null) {
/*    */             try {
/* 53 */               FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 54 */               byte[] arrayOfByte = new byte[1024];
/*    */               int i;
/* 56 */               while ((i = inputStream.read(arrayOfByte)) > 0) {
/* 57 */                 fileOutputStream.write(arrayOfByte, 0, i);
/*    */               }
/* 59 */               fileOutputStream.close();
/* 60 */               inputStream.close();
/* 61 */             } catch (Exception exception) {
/* 62 */               exception.printStackTrace();
/*    */             } 
/*    */           }
/* 65 */           FactionWars.get().getLogger().info("Successfully created config file " + this.filename); }
/*    */         else
/* 67 */         { FactionWars.get().getLogger().warning("Failed to create config file " + this.filename); } 
/* 68 */       } catch (IOException iOException) {
/* 69 */         iOException.printStackTrace();
/*    */       } 
/*    */     } 
/* 72 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
/* 73 */     if (this.reloadDefaults)
/* 74 */       loadDefaults(); 
/*    */   }
/*    */   
/*    */   private File getFile() {
/* 78 */     return new File(FactionWars.get().getDataFolder(), this.filename);
/*    */   }
/*    */   
/*    */   public void saveConfig() {
/*    */     try {
/* 83 */       this.config.save(getFile());
/* 84 */     } catch (IOException iOException) {
/* 85 */       iOException.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public FileConfiguration getConfig() {
/* 90 */     if (this.config == null)
/* 91 */       reloadConfig(); 
/* 92 */     return this.config;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\models\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */