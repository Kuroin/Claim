package custom.plugin;

import custom.plugin.commands.*;
import custom.plugin.events.ClaimEvent;
import custom.plugin.events.ClaimSellEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class Claims extends JavaPlugin {
    private ClaimManager claimManager;
    private PermManager permManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        claimManager = new ClaimManager(this);
        permManager = new PermManager(this);

        getCommand("claim").setExecutor(new ClaimCommand(this));
        getCommand("sellChunk").setExecutor(new SellChunk(this));
        getCommand("givePermission").setExecutor(new GivePermission(this));
        getCommand("removePermission").setExecutor(new RemovePermission(this));
        getCommand("sellClaim").setExecutor(new SellClaim(this));
        getCommand("chunkInfo").setExecutor(new ChunkInfo(this));
        getCommand("makeThief").setExecutor(new Thief(this));
        getCommand("removeThief").setExecutor(new RemoveThief(this));
        getCommand("try").setExecutor(new Try());

        getServer().getPluginManager().registerEvents(new ClaimSellEvent(this),this);
        getServer().getPluginManager().registerEvents(new ClaimEvent(this),this);

        if (!claimManager.setupEconomy()) {
            System.out.println("No plugin found. Disabling Vault.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public ClaimManager getClaimManager() {
        return claimManager;
    }
    public PermManager getPermManager(){return permManager;}
}
