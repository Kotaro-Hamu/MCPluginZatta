package unshi.unshiid;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Blockbreak implements Listener {

    public Blockbreak(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void LoreTest(BlockBreakEvent e){
        Player p = e.getPlayer();

        ItemStack tool = p.getInventory().getItemInMainHand();

        ItemMeta meta = tool.getItemMeta();

        if(meta == null)
            return;

        List<String> lore = new ArrayList<String>();

        lore.add("test");

        meta.setLore(lore);

        tool.setItemMeta(meta);

        p.getInventory().setItemInMainHand(tool);

        p.sendMessage(tool.displayName());
    }
}
