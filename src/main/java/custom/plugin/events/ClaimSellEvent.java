package custom.plugin.events;

import custom.plugin.Claims;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClaimSellEvent implements Listener {

    private final Claims plugin;
    private final File file;
    private final YamlConfiguration config;

    public ClaimSellEvent(Claims plugin){
        this.plugin=plugin;
        file = new File(plugin.getDataFolder(), "/Dinar/bank.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryView gui = e.getView();
        Player player = (Player) e.getWhoClicked();
        Economy economy = plugin.getClaimManager().getEconomy();


        if(gui.getTitle().equalsIgnoreCase(ChatColor.BLACK+"Satın almak istiyor musun ?") && e.getCurrentItem().getType()!= Material.AIR){
            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE:
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    List<String> lore= meta.getLore();
                    Player p = Bukkit.getPlayer(lore.get(0));
                    String lores = lore.get(1);
                    String[] dizi = lores.split(" ");
                    double money = Double.parseDouble(dizi[1]);
                    Chunk chunk = p.getLocation().getChunk();
                    String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
                    player.closeInventory();
                    if(economy.getBalance(player)>money){
                        plugin.getClaimManager().sellChunk(chunkID);
                        plugin.getClaimManager().addChunk(chunkID,player.getUniqueId(),player.getDisplayName());
                        economy.withdrawPlayer(player,money);
                        economy.depositPlayer(p,money);
                        config.getConfigurationSection(player.getUniqueId().toString()).set("Money",economy.getBalance(player));
                        config.getConfigurationSection(p.getUniqueId().toString()).set("Money",economy.getBalance(p));
                        try {
                            config.save(file);
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                        player.sendMessage("Claim başarıyla satın alındı.");
                    }
                    else{
                        player.sendMessage("Yeterli bakiyen yok fakir");
                    }
                    break;
                case RED_CONCRETE:
                    player.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }
    }
}
