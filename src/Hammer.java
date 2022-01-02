// tinkers constructのハンマーみたいなものの作成を目指します。

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class Blockbreak implements Listener {

    public Blockbreak(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void Hammer(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Block b = e.getBlock();

        float theta = p.getLocation().getPitch();
        float phi = p.getLocation().getYaw();


        if (theta > 50.0 || theta < -50.0) {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    b.getRelative(i, 0, j).breakNaturally();
        } else if ((45. < phi && phi < 145.) || (-145. < phi && phi < -45.)) {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    b.getRelative(0, i, j).breakNaturally();
        } else {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    b.getRelative(i, j, 0).breakNaturally();
        }
    }
}
