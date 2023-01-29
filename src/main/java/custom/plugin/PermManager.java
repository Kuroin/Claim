package custom.plugin;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermManager {

    private Claims main;
    public PermManager(Claims main){
        this.main=main;
    }
    private Map<Player, PermissionAttachment> attachments = new HashMap<>();

    // Add permission to player
    public void addPermission(Player player, String permission) {
        PermissionAttachment attachment = attachments.get(player);
        if (attachment == null) {
            attachment = player.addAttachment(main);
            attachments.put(player, attachment);
        }
        attachment.setPermission(permission, true);
    }

    // Remove permission from player
    public void removePermission(Player player, String permission) {
        PermissionAttachment attachment = attachments.get(player);
        if (attachment != null) {
            attachment.unsetPermission(permission);
            player.recalculatePermissions();
        }
    }

    // Remove all permissions from player
    public void removeAllPermissions(Player player) {
        PermissionAttachment attachment = attachments.remove(player);
        if (attachment != null) {
            attachment.remove();
        }
    }



}
