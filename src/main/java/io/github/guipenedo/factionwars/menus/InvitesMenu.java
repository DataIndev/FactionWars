/*    */ package io.github.guipenedo.factionwars.menus;
/*    */ 
/*    */ import io.github.guipenedo.factionwars.FactionWars;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsAcceptCommand;
/*    */ import io.github.guipenedo.factionwars.commands.wars.WarsDeclineCommand;
/*    */ import io.github.guipenedo.factionwars.helpers.ConditionsChecker;
/*    */ import io.github.guipenedo.factionwars.helpers.ItemBuilder;
/*    */ import io.github.guipenedo.factionwars.helpers.Util;
/*    */ import io.github.guipenedo.factionwars.menus.generic.ChestMenu;
/*    */ import io.github.guipenedo.factionwars.menus.generic.MenuItem;
/*    */ import io.github.guipenedo.factionwars.models.PlayerSelection;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class InvitesMenu
/*    */   extends ChestMenu {
/*    */   InvitesMenu(Player paramPlayer) {
/* 20 */     super(paramPlayer, Util.getMessage("menus.invites.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean openMenu() {
/* 25 */     if (!ConditionsChecker.check(this.player, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.HAS_FACTION, ConditionsChecker.Condition.HAS_INVITES }))
/* 26 */       return false; 
/* 27 */     String str = FactionWars.getHandler().getPlayerTeam(this.player);
/* 28 */     for (Iterator<PlayerSelection> iterator = PlayerSelection.getToFaction(str).iterator(); iterator.hasNext(); ) { PlayerSelection playerSelection = iterator.next();
/* 29 */       if (!playerSelection.isConfirmed())
/* 30 */         continue;  ArrayList<String> arrayList = new ArrayList();
/* 31 */       arrayList.add(Util.getPlainMessage("menus.invites.gamemode", Collections.singletonMap("gamemode", playerSelection.getGamemode().getName())));
/* 32 */       if (ConditionsChecker.checkSilently(this.player, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.IS_LEADER })) {
/* 33 */         arrayList.add(Util.getPlainMessage("menus.invites.accept"));
/* 34 */         arrayList.add(Util.getPlainMessage("menus.invites.decline"));
/*    */       } 
/* 36 */       String str1 = FactionWars.getHandler().getTeamName(playerSelection.getFrom());
/* 37 */       ItemBuilder itemBuilder = new ItemBuilder(playerSelection.getGamemode().getItem().clone(), str1, arrayList, true);
/* 38 */       this.items.add(new MenuItem(itemBuilder, paramMenuItemClickEvent -> {
/*    */               if (ConditionsChecker.checkSilently(this.player, new ConditionsChecker.Condition[] { ConditionsChecker.Condition.IS_LEADER }))
/*    */                 if (paramMenuItemClickEvent.getClickType().isLeftClick()) {
/*    */                   (new WarsAcceptCommand()).perform(this.player, paramString1, new String[] { paramString2 });
/*    */                 } else {
/*    */                   (new WarsDeclineCommand()).perform(this.player, paramString1, new String[] { paramString2 });
/*    */                 }  
/*    */             })); }
/* 46 */      openGUI();
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Escritorio\FactionWars.jar!\io\github\guipenedo\factionwars\menus\InvitesMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */