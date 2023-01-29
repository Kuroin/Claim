package custom.plugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ClaimManager {

    private final File file;
    private final YamlConfiguration config;
    private Economy econ = null;
    private final Claims main;
    public ClaimManager(Claims main){
        this.main=main;
        if(!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(),"claims.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void addChunk(String chunk, UUID owner, String username){
        config.createSection(chunk);
        config.getConfigurationSection(chunk).set("Owner",owner.toString());
        config.getConfigurationSection(chunk).set("Username",username);
        config.getConfigurationSection(chunk).set("Allowed", Arrays.asList());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isChunk(String chunk){
        return config.getConfigurationSection(chunk)!=null;
    }

    public void addPermission(UUID player,String chunk){
        List<String> alloweds = config.getConfigurationSection(chunk).getStringList("Allowed");
        alloweds.add(player.toString());
        config.getConfigurationSection(chunk).set("Allowed", alloweds);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removePermission(UUID player,String chunk){
        List<String> alloweds = config.getConfigurationSection(chunk).getStringList("Allowed");
        alloweds.remove(player.toString());
        config.getConfigurationSection(chunk).set("Allowed", alloweds);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sellChunk(String chunk){
        config.set(chunk,null);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public UUID getOwner(String chunk){
        return UUID.fromString(config.getConfigurationSection(chunk).getString("Owner"));
    }

    public String getUsername(String chunk){
       return config.getConfigurationSection(chunk).getString("Username");
    }

    public List<String> getAllowed(String chunk){
        return config.getConfigurationSection(chunk).getStringList("Allowed");
    }


    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = main.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    public  Economy getEconomy() {
        return econ;
    }
}
