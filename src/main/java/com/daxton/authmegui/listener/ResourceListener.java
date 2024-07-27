package com.daxton.authmegui.listener;



import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.unrealresource.event.UnrealResourceLoadFinishEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResourceListener implements Listener {



    @EventHandler//當玩家資源加載成功
    public void onPlayerJoin(UnrealResourceLoadFinishEvent event) {
        AuthMeGUI.sendTestLogger("資源載入成功");
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

}
