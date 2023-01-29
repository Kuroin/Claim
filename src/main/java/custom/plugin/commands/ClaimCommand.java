package custom.plugin.commands;

import custom.plugin.Claims;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ClaimCommand implements CommandExecutor {
    private final Claims plugin;
    private final File file;
    private final YamlConfiguration config;

    public ClaimCommand(Claims plugin){
        this.plugin=plugin;
        file = new File(Bukkit.getPluginManager().getPlugin("Dinar").getDataFolder(),"/bank.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            Chunk chunk = player.getLocation().getChunk();

            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
            Economy economy = plugin.getClaimManager().getEconomy();

            if(plugin.getClaimManager().isChunk(chunkID)){
                String chunkOwner = plugin.getClaimManager().getUsername(chunkID);
                player.sendMessage("Bu chunk"+ ChatColor.RED+chunkOwner+" "+"aittir.");
                return false;
            }
            else{
                if(economy.getBalance(player)>=500){
                    plugin.getClaimManager().addChunk(chunkID,player.getUniqueId(),player.getName());
                    economy.withdrawPlayer(player,500);
                    int money= (int)economy.getBalance(player);
                    config.getConfigurationSection(player.getUniqueId().toString()).set("Money",money);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.sendMessage("Bu chunk'ı satın aldınız.");
                    return true;
                }
                else{
                    player.sendMessage("Yetersiz bakiye");
                    return false;
                }
            }
        }

        return false;
    }
}
