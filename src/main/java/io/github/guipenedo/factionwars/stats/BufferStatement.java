/*    */ package io.github.guipenedo.factionwars.stats;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BufferStatement
/*    */ {
/*    */   private final Object[] values;
/*    */   private final String query;
/*    */   
/*    */   BufferStatement(String paramString, Object... paramVarArgs) {
/* 18 */     this.query = paramString;
/* 19 */     this.values = paramVarArgs;
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
/*    */ 
/*    */ 
/*    */   
/*    */   PreparedStatement prepareStatement(Connection paramConnection) {
/* 34 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(this.query);
/* 35 */     for (byte b = 1; b <= this.values.length; b++) {
/* 36 */       preparedStatement.setObject(b, this.values[b - 1]);
/*    */     }
/* 38 */     return preparedStatement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return "Query: " + this.query + ", values: " + Arrays.toString(this.values);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\BufferStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */