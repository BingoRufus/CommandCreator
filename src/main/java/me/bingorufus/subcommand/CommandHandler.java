package me.bingorufus.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandHandler {
     boolean execute(CommandSender sender, Command command,String label, String[] args);
}
