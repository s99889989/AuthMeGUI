package com.daxton.authmegui;

import com.daxton.authmegui.command.MainAddCommand;
import com.daxton.authmegui.command.MainAddTab;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.authmegui.listener.MainAddListener;
import com.daxton.authmegui.listener.ResourceListener;
import com.daxton.unrealcore.UnrealCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AuthMeGUI extends JavaPlugin {

    public static UnrealCorePlugin unrealCorePlugin;

    @Override
    public void onEnable() {
        unrealCorePlugin = new UnrealCorePlugin(this);

        Objects.requireNonNull(Bukkit.getPluginCommand("authmegui")).setExecutor(new MainAddCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("authmegui")).setTabCompleter(new MainAddTab());

        MainAddController.load();

        Bukkit.getPluginManager().registerEvents(new MainAddListener(), this);
        if(Bukkit.getServer().getPluginManager().getPlugin("UnrealResource") != null){
            Bukkit.getPluginManager().registerEvents(new ResourceListener(), this);
        }
    }

    @Override
    public void onDisable() {

    }


    //發送後臺訊息(一般)
    public static void sendTestLogger(String message){
        //unrealCorePlugin.sendLogger(message);
    }


}
