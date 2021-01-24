package me.bingorufus.subcommandtest;

import me.bingorufus.commandbuilder.builders.CommandBuilder;
import me.bingorufus.commandbuilder.builders.SubCommandBuilder;
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
        CommandBuilder builder = (CommandBuilder) new CommandBuilder("subcommand").addSubCommand(new SubCommandBuilder("test").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("oof");
            return true;
        }).build());

        builder.addSubCommand(new SubCommandBuilder("tick").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("§ctock");
            return true;
        }).setUsageMessage("/subcommand tick").addSubCommand(
                new SubCommandBuilder("tock").setPermission("subcommand.tock", PermissionDefault.OP).setCommandHandler(((sender, command, label, args) -> {
                    if (args.length != 1) {
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p == null) return false;
                    p.getInventory().addItem(new ItemStack(Material.CLOCK, 1));
                    return true;
                })).setTabHandler((sender, command, label, args) -> {
                    List<String> out = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach(p -> out.add(p.getName()));
                    return out;
                }).setUsageMessage("/subcommand tick tock <Player>").build()

        ).build());

        builder.setCommandHandler(((sender, command, label, args) -> {
            sender.sendMessage("hello there!");
            return true;
        }));
        builder.build().register(this);


    }

    @Override
    public void onDisable() {

    }

}
