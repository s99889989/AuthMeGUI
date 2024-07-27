package com.daxton.authmegui.controller;

import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.gui.LoginGUI;
import com.daxton.authmegui.gui.RegisterGUI;
import com.daxton.unrealcore.application.UnrealCoreAPI;
import com.daxton.unrealcore.application.base.PluginUtil;
import com.daxton.unrealcore.application.method.SchedulerFunction;
import com.daxton.unrealcore.display.type.GUIType;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MainAddController {

    //GUI設定
    public static FileConfiguration loginGUIConfig;

    public static FileConfiguration registerGUIConfig;

    public static int minPassWord;  //最短密碼限制

    public static int maxPassWord;  //密碼最長限制

    //是否發送GUI
    public static Map<String, Boolean> sendGUI = new ConcurrentHashMap<>();

    public static Map<String, SchedulerFunction.RunTask> stringScheduledFutureMap = new ConcurrentHashMap<>();

    public static void load(){
        //建立設定檔
        createConfig();

        loginGUIConfig = getYmlFile("/gui/LoginGUI.yml");

        registerGUIConfig = getYmlFile("/gui/RegisterGUI.yml");

        FileConfiguration fileConfiguration = getYmlFile("config.yml");
        minPassWord = fileConfiguration.getInt("PassWord.MinLength");
        maxPassWord = fileConfiguration.getInt("PassWord.MaxLength");

    }

    //打開登入介面
    public static void openGUI(Player player){
        AuthMeApi authMeApi = AuthMeApi.getInstance();

        //檢測玩家是否有註冊
        if(authMeApi.isRegistered(player.getName())){
            LoginGUI loginGUI = new LoginGUI("AuthMeGUI", loginGUIConfig, player);
            UnrealCoreAPI.inst(player).getGUIHelper().openCoreGUI(loginGUI);
        }else {
            RegisterGUI registerGUI = new RegisterGUI("AuthMeGUI", registerGUIConfig, player);
            UnrealCoreAPI.inst(player).getGUIHelper().openCoreGUI(registerGUI);
        }

    }

    //從插件預設路徑獲取YML檔案
    public static FileConfiguration getYmlFile(String path){

        File file = new File(AuthMeGUI.unrealCorePlugin.getResourceFolder(), path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    //建立設定檔
    public static void createConfig(){
        PluginUtil.resourceCopy(AuthMeGUI.unrealCorePlugin.getJavaPlugin(), "gui/LoginGUI.yml", false);
        PluginUtil.resourceCopy(AuthMeGUI.unrealCorePlugin.getJavaPlugin(), "gui/RegisterGUI.yml", false);
        PluginUtil.resourceCopy(AuthMeGUI.unrealCorePlugin.getJavaPlugin(), "config.yml", false);
    }

}
