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
/*    */ public class SetHologramCommand
/*    */ {
/*    */   public boolean onCommand(CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 14 */     if (!paramCommandSender.hasPermission("factionwars.admin.sethologram") && !paramCommandSender.hasPermission("factionwars.admin.*")) {
/* 15 */       paramCommandSender.sendMessage("§cYou do not have permission to use this command!");
/* 16 */       return false;
/*    */     } 
/*    */     
/* 19 */     if (paramArrayOfString.length != 0) {
/* 20 */       paramCommandSender.sendMessage("§cUsage: /fw sethologram");
/*    */     }
/*    */     
/* 23 */     Player player = (Player)paramCommandSender;
/* 24 */     Location location = player.getLocation();
/* 25 */     location.setY(location.getY() + 3.0D);
/*    */     
/* 27 */     (HologramManager.get()).locations.add(location);
/*    */     
/* 29 */     String str = location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
/*    */     
/* 31 */     ArrayList<String> arrayList = new ArrayList();
/* 32 */     arrayList.add(str);
/*    */     
/* 34 */     if (FactionWars.getMainConfig().getConfig().contains("holograms.locations")) {
/* 35 */       arrayList.addAll(FactionWars.getMainConfig().getConfig().getStringList("holograms.locations"));
/*    */     }
/*    */     
/* 38 */     FactionWars.getMainConfig().getConfig().set("holograms.locations", arrayList);
/* 39 */     FactionWars.getMainConfig().saveConfig();
/* 40 */     for (String str1 : new ArrayList((HologramManager.get()).holograms.keySet())) {
/* 41 */       HologramManager.get().delete(str1);
/* 42 */       HologramManager.get().create(str1);
/*    */     } 
/* 44 */     player.sendMessage("§aHologram location saved successfully!");
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\holograms\SetHologramCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */