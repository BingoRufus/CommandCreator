package me.bingorufus.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TabCompleter implements org.bukkit.command.TabCompleter {
   private final SubCommand subCommand;

    public TabCompleter(SubCommand subCommand) {
        this.subCommand = subCommand;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        ArrayList<String> out = new ArrayList<>();
        SubCommand writtenCommand = getSubCommand(subCommand,args);
        if(writtenCommand == null) return null;
        if(writtenCommand.getSubCommands().size() == 0 && writtenCommand.getTabHandler() != null){
            out.addAll(writtenCommand.getTabHandler().onTab(sender,command,label,args));
        }
            writtenCommand.getSubCommands().forEach(sub -> {
                if(sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) return;
                sub.getAliases().forEach((alias, tab) -> {
                    if (tab && alias.toUpperCase().startsWith(args[args.length - 1].toUpperCase())) out.add(alias);
                });
            });
        return out;
    }
    private SubCommand getSubCommand(SubCommand command, String[] args){
        if(command.getSubCommands().size() == 0 || args.length == 0) return command;
        for (SubCommand commandSubCommand : command.getSubCommands()) {
            for (String commandName : commandSubCommand.getAliases().keySet()) {
                if (commandName.equalsIgnoreCase(args[0]))
                    return getSubCommand(commandSubCommand, removeFirstItem(args));
                if (commandName.toUpperCase().startsWith(args[0].toUpperCase())) return command;
            }
        }
        return command;
    }
    private String[] removeFirstItem(String[] args){
        ArrayList<String > out = new ArrayList<>(Arrays.asList(args));
        out.remove(0);
        return out.toArray(new String[0]);
    }
}
