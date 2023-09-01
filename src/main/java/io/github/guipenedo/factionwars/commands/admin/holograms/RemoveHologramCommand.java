/*    */ package io.github.guipenedo.factionwars.commands.admin.holograms;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.managers.HologramManager;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class RemoveHologramCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 14 */     if (!paramCommandSender.hasPermission("factionwars.admin.removehologram") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 15 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 16 */       return false;
/*    */     } 
/*    */     
/* 19 */     if (!(paramCommandSender instanceof Player)) {
/* 20 */       paramCommandSender.sendMessage("This command may only be used by players!");
/* 21 */       return false;
/*    */     } 
/*    */     
/* 24 */     ArrayList<Location> arrayList = new ArrayList();
/* 25 */     ArrayList<String> arrayList1 = new ArrayList();
/* 26 */     for (Location location : (HologramManager.get()).locations) {
/* 27 */       if (location.distanceSquared(((Player)paramCommandSender).getLocation()) < 25.0D) {
/* 28 */         arrayList.add(location);
/* 29 */         String str = location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
/* 30 */         arrayList1.add(str);
/*    */       } 
/*    */     } 
/*    */     
/* 34 */     (HologramManager.get()).locations.removeAll(arrayList);
/*    */     
/* 36 */     FactionWars.getMainConfig().getConfig().set("holograms.locations", Boolean.valueOf(FactionWars.getMainConfig().getConfig().getStringList("holograms.locations").removeAll(arrayList1)));
/* 37 */     FactionWars.getMainConfig().saveConfig();
/* 38 */     for (String str : new ArrayList((HologramManager.get()).holograms.keySet())) {
/* 39 */       HologramManager.get().delete(str);
/* 40 */       HologramManager.get().create(str);
/*    */     } 
/* 42 */     paramCommandSender.sendMessage("§aRemoved §c" + arrayList.size() + " §aholograms.");
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\holograms\RemoveHologramCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */