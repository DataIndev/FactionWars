/*     */ package io.github.guipenedo.factionwars.commands.admin;
/*     */ import io.github.guipenedo.factionwars.commands.admin.gamemodes.ListGamemodesCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.holograms.RemoveHologramCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.AddKitCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.EditKitContentsCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.ListKitsCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.RemoveKitCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.SetKitContentsCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.kits.SetKitPriceCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.maps.MapSetupCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.maps.RemoveMapCommand;
/*     */ import io.github.guipenedo.factionwars.commands.admin.maps.SetMapGamemode;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MainCommand implements CommandExecutor {
/*     */   public boolean onCommand(@NotNull CommandSender paramCommandSender, @NotNull Command paramCommand, @NotNull String paramString, String[] paramArrayOfString) {
/*  20 */     if (paramArrayOfString.length < 1) {
/*  21 */       showHelp(paramCommandSender, "");
/*  22 */       return false;
/*     */     } 
/*     */     
/*  25 */     if (Util.runMapChecks(paramCommandSender)) {
/*  26 */       return true;
/*     */     }
/*  28 */     String str = paramArrayOfString[0];
/*  29 */     paramArrayOfString = Arrays.<String>copyOfRange(paramArrayOfString, 1, paramArrayOfString.length);
/*     */     
/*  31 */     if (str.equalsIgnoreCase("addmap") || str.equalsIgnoreCase("addarena") || str.equalsIgnoreCase("newmap")) {
/*  32 */       (new AddMapCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  33 */     } else if (str.equalsIgnoreCase("listmaps") || str.equalsIgnoreCase("lsm") || str.equalsIgnoreCase("lsmaps")) {
/*  34 */       (new ListMapsCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  35 */     } else if (str.equalsIgnoreCase("setspawn") || str.equalsIgnoreCase("setl") || str.equalsIgnoreCase("setlocation")) {
/*  36 */       (new AddMapLocationCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  37 */     } else if (str.equalsIgnoreCase("addkit")) {
/*  38 */       (new AddKitCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  39 */     } else if (str.equalsIgnoreCase("setkit")) {
/*  40 */       (new SetKitContentsCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  41 */     } else if (str.equalsIgnoreCase("removekit")) {
/*  42 */       (new RemoveKitCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  43 */     } else if (str.equalsIgnoreCase("removemap")) {
/*  44 */       (new RemoveMapCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  45 */     } else if (str.equalsIgnoreCase("setgamemode") || str.equalsIgnoreCase("setgm")) {
/*  46 */       (new SetMapGamemode()).onCommand(paramCommandSender, paramArrayOfString);
/*  47 */     } else if (str.equalsIgnoreCase("listgm") || str.equalsIgnoreCase("listgamemodes")) {
/*  48 */       (new ListGamemodesCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  49 */     } else if (str.equalsIgnoreCase("setgmitem") || str.equalsIgnoreCase("setgamemodeitem")) {
/*  50 */       (new SetGamemodeItemCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  51 */     } else if (str.equalsIgnoreCase("mapsetup") || str.equalsIgnoreCase("setupmap")) {
/*  52 */       (new MapSetupCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  53 */     } else if (str.equalsIgnoreCase("editkit")) {
/*  54 */       (new EditKitContentsCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  55 */     } else if (str.equalsIgnoreCase("listkits")) {
/*  56 */       (new ListKitsCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  57 */     } else if (str.equalsIgnoreCase("setkititem")) {
/*  58 */       (new SetKitItemCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  59 */     } else if (str.equalsIgnoreCase("setkitprice")) {
/*  60 */       (new SetKitPriceCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  61 */     } else if (str.equalsIgnoreCase("version") || str.equalsIgnoreCase("v")) {
/*  62 */       (new VersionCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  63 */     } else if (str.equalsIgnoreCase("reload") || str.equalsIgnoreCase("rl")) {
/*  64 */       (new ReloadCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  65 */     } else if (str.equalsIgnoreCase("start")) {
/*  66 */       (new StartCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  67 */     } else if (str.equalsIgnoreCase("stats")) {
/*  68 */       (new StatsCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  69 */     } else if (str.equalsIgnoreCase("sethologram")) {
/*  70 */       (new SetHologramCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*  71 */     } else if (str.equalsIgnoreCase("removehologram")) {
/*  72 */       (new RemoveHologramCommand()).onCommand(paramCommandSender, paramArrayOfString);
/*     */     } else {
/*  74 */       showHelp(paramCommandSender, str);
/*  75 */     }  return true;
/*     */   }
/*     */   
/*     */   private void showHelp(CommandSender paramCommandSender, String paramString) {
/*  79 */     if (!paramCommandSender.hasPermission("factionwars.admin"))
/*  80 */       return;  switch (paramString) {
/*     */       case "map":
/*     */       case "maps":
/*  83 */         paramCommandSender.sendMessage("§9== §c§lMap Commands §9==");
/*  84 */         paramCommandSender.sendMessage("§a/fw addmap <map name> §6- §3add map with name <map name>");
/*  85 */         paramCommandSender.sendMessage("§a/fw mapsetup <map id> §6- §3setup instructions for map <map id>");
/*  86 */         paramCommandSender.sendMessage("§a/fw removemap <map id> §6- §3remove map with id <map id>");
/*  87 */         paramCommandSender.sendMessage("§a/fw listmaps §6- §3list of maps and their ids");
/*  88 */         paramCommandSender.sendMessage("§a/fw setgamemode <map id> <gamemode> §6- §3sets gamemode for map <map id>");
/*  89 */         paramCommandSender.sendMessage("§a/fw setlocation <map id> <location> §6- §3set location for map");
/*     */         return;
/*     */       case "kit":
/*     */       case "kits":
/*  93 */         paramCommandSender.sendMessage("§9== §c§lKit Commands §9==");
/*  94 */         paramCommandSender.sendMessage("§a/fw addkit <kit name> §6- §3add kit with name <kit name>");
/*  95 */         paramCommandSender.sendMessage("§a/fw removekit <kit id> §6- §3remove kit with id <kit id>");
/*  96 */         paramCommandSender.sendMessage("§a/fw listkits §6- §3list of kits and their ids");
/*  97 */         paramCommandSender.sendMessage("§a/fw setkit <kit id> §6- §3set kit's contents to your inventory, armor and potion effects");
/*  98 */         paramCommandSender.sendMessage("§a/fw editkit <kit id> §6- §3give yourself the kit to edit");
/*  99 */         paramCommandSender.sendMessage("§a/fw setkititem <kit id> §6- §3set GUI display item to the one in your hand");
/* 100 */         paramCommandSender.sendMessage("§a/fw setkitprice <kit id> §6- §3set kit price");
/*     */         return;
/*     */       case "holograms":
/*     */       case "holo":
/*     */       case "hologram":
/* 105 */         paramCommandSender.sendMessage("§9== §c§lHologram Commands §9==");
/* 106 */         paramCommandSender.sendMessage("§a/fw sethologram §6- §3set hologram location");
/* 107 */         paramCommandSender.sendMessage("§a/fw removehologram §6- §3removes all holograms within a 5 block radius");
/*     */         return;
/*     */       case "gamemodes":
/*     */       case "gamemode":
/*     */       case "gms":
/*     */       case "gm":
/* 113 */         paramCommandSender.sendMessage("§9== §c§lGamemode Commands §9==");
/* 114 */         paramCommandSender.sendMessage("§a/fw listgamemodes §6- §3list gamemodes");
/* 115 */         paramCommandSender.sendMessage("§a/fw setgmitem <gamemode> §6- §3set GUI display item to the one in your hand");
/*     */         return;
/*     */     } 
/* 118 */     paramCommandSender.sendMessage("§9== §c§lHelp Commands §9==");
/* 119 */     paramCommandSender.sendMessage("§a/fw version §6- §3plugin version");
/* 120 */     paramCommandSender.sendMessage("§a/fw map §6- §3map help menu");
/* 121 */     paramCommandSender.sendMessage("§a/fw kit §6- §3kit help menu");
/* 122 */     paramCommandSender.sendMessage("§a/fw hologram §6- §3hologram help menu");
/* 123 */     paramCommandSender.sendMessage("§a/fw gamemode §6- §3gamemode help menu");
/* 124 */     paramCommandSender.sendMessage("§a/fw reload §6- §3reload maps, kits, messages and config");
/* 125 */     paramCommandSender.sendMessage("§a/fw start <team1> <team2> [map] §6- §3start a match between team1 and team2");
/* 126 */     paramCommandSender.sendMessage("§a/fw stats <team> <kills/deaths/wins/losts> <offset> §6- §3change a team's kills, deaths, wins or losts by the given offset");
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\commands\admin\MainCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */