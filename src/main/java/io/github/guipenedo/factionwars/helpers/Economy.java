/*    */ package io.github.guipenedo.factionwars.helpers;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ 
/*    */ public class Economy
/*    */ {
/*    */   public Economy() {
/* 11 */     setupEconomy();
/*    */   }
/*    */ 
/*    */   
/*    */   private net.milkbowl.vault.economy.Economy vault;
/*    */ 
/*    */   
/*    */   private boolean setupEconomy() {
/* 19 */     if (FactionWars.get().getServer().getPluginManager().isPluginEnabled("Vault")) {
/* 20 */       RegisteredServiceProvider registeredServiceProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
/* 21 */       if (registeredServiceProvider != null) {
/* 22 */         this.vault = (net.milkbowl.vault.economy.Economy)registeredServiceProvider.getProvider();
/*    */       }
/*    */     } 
/*    */     
/* 26 */     return (this.vault != null);
/*    */   }
/*    */   
/*    */   boolean isValid() {
/* 30 */     return (this.vault != null);
/*    */   }
/*    */   
/*    */   public void transaction(double paramDouble) {
/* 34 */     String str = FactionWars.getMainConfig().getConfig().getString("bank-account");
/* 35 */     if (!str.isEmpty()) {
/* 36 */       FactionWars.debug("Transaction: " + paramDouble);
/* 37 */       if (paramDouble > 0.0D) {
/* 38 */         withdraw(str, paramDouble);
/* 39 */       } else if (paramDouble < 0.0D) {
/* 40 */         deposit(str, -paramDouble);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean deposit(String paramString, double paramDouble) {
/* 46 */     return this.vault.depositPlayer(Bukkit.getServer().getOfflinePlayer(paramString), paramDouble).transactionSuccess();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean deposit(UUID paramUUID, double paramDouble) {
/* 51 */     return this.vault.depositPlayer(Bukkit.getServer().getOfflinePlayer(paramUUID), paramDouble).transactionSuccess();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean withdraw(String paramString, double paramDouble) {
/* 56 */     return this.vault.withdrawPlayer(Bukkit.getServer().getOfflinePlayer(paramString), paramDouble).transactionSuccess();
/*    */   }
/*    */   
/*    */   public boolean withdraw(UUID paramUUID, double paramDouble) {
/* 60 */     return this.vault.withdrawPlayer(Bukkit.getServer().getOfflinePlayer(paramUUID), paramDouble).transactionSuccess();
/*    */   }
/*    */   
/*    */   public double getBalance(UUID paramUUID) {
/* 64 */     return this.vault.getBalance(Bukkit.getServer().getOfflinePlayer(paramUUID));
/*    */   }
/*    */   
/*    */   public double getBalance(String paramString) {
/* 68 */     return this.vault.getBalance(Bukkit.getServer().getOfflinePlayer(paramString));
/*    */   }
/*    */   
/*    */   public void transfer(String paramString1, String paramString2, double paramDouble) {
/* 72 */     this.vault.withdrawPlayer(Bukkit.getServer().getOfflinePlayer(paramString1), paramDouble);
/* 73 */     this.vault.depositPlayer(Bukkit.getServer().getOfflinePlayer(paramString2), paramDouble);
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\helpers\Economy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */