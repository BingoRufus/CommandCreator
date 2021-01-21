package me.bingorufus.commandbuilder.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabHandler {
    List<String> onTab(CommandSender sender, Command command, String label, String... args);
}
