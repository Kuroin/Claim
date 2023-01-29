package custom.plugin.commands;

import custom.plugin.Claims;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class Thief implements CommandExecutor {
    private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();
    private Claims main;
    public Thief(Claims main){
        this.main=main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(sender instanceof  Player){
                Player target = Bukkit.getPlayer(args[0]);
                Player player = (Player) sender;
                if(target==null){
                    player.sendMessage("Böyle bir oyuncu bulunmamaktadır.");
                }
                if(!target.hasPermission("claim.thief")){
                    main.getPermManager().addPermission(target,"claim.thief");
                    target.sendMessage("Hırsızsın");
                }
                else{
                    target.sendMessage("Zaten Hırsızsın");
                }
            }
        return false;
    }
}
