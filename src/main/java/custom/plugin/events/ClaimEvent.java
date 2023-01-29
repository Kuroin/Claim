package custom.plugin.events;

import custom.plugin.Claims;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClaimEvent implements Listener {

    private final Claims plugin;

    public ClaimEvent(Claims plugin){
        this.plugin=plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() != null){
            Player player= e.getPlayer();
            if(player.isOp()) return;
            Chunk chunk = e.getClickedBlock().getChunk();
            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
            if(plugin.getClaimManager().isChunk(chunkID)){
                if(!plugin.getClaimManager().getAllowed(chunkID).contains(player.getUniqueId().toString())) {
                    if (!plugin.getClaimManager().getOwner(chunkID).equals(player.getUniqueId())) {
                        if(!player.hasPermission("claim.thief")){
                            e.setCancelled(true);
                            String user = plugin.getClaimManager().getUsername(chunkID);
                            player.sendMessage("Bu chunk'ın sahibi: " + ChatColor.RED + user);
                        }else{
                            player.sendMessage("Tıkla babam tıkla");
                        }
                    }
                }
            }
            else{
                if(!player.hasPermission("claim.thief")){
                    player.sendMessage("Herhangi bir şey yapmak için bu chunk'ı satın almalısınız.");
                    e.setCancelled(true);
                }
                else{
                    player.sendMessage("Tıklaaa");
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        plugin.getPermManager().removeAllPermissions(e.getPlayer());
    }
}
