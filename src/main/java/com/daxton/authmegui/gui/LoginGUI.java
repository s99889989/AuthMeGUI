package com.daxton.authmegui.gui;


import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.unrealcore.application.UnrealCoreAPI;
import com.daxton.unrealcore.application.method.SchedulerFunction;
import com.daxton.unrealcore.common.type.MouseActionType;
import com.daxton.unrealcore.common.type.MouseButtonType;
import com.daxton.unrealcore.display.content.gui.UnrealCoreGUI;
import com.daxton.unrealcore.display.content.module.control.ButtonModule;

import com.daxton.unrealcore.display.content.module.input.InputModule;
import com.daxton.unrealcore.display.type.GUIType;
import fr.xephi.authme.api.v3.AuthMeApi;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;



public class LoginGUI extends UnrealCoreGUI {

    private String password = "";



    public LoginGUI(String guiName, FileConfiguration fileConfiguration, Player player) {
        super(guiName, fileConfiguration);

        //登入按鈕
        ButtonModule loginButton = (ButtonModule) getModule("LoginButton");
        loginButton.onButtonClick((buttonModule, mouseButtonType, mouseActionType) -> {
            if(mouseButtonType == MouseButtonType.Left && mouseActionType == MouseActionType.On){
                enter();
            }
        });

        //離開伺服器按鈕
        ButtonModule leaveServerButton = (ButtonModule) getModule("LeaveServerButton");
        leaveServerButton.onButtonClick((buttonModule, mouseButtonType, mouseActionType) -> {
            if(mouseButtonType == MouseButtonType.Left && mouseActionType == MouseActionType.On){
                player.kickPlayer("");
            }
        });

        //密碼輸入
        InputModule passWordInput = (InputModule) getModule("PassWordInput");
        passWordInput.onInputChange((inputModule, text, finish) -> {
            password = text;
        });

    }


    //確認
    public void enter(){
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        //檢查密碼是否正確
        boolean correct = authMeApi.checkPassword(getPlayer().getName(), password);

        //正確
        if(correct){

            authMeApi.forceLogin(getPlayer());
            String uuidString = getPlayer().getUniqueId().toString();
            MainAddController.sendGUI.put(uuidString, false);
//            UnrealCoreAPI.setScreenDisplay(getPlayer(), GUIType.All, true);
            UnrealCoreAPI.closeGUI(getPlayer());

        }

        //不正確
        if(!correct){
            InputModule passWordInput = (InputModule) getModule("PassWordInput");

            //密碼錯誤字串
            String wrongPassword = getFileConfiguration().getString(passWordInput.getFilePath()+".WrongPassword");
            passWordInput.setPrompt(wrongPassword);

            passWordInput.setText("");

            upDate();
        }

    }

    @Override
    public void keyOn(int keyID, String keyName) {
        if(keyName.equals("Enter") || keyName.equals("NumEnter")){
            enter();
        }


        super.keyOn(keyID, keyName);
    }

    @Override
    public void opening() {
        if(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            placeholderChange();
        }

    }

    public void placeholderChange(){
        applyFunctionToFields(this::placeholder);
    }

    public String placeholder(String content){
        if(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            return  PlaceholderAPI.setPlaceholders(getPlayer(), content);
        }
        return content;
    }

}
