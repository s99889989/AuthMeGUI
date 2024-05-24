package com.daxton.authmegui;

import com.daxton.authmegui.command.MainAddCommand;
import com.daxton.authmegui.command.MainAddTab;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.authmegui.listener.MainAddListener;
import com.daxton.authmegui.listener.ResourceListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AuthMeGUI extends JavaPlugin {

    public static AuthMeGUI authMeGUI;

    @Override
    public void onEnable() {
        authMeGUI = this;

        Objects.requireNonNull(Bukkit.getPluginCommand("authmegui")).setExecutor(new MainAddCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("authmegui")).setTabCompleter(new MainAddTab());

        MainAddController.load();

        Bukkit.getPluginManager().registerEvents(new MainAddListener(), authMeGUI);
        if(Bukkit.getServer().getPluginManager().getPlugin("UnrealResource") != null){
            Bukkit.getPluginManager().registerEvents(new ResourceListener(), authMeGUI);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //發送後臺訊息(系統)
    public static void sendSystemLogger(String message){
        authMeGUI.getLogger().info("System: "+message);
    }

    //發送後臺訊息(錯誤)
    public static void sendErrorLogger(String message){
        authMeGUI.getLogger().info("Error: "+message);
    }

    //發送後臺訊息(一般)
    public static void sendLogger(String message){
        //authMeGUI.getLogger().info(message);
    }

    //獲取資源路徑
    public static String getResourceFolder(){
        return authMeGUI.getDataFolder()+"/";
    }

}
