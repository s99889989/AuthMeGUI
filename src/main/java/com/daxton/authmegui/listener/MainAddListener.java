package com.daxton.authmegui.listener;

import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.unrealcore.application.UnrealCoreAPI;
import com.daxton.unrealcore.application.method.SchedulerFunction;
import com.daxton.unrealcore.common.event.PlayerKeyBoardEvent;
import com.daxton.unrealcore.communication.event.PlayerConnectionSuccessfulEvent;

import com.daxton.unrealcore.display.event.gui.PlayerGUICloseEvent;
import com.daxton.unrealcore.display.event.gui.PlayerGUIOpenEvent;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

//import fr.xephi.authme.events.FailedLoginEvent;
//import fr.xephi.authme.events.LoginEvent;
public class MainAddListener implements Listener {

    @EventHandler//當玩家連線成功
    public void onPlayerJoin(PlayerConnectionSuccessfulEvent event) {
        if(Bukkit.getServer().getPluginManager().getPlugin("UnrealResource") != null){
            return;
        }


        AuthMeGUI.sendTestLogger("玩家連線成功");
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        boolean sendGUI = MainAddController.sendGUI.get(uuidString);
        if(sendGUI){
            MainAddController.openGUI(player);
        }
//        AuthMeApi authMeApi = AuthMeApi.getInstance();
//        //檢查是否有通過驗證
//        if(!authMeApi.isAuthenticated(player)){
//            UnrealCoreAPI.inst(player).getGUIHelper().closeGUI();
//            SchedulerFunction.RunTask schedulerFunction = SchedulerFunction.runTimer(AuthMeGUI.authMeGUI, ()->{
//                AuthMeGUI.sendLogger("加入-打開");
//                MainAddController.openGUI(player);
//            }, 3, 10);
//            MainAddController.stringScheduledFutureMap.put(uuidString, schedulerFunction);
//
//        }

    }


    @EventHandler//當玩家打開GUI
    public void onGUIOpen(PlayerGUIOpenEvent event) {

        String guiName = event.getGuiName();

        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        boolean sendGUI = MainAddController.sendGUI.get(uuidString);
        if(sendGUI){
            if(!guiName.equals("AuthMeGUI")){
                UnrealCoreAPI.inst(player).getGUIHelper().closeGUI();
            }
        }

//        Player player = event.getPlayer();
//        String guiName = event.getGuiName();
//        AuthMeApi authMeApi = AuthMeApi.getInstance();
//        AuthMeGUI.sendLogger(guiName+" : "+event.getActionType());
//        String uuidString = player.getUniqueId().toString();
//        //檢查是否有通過驗證
//        if(!authMeApi.isAuthenticated(player)){
//            if(!guiName.equals("AuthMeGUI")){
//                UnrealCoreAPI.inst(player).getGUIHelper().closeGUI();
//            }
//        }
//
//        SchedulerFunction.RunTask schedulerFunction = MainAddController.stringScheduledFutureMap.get(uuidString);
//        if(schedulerFunction != null){
//            schedulerFunction.cancel();
//            MainAddController.stringScheduledFutureMap.remove(uuidString);
//            AuthMeGUI.sendLogger("暫停"+guiName);
//        }

    }

    @EventHandler //玩家登入
    public void onPlayerLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        //檢查是否有通過驗證
        if(!authMeApi.isAuthenticated(player)){
            MainAddController.sendGUI.put(uuidString, true);
        }else {
            MainAddController.sendGUI.put(uuidString, false);
        }
    }

    @EventHandler //玩家登出
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        MainAddController.sendGUI.put(uuidString, false);
//        if(MainAddController.stringScheduledFutureMap.containsKey(uuidString)){
//            SchedulerFunction.RunTask schedulerFunction =  MainAddController.stringScheduledFutureMap.get(uuidString);
//            schedulerFunction.cancel();
//            MainAddController.stringScheduledFutureMap.remove(uuidString);
//        }
    }

    @EventHandler//當玩家關閉GUI
    public void onGUIClose(PlayerGUICloseEvent event) {
        String guiName = event.getGuiName();

        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        boolean sendGUI = MainAddController.sendGUI.get(uuidString);
        if(sendGUI){
            if(guiName.equals("AuthMeGUI")){
                SchedulerFunction.runLater(AuthMeGUI.unrealCorePlugin.getJavaPlugin(), ()->{
                    MainAddController.openGUI(player);
                }, 1);

            }
        }
//        AuthMeApi authMeApi = AuthMeApi.getInstance();
//        String guiName = event.getGuiName();
//        if(!authMeApi.isAuthenticated(player)){
//            SchedulerFunction.RunTask schedulerFunction = MainAddController.stringScheduledFutureMap.get(uuidString);
//            if(schedulerFunction == null){
//                schedulerFunction = SchedulerFunction.runTimer(AuthMeGUI.authMeGUI, ()->{
//                    AuthMeGUI.sendLogger("關閉-打開");
//                    MainAddController.openGUI(player);
//                }, 0, 10);
//                MainAddController.stringScheduledFutureMap.put(uuidString, schedulerFunction);
//                return;
//            }
//        }

    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerKeyBoardEvent(PlayerKeyBoardEvent event){
        String keyName = event.getKeyName();

    }
//    @EventHandler
//    public void onAuthMeLogin(LoginEvent event) {
//        Player player = event.getPlayer();
//        UnrealCoreAPI.closeGUI(player);
//    }
//
//    @EventHandler
//    public void onAuthMeLoginFalse(FailedLoginEvent event) {
//        Player player = event.getPlayer();
//        AuthMeGUI.sendLogger("1111");
//    }


}
