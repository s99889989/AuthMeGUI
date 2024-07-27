package com.daxton.authmegui.gui;

import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.controller.MainAddController;
import com.daxton.unrealcore.common.type.MouseActionType;
import com.daxton.unrealcore.common.type.MouseButtonType;
import com.daxton.unrealcore.display.content.gui.UnrealCoreGUI;
import com.daxton.unrealcore.display.content.module.control.ButtonModule;
import com.daxton.unrealcore.display.content.module.input.InputModule;
import fr.xephi.authme.api.v3.AuthMeApi;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RegisterGUI extends UnrealCoreGUI {

    private String password = "";

    private String confirm_password = "";

    public RegisterGUI(String guiName, FileConfiguration fileConfiguration, Player player) {
        super(guiName, fileConfiguration);

        //註冊按鈕
        ButtonModule registerButton = (ButtonModule) getModule("RegisterButton");
        registerButton.onButtonClick((buttonModule, mouseButtonType, mouseActionType) -> {
            if(mouseButtonType == MouseButtonType.Left && mouseActionType == MouseActionType.On){
                AuthMeGUI.sendTestLogger("註冊");
                enter();
            }
        });

        //離開伺服器按鈕
        ButtonModule leaveServerButton = (ButtonModule) getModule("LeaveServerButton");
        leaveServerButton.onButtonClick((buttonModule, mouseButtonType, mouseActionType) -> {
            if(mouseButtonType == MouseButtonType.Left && mouseActionType == MouseActionType.On){
                AuthMeGUI.sendTestLogger("離開");
                getPlayer().kickPlayer("");
            }
        });

    }

    //確認
    public void enter(){

        InputModule passWordInput = (InputModule) getModule("PassWordInput");
        String passWordText = getFileConfiguration().getString(passWordInput.getFilePath()+".Prompt");
        passWordInput.setPrompt(passWordText);
        passWordInput.setText("");

        InputModule passWordConfirmInput = (InputModule) getModule("PassWordConfirmInput");
        String passWordConfirmText = getFileConfiguration().getString(passWordConfirmInput.getFilePath()+".Prompt");
        passWordConfirmInput.setPrompt(passWordConfirmText);
        passWordConfirmInput.setText("");

        //密碼太短字串
        String passwordMinLength = getFileConfiguration().getString(passWordInput.getFilePath()+".PasswordMinLength");
        if (passwordMinLength != null) {
            passwordMinLength = passwordMinLength.replace("{min}", MainAddController.minPassWord+"").replace("{max}", MainAddController.maxPassWord+"");
        }
        //密碼太長字串
        String passwordMaxLength = getFileConfiguration().getString(passWordInput.getFilePath()+".PasswordMaxLength");
        if (passwordMaxLength != null) {
            passwordMaxLength = passwordMaxLength.replace("{min}", MainAddController.minPassWord+"").replace("{max}", MainAddController.maxPassWord+"");
        }

        boolean error = false;

        //密碼長度限制
        if(password.length() < MainAddController.minPassWord){
            passWordInput.setPrompt(passwordMinLength);
            error = true;
        }
        if(password.length() > MainAddController.maxPassWord){
            passWordInput.setPrompt(passwordMaxLength);
            error = true;
        }

        //確認密碼長度限制
        if(confirm_password.length() < MainAddController.minPassWord){
            passWordConfirmInput.setPrompt(passwordMinLength);
            error = true;
        }
        if(confirm_password.length() > MainAddController.maxPassWord){
            passWordConfirmInput.setPrompt(passwordMaxLength);
            error = true;
        }

        //判斷密碼和確認密碼是否相同
        if(!password.equals(confirm_password)){
            error = true;
            password = "";
            confirm_password = "";
            //密碼相同字串
            String notSame = getFileConfiguration().getString(passWordInput.getFilePath()+".NotSame");
            passWordInput.setPrompt(notSame);
        }

        //如果有錯誤就返回
        if(error){
            upDate();
            return;
        }

        AuthMeApi authMeApi = AuthMeApi.getInstance();
        authMeApi.registerPlayer(getPlayer().getName(), password);
        getPlayer().kickPlayer("");

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

        //密碼輸入
        InputModule passWordInput = (InputModule) getModule("PassWordInput");
        passWordInput.onInputChange((inputModule, text, finish) -> {
            password = text;
        });

        //密碼確認輸入
        InputModule passWordConfirmInput = (InputModule) getModule("PassWordConfirmInput");
        passWordConfirmInput.onInputChange((inputModule, text, finish) -> {
            confirm_password = text;
        });
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
