/*     */ package io.github.guipenedo.factionwars.gamemodes;
/*     */ import com.gmail.filoghost.holographicdisplays.api.Hologram;
/*     */ import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.managers.ScoreboardManager;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class GamemodeHALO implements GamemodeManager, Listener {
/*     */   private final String map;
/*  24 */   private final String msg = "gamemodes.halo.";
/*  25 */   private int range = 3; private int toWin = 3;
/*     */   private int conq1;
/*     */   
/*     */   public GamemodeHALO(WarMap paramWarMap, ConfigurationSection paramConfigurationSection) {
/*  29 */     this.map = paramWarMap.getId();
/*  30 */     this.range = paramConfigurationSection.getInt("gamemode-settings.range", this.range);
/*  31 */     this.toWin = paramConfigurationSection.getInt("gamemode-settings.to-win", this.toWin);
/*  32 */     FactionWars.get().getServer().getPluginManager().registerEvents(this, (Plugin)FactionWars.get());
/*  33 */     updateLocations(paramWarMap);
/*     */   }
/*     */   private int conq2; private ArrayList<Flag> flags;
/*     */   public void updateLocations(WarMap paramWarMap) {
/*  37 */     if (paramWarMap.isSetup(this)) {
/*  38 */       this.flags = new ArrayList<>(Arrays.asList(new Flag[] { new Flag(((ArrayList<Location>)paramWarMap.getLocations().get("flag1")).get(0), 1), new Flag(((ArrayList<Location>)paramWarMap.getLocations().get("flag2")).get(0), 2) }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void startMatch() {
/*  43 */     this.conq1 = 0;
/*  44 */     this.conq2 = 0;
/*  45 */     for (Flag flag : this.flags)
/*  46 */       updateHologram(flag); 
/*     */   }
/*     */   
/*     */   private void updateHologram(Flag paramFlag) {
/*  50 */     if (!FactionWars.get().getServer().getPluginManager().isPluginEnabled("HolographicDisplays"))
/*  51 */       return;  Location location = paramFlag.getLocation().clone();
/*  52 */     location.setY(location.getY() + 2.0D);
/*  53 */     if (paramFlag.getHologram() == null)
/*  54 */       paramFlag.setHologram(HologramsAPI.createHologram((Plugin)FactionWars.get(), location)); 
/*  55 */     Hologram hologram = paramFlag.getHologram();
/*  56 */     hologram.clearLines();
/*  57 */     hologram.appendTextLine(paramFlag.getColor().toString() + "⚑ ⚑ ⚑");
/*  58 */     hologram.appendTextLine(color(paramFlag.getColor()) + " " + Util.getPlainMessage("gamemodes.halo.hologram.flag"));
/*  59 */     hologram.appendTextLine(Util.getPlainMessage("gamemodes.halo.hologram.status") + ": §l" + (paramFlag.isTaken() ? Util.getPlainMessage("gamemodes.halo.hologram.taken") : Util.getPlainMessage("gamemodes.halo.hologram.defended")));
/*     */   }
/*     */ 
/*     */   
/*     */   public void timeOut() {
/*  64 */     MatchManager.get().tied(WarMap.getMap(this.map));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScoreboard() {
/*  69 */     Flag flag1 = this.flags.get(0), flag2 = this.flags.get(1);
/*  70 */     WarMap warMap = WarMap.getMap(this.map);
/*  71 */     ScoreboardManager.get().display(warMap.getPlayerList(), warMap, new String[] {
/*  72 */           Util.getPlainMessage("gamemodes.halo.scoreboard.conquers"), 
/*  73 */           Util.getTeam1Color() + FactionWars.getHandler().getTeamName(warMap.getF1()), this.conq1 + "/" + this.toWin + " §6⚑", 
/*     */           
/*  75 */           Util.getTeam2Color() + FactionWars.getHandler().getTeamName(warMap.getF2()), this.conq2 + "/" + this.toWin + " §6⚑", "", 
/*     */ 
/*     */           
/*  78 */           Util.getPlainMessage("gamemodes.halo.scoreboard.takers"), "§f" + flag1
/*  79 */           .getColor() + "⚑ §f" + (!flag1.isTaken() ? Util.getPlainMessage("gamemodes.halo.scoreboard.untaken") : FactionWars.get().getServer().getPlayer(flag1.getTaker()).getName()), "§f" + flag2
/*  80 */           .getColor() + "⚑ §f" + (!flag2.isTaken() ? Util.getPlainMessage("gamemodes.halo.scoreboard.untaken") : FactionWars.get().getServer().getPlayer(flag2.getTaker()).getName())
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill(UUID paramUUID) {}
/*     */ 
/*     */   
/*     */   public void death(UUID paramUUID) {
/*  89 */     for (Flag flag : this.flags) {
/*  90 */       if (flag.isTaken() && flag.getTaker().equals(paramUUID)) {
/*  91 */         flag.setTaker(null);
/*  92 */         WarMap.getMap(this.map).message(Util.getMessage("gamemodes.halo.lost-flag", Util.getVars(new Object[] { "player", FactionWars.get().getServer().getPlayer(paramUUID).getName(), "color", color(flag.getColor()) })));
/*  93 */         updateHologram(flag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*  99 */     for (Flag flag : this.flags) {
/* 100 */       flag.setTaker(null);
/* 101 */       if (flag.getHologram() != null)
/* 102 */         flag.getHologram().delete(); 
/* 103 */       flag.setHologram(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRespawn(UUID paramUUID) {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(PlayerMoveEvent paramPlayerMoveEvent) {
/* 114 */     int i = this.range * this.range;
/* 115 */     String str = FactionWars.getHandler().getPlayerTeam(paramPlayerMoveEvent.getPlayer());
/* 116 */     Flag flag1 = ((Flag)this.flags.get(0)).getOwner().equals(str) ? this.flags.get(1) : this.flags.get(0), flag2 = flag1.equals(this.flags.get(0)) ? this.flags.get(1) : this.flags.get(0);
/* 117 */     if (flag2.getLocation().distanceSquared(paramPlayerMoveEvent.getTo()) <= i && flag1.isTaken() && flag1.getTaker().equals(paramPlayerMoveEvent.getPlayer().getUniqueId())) {
/* 118 */       if (flag2.isTaken()) {
/* 119 */         paramPlayerMoveEvent.getPlayer().sendMessage(Util.getMessage("gamemodes.halo.yours-taken"));
/*     */       } else {
/* 121 */         if (str.equals(WarMap.getMap(this.map).getF1()))
/* 122 */         { this.conq1++; }
/* 123 */         else { this.conq2++; }
/* 124 */          WarMap.getMap(this.map).message(Util.getMessage("gamemodes.halo.capture", Util.getVars(new Object[] { "player", paramPlayerMoveEvent.getPlayer().getName(), "color", color(flag1.getColor()) })));
/* 125 */         flag1.setTaker(null);
/* 126 */         checkEnd();
/* 127 */         updateScoreboard();
/* 128 */         updateHologram(flag1);
/*     */       } 
/* 130 */     } else if (flag1.getLocation().distanceSquared(paramPlayerMoveEvent.getTo()) <= i && !flag1.isTaken()) {
/* 131 */       WarMap.getMap(this.map).message(Util.getMessage("gamemodes.halo.take", Util.getVars(new Object[] { "player", paramPlayerMoveEvent.getPlayer().getName(), "color", color(flag1.getColor()) })));
/* 132 */       flag1.setTaker(paramPlayerMoveEvent.getPlayer().getUniqueId());
/* 133 */       updateScoreboard();
/* 134 */       updateHologram(flag1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String color(ChatColor paramChatColor) {
/* 139 */     return paramChatColor.toString() + paramChatColor.name().replaceAll("_", " ");
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 143 */     WarMap warMap = WarMap.getMap(this.map);
/* 144 */     if (this.conq1 >= this.toWin) {
/* 145 */       MatchManager.get().won(warMap, warMap.getF1());
/* 146 */     } else if (this.conq2 >= this.toWin) {
/* 147 */       MatchManager.get().won(warMap, warMap.getF2());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, String> getLocations(WarMap paramWarMap) {
/* 152 */     return (Map<String, String>)ImmutableMap.of("flag1", "Add flag for " + Util.getTeam1Color() + "team 1§6 (only add once!): type §c/fw setlocation " + paramWarMap.getId() + " flag1", "flag2", "Add flag for " + 
/* 153 */         Util.getTeam2Color() + "team 2§6 (only add once!): type §c/fw setlocation " + paramWarMap.getId() + " flag2");
/*     */   }
/*     */   
/*     */   private class Flag { private final int owner;
/*     */     private Location location;
/*     */     private UUID taker;
/*     */     private UUID passId;
/*     */     private Hologram hologram;
/*     */     
/*     */     Flag(Location param1Location, int param1Int) {
/* 163 */       this.location = param1Location;
/* 164 */       this.owner = param1Int;
/*     */     }
/*     */     
/*     */     Hologram getHologram() {
/* 168 */       return this.hologram;
/*     */     }
/*     */     
/*     */     void setHologram(Hologram param1Hologram) {
/* 172 */       this.hologram = param1Hologram;
/*     */     }
/*     */     
/*     */     ChatColor getColor() {
/* 176 */       return (this.owner == 1) ? Util.getTeam1Color() : Util.getTeam2Color();
/*     */     }
/*     */     
/*     */     boolean isTaken() {
/* 180 */       return (this.taker != null);
/*     */     }
/*     */     
/*     */     UUID getTaker() {
/* 184 */       return this.taker;
/*     */     }
/*     */     
/*     */     void setTaker(UUID param1UUID) {
/* 188 */       Player player = FactionWars.get().getServer().getPlayer(this.taker);
/* 189 */       this.taker = param1UUID;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Location getLocation() {
/* 226 */       return this.location;
/*     */     }
/*     */     
/*     */     String getOwner() {
/* 230 */       return (this.owner == 1) ? WarMap.getMap(GamemodeHALO.this.map).getF1() : WarMap.getMap(GamemodeHALO.this.map).getF2();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeHALO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */