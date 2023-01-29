package custom.plugin.commands;

import custom.plugin.Claims;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePermission implements CommandExecutor {
    private final Claims plugin;

    public RemovePermission(Claims plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Chunk chunk = player.getLocation().getChunk();

            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";

            if(plugin.getClaimManager().isChunk(chunkID)){
                if(args.length>0){
                    Player p = Bukkit.getPlayer(args[0]);
                    if(p != null){
                        if(p==player){
                            player.sendMessage(ChatColor.DARK_RED + "Kendini etiketleyemezsin.");
                            return false;
                        }
                        plugin.getClaimManager().removePermission(p.getUniqueId(),chunkID);
                        sender.sendMessage(p.getName()+" kullanıcısından izin kaldırıldı.");
                        p.sendMessage("Bu chunk'a blok koyma iznin kaldırıldı.");
                        return true;
                    }
                    else{
                        player.sendMessage(ChatColor.DARK_RED+"Birini etiketlemelisin.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
