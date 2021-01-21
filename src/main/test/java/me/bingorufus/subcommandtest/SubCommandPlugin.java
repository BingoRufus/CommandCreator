package me.bingorufus.subcommandtest;

import me.bingorufus.subcommand.CommandBuilder;
import me.bingorufus.subcommand.SubCommandBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SubCommandPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        CommandBuilder builder = (CommandBuilder) new CommandBuilder(this,"subcommand").addSubCommand(new SubCommandBuilder("test").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("oof");
            return true;
        }).build());
        builder.addSubCommand(new SubCommandBuilder("tick").setCommandHandler((sender, command, label, args) -> {
            sender.sendMessage("§ctock");
            return false;
        }).setUsage("/subcommand tick").addSubCommand(new SubCommandBuilder("tock").setCommandHandler(((sender, command, label, args) -> {
            if(sender instanceof Player){
                Player p = (Player) sender;
                p.getInventory().addItem(new ItemStack(Material.CLOCK,1));
            }
         return true;
        })).setUsage("/subcommand tick tock").build()).build());
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
