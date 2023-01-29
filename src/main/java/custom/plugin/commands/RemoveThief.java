package custom.plugin.commands;

import custom.plugin.Claims;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveThief implements CommandExecutor {

    private Claims main;
    public RemoveThief(Claims main){
        this.main=main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if(target==null){
                player.sendMessage("Böyle bir oyuncu bulunmamaktadır.");
            }
            if(target.hasPermission("claim.thief")){
                main.getPermManager().removePermission(target,"claim.thief");
                target.sendMessage("Artık hırsız değilsin");
            }
            else{
                target.sendMessage("Kaldırılamadı");
            }
        }
        return false;
    }
}
