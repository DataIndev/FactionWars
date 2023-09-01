/*    */ package io.github.guipenedo.factionwars.menus;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsInviteCommand;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsStatsCommand;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsToggleCommand;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsTopCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.ConditionsChecker;
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.generic.AnvilGUI;
/*    */ import io.github.guipenedo.factionwars.menus.generic.ChestMenu;
/*    */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*    */ import io.github.guipenedo.factionwars.models.PlayerData;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class MainMenu
/*    */   extends ChestMenu
/*    */ {
/*    */   public MainMenu(Player paramPlayer) {
/* 25 */     super(paramPlayer, Util.getMessage("menus.main.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean openMenu() {
/* 30 */     if (ConditionsChecker.checkSilently(this.player, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.IS_LEADER }) && FactionWars.getMainConfig().getConfig().getBoolean("selection.anvilgui-enabled"))
/*    */     {
/* 32 */       this.items.add(new MenuItem(new ItemBuilder(new ItemStack(Material.NAME_TAG), Util.getPlainMessage("menus.main.invite"), Collections.singletonList(Util.getPlainMessage("commands.invite")), true), paramMenuItemClickEvent -> {
/*    */               paramMenuItemClickEvent.setWillClose(false);
/*    */ 
/*    */ 
/*    */ 
/*    */               
/*    */               AnvilGUI anvilGUI = new AnvilGUI(this.player, (), Util.getPlainMessage("menus.main.invite-tag"));
/*    */ 
/*    */ 
/*    */ 
/*    */               
/*    */               anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, (new ItemBuilder(new ItemStack(Material.NAME_TAG), Util.getPlainMessage("menus.main.invite-tag"))).build());
/*    */ 
/*    */ 
/*    */               
/*    */               try {
/*    */                 anvilGUI.open();
/* 49 */               } catch (Exception exception) {
/*    */                 exception.printStackTrace();
/*    */               } 
/*    */             }));
/*    */     }
/*    */     
/* 55 */     this.items.add(new MenuItem(new ItemBuilder(new ItemStack(Material.BOOK), Util.getPlainMessage("menus.main.list"), Collections.singletonList(Util.getPlainMessage("commands.list")), true), paramMenuItemClickEvent -> {
/*    */             if (ConditionsChecker.check(this.player, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.HAS_FACTION, ConditionsChecker.Condition.HAS_INVITES })) {
/*    */               paramMenuItemClickEvent.setWillClose(false);
/*    */               
/*    */               InvitesMenu invitesMenu = new InvitesMenu(this.player);
/*    */               
/*    */               invitesMenu.open();
/*    */             } 
/*    */           }));
/* 64 */     this.items.add(new MenuItem(new ItemBuilder(new ItemStack(Material.GOLD_BLOCK), Util.getPlainMessage("menus.main.stats"), Collections.singletonList(Util.getPlainMessage("commands.stats")), true), paramMenuItemClickEvent -> (new WarsStatsCommand()).perform(this.player, FactionWars.getHandler().getPlayerTeam(this.player), new String[0])));
/*    */     
/* 66 */     this.items.add(new MenuItem(new ItemBuilder(new ItemStack(Material.NETHER_STAR), Util.getPlainMessage("menus.main.top"), Collections.singletonList(Util.getPlainMessage("commands.top")), true), paramMenuItemClickEvent -> (new WarsTopCommand()).perform(this.player, FactionWars.getHandler().getPlayerTeam(this.player), new String[0])));
/*    */     
/* 68 */     if (FactionWars.getMainConfig().getConfig().getBoolean("enable-war-opt-out"))
/* 69 */       this.items.add(new MenuItem(new ItemBuilder(new ItemStack(Material.LEVER), Util.getPlainMessage("menus.main.toggle"), Arrays.asList(new String[] { Util.getPlainMessage("commands.toggle"), PlayerData.getOptedOut().contains(this.player.getUniqueId()) ? Util.getPlainMessage("toggle-command.on") : Util.getPlainMessage("toggle-command.off") }, ), true), paramMenuItemClickEvent -> (new WarsToggleCommand()).perform(this.player, FactionWars.getHandler().getPlayerTeam(this.player), new String[0]))); 
/* 70 */     openGUI();
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\MainMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */