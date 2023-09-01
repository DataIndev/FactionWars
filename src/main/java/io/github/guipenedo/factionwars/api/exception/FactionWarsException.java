/*    */ package io.github.guipenedo.factionwars.api.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class FactionWarsException
/*    */   extends Exception
/*    */ {
/*    */   protected FactionWarsException() {}
/*    */   
/*    */   protected FactionWarsException(String paramString) {
/* 17 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected FactionWarsException(String paramString, Throwable paramThrowable) {
/* 27 */     super(paramString, paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected FactionWarsException(Throwable paramThrowable) {
/* 36 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\api\exception\FactionWarsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */