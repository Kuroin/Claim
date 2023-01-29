package custom.plugin.commands;

import custom.plugin.Claims;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SellChunk implements CommandExecutor {
    private final Claims plugin;
    private File file;
    private YamlConfiguration config;

    public SellChunk(Claims plugin){
        this.plugin=plugin;
        file = new File(plugin.getDataFolder(),"/Dinar/bank.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            Chunk chunk = player.getLocation().getChunk();
            Economy economy = plugin.getClaimManager().getEconomy();
            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
            if(plugin.getClaimManager().isChunk(chunkID)){
                plugin.getClaimManager().sellChunk(chunkID);
                player.sendMessage("Chunk başarıyla satıldı.");
                economy.depositPlayer(player,20.0);
                config.getConfigurationSection(player.getUniqueId().toString()).set("Money",(int)economy.getBalance(player));
                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            else{
                player.sendMessage(ChatColor.DARK_RED+"Sahibi olmadığın chunk'ı satamazsın.");
                return false;
            }
        }
        return true;
    }
}
