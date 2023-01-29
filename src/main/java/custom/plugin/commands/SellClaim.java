package custom.plugin.commands;

import custom.plugin.Claims;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SellClaim implements CommandExecutor {

    private final Claims plugin;

    public SellClaim(Claims plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Chunk chunk = player.getLocation().getChunk();
            String chunkID = chunk.getX()+"x"+chunk.getZ()+"y";
            if(args.length==2){
                Player p = Bukkit.getPlayer(args[0]);
                double money = Double.parseDouble(args[1]);
                if(p!=null && plugin.getClaimManager().isChunk(chunkID)){
                    if(plugin.getClaimManager().getOwner(chunkID).equals(player.getUniqueId())) {
                        Inventory gui = Bukkit.createInventory(null,9, ChatColor.BLACK+"Satın almak istiyor musun ?");

                        ItemStack yes = new ItemStack(Material.GREEN_CONCRETE);
                        ItemMeta metaYes = yes.getItemMeta();
                        metaYes.setDisplayName(ChatColor.GREEN+"Kabul et");
                        ArrayList<String> loreYes = new ArrayList<>();
                        loreYes.add(player.getName());
                        loreYes.add(ChatColor.GOLD+"Fiyatı:"+" "+money+" "+"sol");
                        loreYes.add(" ");
                        metaYes.setLore(loreYes);
                        yes.setItemMeta(metaYes);

                        ItemStack no = new ItemStack(Material.RED_CONCRETE);
                        ItemMeta metaNo = no.getItemMeta();
                        metaNo.setDisplayName(ChatColor.RED+"Reddet");
                        no.setItemMeta(metaNo);

                        gui.setItem(3,yes);
                        gui.setItem(5,no);
                        p.openInventory(gui);
                    }
                }
            }
        }
        return false;
    }
}
