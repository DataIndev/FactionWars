/*    */ package io.github.guipenedo.factionwars.stats;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ public class MySQLCore
/*    */   implements DatabaseCore
/*    */ {
/*    */   private String url;
/*    */   private Properties info;
/*    */   private static final int MAX_CONNECTIONS = 8;
/* 17 */   private static ArrayList<Connection> pool = new ArrayList<>();
/*    */   
/*    */   public MySQLCore(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 20 */     this.info = new Properties();
/* 21 */     this.info.put("autoReconnect", "true");
/* 22 */     this.info.put("user", paramString2);
/* 23 */     this.info.put("password", paramString3);
/* 24 */     this.info.put("useUnicode", "true");
/* 25 */     this.info.put("characterEncoding", "utf8");
/* 26 */     this.url = "jdbc:mysql://" + paramString1 + ":" + paramString5 + "/" + paramString4;
/*    */     
/* 28 */     for (byte b = 0; b < 8; ) { pool.add(null); b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Connection getConnection() {
/* 38 */     for (byte b = 0; b < 8; b++) {
/* 39 */       Connection connection = pool.get(b);
/*    */       
/*    */       try {
/* 42 */         if (connection != null && !connection.isClosed() && 
/* 43 */           connection.isValid(10)) {
/* 44 */           return connection;
/*    */         }
/*    */ 
/*    */         
/* 48 */         connection = DriverManager.getConnection(this.url, this.info);
/*    */         
/* 50 */         pool.set(b, connection);
/*    */         
/* 52 */         return connection;
/*    */       }
/* 54 */       catch (SQLException sQLException) {
/* 55 */         sQLException.printStackTrace();
/*    */       } 
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void queue(BufferStatement paramBufferStatement) {
/*    */     try {
/* 64 */       Connection connection = getConnection();
/* 65 */       while (connection == null) { try {
/* 66 */           Thread.sleep(15L);
/* 67 */         } catch (InterruptedException interruptedException) {}
/*    */ 
/*    */ 
/*    */         
/* 71 */         connection = getConnection(); }
/*    */       
/* 73 */       PreparedStatement preparedStatement = paramBufferStatement.prepareStatement(connection);
/* 74 */       preparedStatement.execute();
/* 75 */       preparedStatement.close();
/*    */     }
/* 77 */     catch (SQLException sQLException) {
/* 78 */       sQLException.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public void flush() {}
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\MySQLCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */