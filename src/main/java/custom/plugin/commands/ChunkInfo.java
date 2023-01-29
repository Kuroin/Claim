package custom.plugin.commands;

import custom.plugin.Claims;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkInfo implements CommandExecutor {
    private final Claims plugin;

    public ChunkInfo(Claims plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Chunk chunk = player.getLocation().getChunk();
            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
            if(plugin.getClaimManager().isChunk(chunkID)){
                player.sendMessage("Bu chunk"+plugin.getClaimManager().getUsername(chunkID)+"aittir.");
            }
            else{
                player.sendMessage("Bu chunk sahipsizdir.");
            }
        }
        return false;
    }
}
