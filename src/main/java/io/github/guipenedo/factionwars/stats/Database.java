/*    */ package io.github.guipenedo.factionwars.stats;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class Database {
/*    */   private DatabaseCore core;
/*    */   private String table;
/*    */   
/*    */   public String getTable() {
/* 11 */     return this.table;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Database(DatabaseCore paramDatabaseCore, String paramString) {
/*    */     try {
/*    */       try {
/* 25 */         if (!paramDatabaseCore.getConnection().isValid(10)) {
/* 26 */           throw new ConnectionException("Database does not appear to be valid!");
/*    */         }
/*    */       }
/* 29 */       catch (AbstractMethodError abstractMethodError) {}
/*    */ 
/*    */     
/*    */     }
/* 33 */     catch (SQLException sQLException) {
/* 34 */       throw new ConnectionException(sQLException.getMessage());
/*    */     } 
/*    */     
/* 37 */     this.core = paramDatabaseCore;
/* 38 */     this.table = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DatabaseCore getCore() {
/* 46 */     return this.core;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Connection getConnection() {
/* 54 */     return this.core.getConnection();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String paramString, Object... paramVarArgs) {
/* 63 */     BufferStatement bufferStatement = new BufferStatement(paramString, paramVarArgs);
/* 64 */     this.core.queue(bufferStatement);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 71 */     this.core.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public static class ConnectionException
/*    */     extends Exception
/*    */   {
/*    */     private static final long serialVersionUID = 8348749992936357317L;
/*    */ 
/*    */     
/*    */     ConnectionException(String param1String) {
/* 82 */       super(param1String);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\Database.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */