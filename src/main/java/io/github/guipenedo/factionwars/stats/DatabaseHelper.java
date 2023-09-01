/*     */ package io.github.guipenedo.factionwars.stats;
/*     */ 
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.models.FactionData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class DatabaseHelper
/*     */ {
/*     */   private static DatabaseHelper instance;
/*     */   
/*     */   public static DatabaseHelper get() {
/*  17 */     if (instance == null)
/*  18 */       instance = new DatabaseHelper(); 
/*  19 */     return instance;
/*     */   }
/*     */   
/*     */   public static ArrayList<String> getAllIds() {
/*  23 */     ArrayList<String> arrayList = new ArrayList();
/*     */     try {
/*  25 */       Database database = FactionWars.getDB();
/*  26 */       PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT id FROM `" + database.getTable() + "`");
/*  27 */       ResultSet resultSet = preparedStatement.executeQuery();
/*  28 */       while (resultSet.next()) {
/*  29 */         arrayList.add(resultSet.getString("id"));
/*     */       }
/*  31 */     } catch (SQLException sQLException) {
/*  32 */       sQLException.printStackTrace();
/*     */     } 
/*  34 */     return arrayList;
/*     */   }
/*     */   
/*     */   public void setup(Database paramDatabase) {
/*     */     try {
/*  39 */       Statement statement = paramDatabase.getConnection().createStatement();
/*  40 */       statement.execute("CREATE TABLE IF NOT EXISTS `" + paramDatabase.getTable() + "` (id varchar(36), name varchar(100), kills int, deaths int, wins int, losts int, PRIMARY KEY (id));");
/*  41 */       migrate(paramDatabase);
/*  42 */     } catch (SQLException sQLException) {
/*  43 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void migrate(Database paramDatabase) {
/*     */     try {
/*  49 */       Statement statement = paramDatabase.getConnection().createStatement();
/*  50 */       statement.execute("ALTER TABLE `" + paramDatabase.getTable() + "` ADD name varchar(100);");
/*  51 */       FactionWars.debug("Migrating database");
/*  52 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFactionStats(FactionData paramFactionData) {
/*  57 */     if (paramFactionData == null)
/*     */       return;  try {
/*  59 */       Database database = FactionWars.getDB();
/*  60 */       PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT * FROM `" + database.getTable() + "` WHERE id = ?");
/*  61 */       preparedStatement.setString(1, paramFactionData.getFactionId());
/*  62 */       ResultSet resultSet = preparedStatement.executeQuery();
/*  63 */       if (resultSet.next()) {
/*  64 */         paramFactionData.setDeaths(resultSet.getInt("deaths"));
/*  65 */         paramFactionData.setKills(resultSet.getInt("kills"));
/*  66 */         paramFactionData.setLosts(resultSet.getInt("losts"));
/*  67 */         paramFactionData.setWins(resultSet.getInt("wins"));
/*     */       } else {
/*  69 */         paramFactionData.setWins(0);
/*  70 */         paramFactionData.setLosts(0);
/*  71 */         paramFactionData.setKills(0);
/*  72 */         paramFactionData.setDeaths(0);
/*  73 */         preparedStatement = database.getConnection().prepareStatement("INSERT INTO `" + database.getTable() + "` (id, wins, losts, deaths, kills, name) VALUES (?, 0, 0, 0, 0, ?)");
/*  74 */         preparedStatement.setString(1, paramFactionData.getFactionId());
/*  75 */         preparedStatement.setString(2, paramFactionData.getName());
/*  76 */         preparedStatement.execute();
/*     */       } 
/*  78 */       paramFactionData.setInitialized();
/*  79 */     } catch (SQLException sQLException) {
/*  80 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveStats(FactionData paramFactionData) {
/*  85 */     if (paramFactionData == null || !paramFactionData.isInitialized())
/*     */       return;  try {
/*  87 */       Database database = FactionWars.getDB();
/*  88 */       if (database == null) {
/*  89 */         FactionWars.debug("DB NULL"); return;
/*     */       } 
/*  91 */       if (database.getConnection() == null) {
/*  92 */         FactionWars.debug("DB get connection NULL");
/*     */         return;
/*     */       } 
/*  95 */       PreparedStatement preparedStatement = database.getConnection().prepareStatement("UPDATE `" + database.getTable() + "` SET wins = ?, losts = ?, kills = ?, deaths = ?, name = ? WHERE id = ?");
/*  96 */       preparedStatement.setInt(1, paramFactionData.getWins());
/*  97 */       preparedStatement.setInt(2, paramFactionData.getLosts());
/*  98 */       preparedStatement.setInt(3, paramFactionData.getKills());
/*  99 */       preparedStatement.setInt(4, paramFactionData.getDeaths());
/* 100 */       preparedStatement.setString(5, (paramFactionData.getName() == null) ? "" : paramFactionData.getName());
/* 101 */       preparedStatement.setString(6, paramFactionData.getFactionId());
/* 102 */       preparedStatement.executeUpdate();
/* 103 */     } catch (SQLException sQLException) {
/* 104 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeFactionData(String paramString) {
/*     */     try {
/* 110 */       Database database = FactionWars.getDB();
/* 111 */       PreparedStatement preparedStatement = database.getConnection().prepareStatement("DELETE FROM `" + database.getTable() + "` WHERE id = ?");
/* 112 */       preparedStatement.setString(1, paramString);
/* 113 */       preparedStatement.executeUpdate();
/* 114 */     } catch (SQLException sQLException) {
/* 115 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\DatabaseHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */