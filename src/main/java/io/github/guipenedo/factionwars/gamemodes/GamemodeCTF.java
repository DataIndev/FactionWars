/*     */ package io.github.guipenedo.factionwars.gamemodes;
/*     */ import com.gmail.filoghost.holographicdisplays.api.Hologram;
/*     */ import io.github.guipenedo.factionwars.FactionWars;
/*     */ import io.github.guipenedo.factionwars.helpers.Util;
/*     */ import io.github.guipenedo.factionwars.managers.MatchManager;
/*     */ import io.github.guipenedo.factionwars.models.WarMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ 
/*     */ public class GamemodeCTF implements GamemodeManager {
/*  21 */   private final String msg = "gamemodes.ctf."; private final String map;
/*  22 */   private int range = 3; private Location s1;
/*     */   private Location s2;
/*     */   private Map<Integer, Flag> flags;
/*  25 */   private Map<UUID, Set<Integer>> carrier = new TreeMap<>();
/*  26 */   private char[] colors = new char[] { '2', '6', '4', '1', '3', 'b', 'a', 'e', 'c', '9', '5', 'd', 'f', '7', '8', '0' };
/*     */   
/*     */   public GamemodeCTF(WarMap paramWarMap, ConfigurationSection paramConfigurationSection) {
/*  29 */     this.map = paramWarMap.getId();
/*  30 */     this.range = paramConfigurationSection.getInt("gamemode-settings.range", this.range);
/*  31 */     updateLocations(paramWarMap);
/*     */   }
/*     */   
/*     */   public void updateLocations(WarMap paramWarMap) {
/*  35 */     if (paramWarMap.isSetup(this)) {
/*  36 */       this.flags = new TreeMap<>();
/*  37 */       byte b = 0;
/*  38 */       for (Location location : paramWarMap.getLocations().get("flag1"))
/*  39 */         this.flags.put(Integer.valueOf(b), new Flag(location, 1, b++)); 
/*  40 */       for (Location location : paramWarMap.getLocations().get("flag2"))
/*  41 */         this.flags.put(Integer.valueOf(b), new Flag(location, 2, b++)); 
/*  42 */       this.s1 = ((ArrayList<Location>)paramWarMap.getLocations().get("s1")).get(0);
/*  43 */       this.s2 = ((ArrayList<Location>)paramWarMap.getLocations().get("s2")).get(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startMatch() {
/*  49 */     for (UUID uUID : WarMap.getMap(this.map).getPlayers())
/*  50 */       this.carrier.put(uUID, new TreeSet<>()); 
/*  51 */     for (Flag flag : this.flags.values()) {
/*  52 */       updateHologram(flag);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateHologram(Flag paramFlag) {
/*  57 */     if (!FactionWars.get().getServer().getPluginManager().isPluginEnabled("HolographicDisplays"))
/*  58 */       return;  Location location = paramFlag.getLocation().clone();
/*  59 */     location.setY(location.getY() + 2.0D);
/*  60 */     if (paramFlag.getHologram() == null)
/*  61 */       paramFlag.setHologram(HologramsAPI.createHologram((Plugin)FactionWars.get(), location)); 
/*  62 */     Hologram hologram = paramFlag.getHologram();
/*  63 */     hologram.clearLines();
/*  64 */     hologram.appendTextLine(paramFlag.getColor().toString() + "⚑ ⚑ ⚑");
/*  65 */     hologram.appendTextLine(color(paramFlag.getColor()) + " " + Util.getPlainMessage("gamemodes.ctf.hologram.flag"));
/*  66 */     hologram.appendTextLine(Util.getPlainMessage("gamemodes.ctf.hologram.status") + ": §l" + (paramFlag.isCaptured() ? Util.getPlainMessage("gamemodes.ctf.hologram.lost") : Util.getPlainMessage("gamemodes.ctf.hologram.defended")));
/*  67 */     hologram.appendTextLine(Util.getPlainMessage("gamemodes.ctf.hologram.owner") + ": §b" + FactionWars.getHandler().getTeamName(paramFlag.getOwner()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void timeOut() {
/*  72 */     MatchManager.get().tied(WarMap.getMap(this.map));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScoreboard() {
/*  77 */     WarMap warMap = WarMap.getMap(this.map);
/*  78 */     StringBuilder stringBuilder1 = new StringBuilder();
/*  79 */     StringBuilder stringBuilder2 = new StringBuilder();
/*  80 */     for (Flag flag : this.flags.values()) {
/*  81 */       String str = flag.getColor() + (flag.isCaptured() ? "▓" : "█") + " ";
/*  82 */       if (flag.getOwner().equals(warMap.getF1())) {
/*  83 */         stringBuilder1.append(str); continue;
/*  84 */       }  stringBuilder2.append(str);
/*     */     } 
/*  86 */     for (Player player : warMap.getPlayerList()) {
/*  87 */       StringBuilder stringBuilder = new StringBuilder();
/*  88 */       for (Iterator<Integer> iterator = ((Set)this.carrier.get(player.getUniqueId())).iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue(); stringBuilder.append(((Flag)this.flags.get(Integer.valueOf(i))).getColor().toString()).append("⚑ "); }
/*  89 */        ScoreboardManager.get().display(player, warMap, new String[] {
/*  90 */             Util.getPlainMessage("gamemodes.ctf.scoreboard.flags"), Util.getTeam1Color() + FactionWars.getHandler().getTeamName(warMap.getF1()), stringBuilder1.toString(), Util.getTeam2Color() + FactionWars.getHandler().getTeamName(warMap.getF2()), stringBuilder2.toString(), "", Util.getPlainMessage("gamemodes.ctf.scoreboard.carrying"), stringBuilder.toString().equals("") ? Util.getPlainMessage("gamemodes.ctf.scoreboard.no-flags") : stringBuilder.toString()
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill(UUID paramUUID) {}
/*     */ 
/*     */   
/*     */   public void death(UUID paramUUID) {
/* 100 */     ((Set)this.carrier.get(paramUUID)).clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 105 */     for (Flag flag : this.flags.values()) {
/* 106 */       flag.setCaptured(false);
/* 107 */       if (flag.getHologram() != null)
/* 108 */         flag.getHologram().delete(); 
/* 109 */       flag.setHologram(null);
/*     */     } 
/* 111 */     this.carrier.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRespawn(UUID paramUUID) {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   private Location getReturnL(Player paramPlayer) {
/* 120 */     if (WarMap.getMap(this.map).getF1().equals(FactionWars.getHandler().getPlayerTeam(paramPlayer)))
/* 121 */       return this.s1; 
/* 122 */     return this.s2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(PlayerMoveEvent paramPlayerMoveEvent) {
/* 127 */     int i = this.range * this.range;
/* 128 */     String str = FactionWars.getHandler().getPlayerTeam(paramPlayerMoveEvent.getPlayer());
/* 129 */     if (paramPlayerMoveEvent.getTo().distanceSquared(getReturnL(paramPlayerMoveEvent.getPlayer())) <= i) {
/* 130 */       for (Iterator<Integer> iterator = ((Set)this.carrier.get(paramPlayerMoveEvent.getPlayer().getUniqueId())).iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/* 131 */         Flag flag = this.flags.get(Integer.valueOf(j));
/* 132 */         if (!flag.isCaptured()) {
/* 133 */           WarMap.getMap(this.map).message(Util.getMessage("gamemodes.ctf.capture", Util.getVars(new Object[] { "player", paramPlayerMoveEvent.getPlayer().getName(), "color", color(flag.getColor()) })));
/* 134 */           flag.setCaptured(true);
/* 135 */           checkEnd();
/* 136 */           updateScoreboard();
/* 137 */           updateHologram(flag);
/*     */         }  }
/*     */       
/* 140 */       ((Set)this.carrier.get(paramPlayerMoveEvent.getPlayer().getUniqueId())).clear();
/*     */     } 
/* 142 */     for (Flag flag : this.flags.values()) {
/* 143 */       if (flag.getLocation().distanceSquared(paramPlayerMoveEvent.getTo()) <= i && 
/* 144 */         !flag.getOwner().equals(str) && !((Set)this.carrier.get(paramPlayerMoveEvent.getPlayer().getUniqueId())).contains(Integer.valueOf(flag.getId())) && !flag.isCaptured()) {
/* 145 */         WarMap.getMap(this.map).message(Util.getMessage("gamemodes.ctf.take", Util.getVars(new Object[] { "player", paramPlayerMoveEvent.getPlayer().getName(), "color", color(flag.getColor()) })));
/* 146 */         ((Set<Integer>)this.carrier.get(paramPlayerMoveEvent.getPlayer().getUniqueId())).add(Integer.valueOf(flag.getId()));
/* 147 */         updateScoreboard();
/* 148 */         updateHologram(flag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String color(ChatColor paramChatColor) {
/* 154 */     return paramChatColor.toString() + paramChatColor.name().replaceAll("_", " ");
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 158 */     WarMap warMap = WarMap.getMap(this.map);
/* 159 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/* 160 */     for (Flag flag : this.flags.values()) {
/* 161 */       if (flag.getOwner().equals(warMap.getF1())) {
/* 162 */         b2++;
/* 163 */         if (flag.isCaptured()) b1++;  continue;
/*     */       } 
/* 165 */       b4++;
/* 166 */       if (flag.isCaptured()) b3++;
/*     */     
/*     */     } 
/* 169 */     if (b1 == b2) {
/* 170 */       MatchManager.get().won(warMap, warMap.getF2());
/* 171 */     } else if (b3 == b4) {
/* 172 */       MatchManager.get().won(warMap, warMap.getF1());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, String> getLocations(WarMap paramWarMap) {
/* 177 */     return (Map<String, String>)ImmutableMap.of("flag1", "Add flags for " + Util.getTeam1Color() + "team 1§6: type §c/fw setlocation " + paramWarMap.getId() + " flag1\nAdd as many as you want!", "flag2", "Add flags for " + 
/* 178 */         Util.getTeam2Color() + "team 2§6: type §c/fw setlocation " + paramWarMap.getId() + " flag2\nAdd as many as you want!", "s1", "Add " + 
/* 179 */         Util.getTeam1Color() + "team 1§6 flag return point (only add once!) §c/fw setlocation " + paramWarMap.getId() + " s1", "s2", "Add " + 
/* 180 */         Util.getTeam2Color() + "team 2§6 flag return point (only add once!) §c/fw setlocation " + paramWarMap.getId() + " s2");
/*     */   }
/*     */   
/*     */   private class Flag {
/*     */     private final int owner;
/*     */     private Location location;
/*     */     private boolean captured = false;
/*     */     private int id;
/*     */     private Hologram hologram;
/*     */     
/*     */     Flag(Location param1Location, int param1Int1, int param1Int2) {
/* 191 */       this.location = param1Location;
/* 192 */       this.id = param1Int2;
/* 193 */       this.owner = param1Int1;
/*     */     }
/*     */     
/*     */     Hologram getHologram() {
/* 197 */       return this.hologram;
/*     */     }
/*     */     
/*     */     void setHologram(Hologram param1Hologram) {
/* 201 */       this.hologram = param1Hologram;
/*     */     }
/*     */     
/*     */     ChatColor getColor() {
/* 205 */       return ChatColor.getByChar(GamemodeCTF.this.colors[this.id % 16]);
/*     */     }
/*     */     
/*     */     boolean isCaptured() {
/* 209 */       return this.captured;
/*     */     }
/*     */     
/*     */     void setCaptured(boolean param1Boolean) {
/* 213 */       this.captured = param1Boolean;
/*     */     }
/*     */     
/*     */     Location getLocation() {
/* 217 */       return this.location;
/*     */     }
/*     */     
/*     */     int getId() {
/* 221 */       return this.id;
/*     */     }
/*     */     
/*     */     String getOwner() {
/* 225 */       return (this.owner == 1) ? WarMap.getMap(GamemodeCTF.this.map).getF1() : WarMap.getMap(GamemodeCTF.this.map).getF2();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\gamemodes\GamemodeCTF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */