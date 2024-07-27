package com.daxton.authmegui.command;

import com.daxton.authmegui.AuthMeGUI;
import com.daxton.authmegui.controller.MainAddController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainAddCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if(!player.isOp()){
                        return true;
                    }
                    player.sendMessage("[AuthMeGUI] Reload");
                }
                AuthMeGUI.unrealCorePlugin.sendSystemLogger("Reload");
                MainAddController.load();
                return true;
            }
        }

        return false;
    }

}
