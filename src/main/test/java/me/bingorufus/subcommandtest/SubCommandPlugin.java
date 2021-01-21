package me.bingorufus.subcommandtest;

import me.bingorufus.commandbuilder.CommandBuilder;
import me.bingorufus.commandbuilder.SubCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SubCommandPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        CommandBuilder builder = (CommandBuilder) new CommandBuilder(this,"commandbuilder").addSubCommand(new SubCommandBuilder("test").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("oof");
            return true;
        }).setUsage("/commandbuilder test").build());
        builder.addSubCommand(new SubCommandBuilder("tick").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("§ctock");
            return false;
        }).setUsage("/commandbuilder tick").addSubCommand(new SubCommandBuilder("tock").setPermission("commandbuilder.tock", PermissionDefault.OP).setCommandHandler(((sender, command, label, args) -> {
            if(args.length != 3){
                return false;
            }
                Player p = Bukkit.getPlayer(args[2]);
            if(p == null) return false;
                p.getInventory().addItem(new ItemStack(Material.CLOCK,1));
         return true;
        })).setUsage("/commandbuilder tick tock <Player>").setTabHandler((sender, command, label, args) -> {
            List<String> out = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(p -> out.add(p.getName()));
         return out;
        }).build()).build());
        builder.setCommandHandler(((sender, command, label, args) -> {
            sender.sendMessage("hello there!");
            return true;
        }));

        builder.register();
    }

    @Override
    public void onDisable() {

    }

}
