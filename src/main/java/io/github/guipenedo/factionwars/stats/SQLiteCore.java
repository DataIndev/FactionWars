/*     */ package io.github.guipenedo.factionwars.stats;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class SQLiteCore
/*     */   implements DatabaseCore
/*     */ {
/*     */   private Connection connection;
/*     */   private final File dbFile;
/*     */   private volatile Thread watcher;
/*  17 */   private final LinkedList<BufferStatement> queue = new LinkedList<>();
/*     */   
/*     */   public SQLiteCore(File paramFile) {
/*  20 */     this.dbFile = paramFile;
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
/*     */   public Connection getConnection() {
/*     */     try {
/*  33 */       if (this.connection != null && !this.connection.isClosed()) {
/*  34 */         return this.connection;
/*     */       }
/*     */     }
/*  37 */     catch (SQLException sQLException) {
/*  38 */       sQLException.printStackTrace();
/*     */     } 
/*     */     
/*  41 */     if (this.dbFile.exists()) {
/*     */       
/*     */       try {
/*  44 */         Class.forName("org.sqlite.JDBC");
/*  45 */         this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbFile);
/*  46 */         return this.connection;
/*     */       }
/*  48 */       catch (ClassNotFoundException|SQLException classNotFoundException) {
/*  49 */         classNotFoundException.printStackTrace();
/*  50 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  57 */       this.dbFile.createNewFile();
/*     */ 
/*     */       
/*  60 */       return getConnection();
/*  61 */     } catch (IOException iOException) {
/*  62 */       iOException.printStackTrace();
/*  63 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queue(BufferStatement paramBufferStatement) {
/*  71 */     synchronized (this.queue) {
/*  72 */       this.queue.add(paramBufferStatement);
/*     */     } 
/*     */     
/*  75 */     if (this.watcher == null || !this.watcher.isAlive()) {
/*  76 */       startWatcher();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/*  82 */     while (!this.queue.isEmpty()) {
/*     */       BufferStatement bufferStatement;
/*  84 */       synchronized (this.queue) {
/*  85 */         bufferStatement = this.queue.removeFirst();
/*     */       } 
/*     */       
/*  88 */       synchronized (this.dbFile) {
/*     */         try {
/*  90 */           PreparedStatement preparedStatement = bufferStatement.prepareStatement(getConnection());
/*  91 */           preparedStatement.execute();
/*  92 */           preparedStatement.close();
/*     */         }
/*  94 */         catch (SQLException sQLException) {
/*  95 */           sQLException.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 103 */     flush();
/*     */     try {
/* 105 */       this.connection.close();
/* 106 */     } catch (SQLException sQLException) {
/* 107 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startWatcher() {
/* 112 */     this.watcher = new Thread(() -> {
/*     */           
/*     */           try {
/*     */             Thread.sleep(30000L);
/* 116 */           } catch (InterruptedException interruptedException) {}
/*     */           
/*     */           flush();
/*     */         });
/* 120 */     this.watcher.start();
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\stats\SQLiteCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */