package unshi.unshiid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blockbreak implements Listener {

    public Blockbreak(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void Kyoka(BlockDamageEvent e) {
        Block block = e.getBlock();

        if(block.getType() != Material.DROPPER)
            return;

        Dropper dropper = (Dropper) block.getState();

        Inventory inventory = dropper.getInventory();

        int toolIndex = inventory.first(Material.NETHERITE_PICKAXE);

        if(toolIndex == -1)
            return;

        ItemStack tool = inventory.getItem(toolIndex);

        ItemMeta toolMeta = tool.getItemMeta();

        List<String> lore = toolMeta.hasLore() ? toolMeta.getLore() : new ArrayList<String>();


        int hayasa = 0,koun = 0;

        int hIndex = -1,kIndex = -1;

        if(lore != null)
        for(int i = 0; i < lore.size(); i++){
            if(lore.get(i).matches("Red:[0-9]+")) {
                hayasa = Integer.parseInt(lore.get(i).substring(4));
                hIndex = i;
            }else if(lore.get(i).matches("Blue:[0-9]+")){
                koun = Integer.parseInt(lore.get(i).substring(5));
                kIndex = i;
            }
        }

        if(hIndex == -1){
            lore.add("Red:0");
            hIndex = lore.size()-1;
        }

        if(kIndex == -1){
            lore.add("Blue:0");
            kIndex = lore.size() -1 ;
        }

        HashMap<Integer, ? extends ItemStack> red,blue;

        red = inventory.all(Material.REDSTONE);
        blue = inventory.all(Material.LAPIS_LAZULI);

        for(Map.Entry<Integer, ? extends ItemStack> item : red.entrySet()) {
            hayasa += item.getValue().getAmount();
            inventory.clear(item.getKey());
        }

        for(Map.Entry<Integer, ? extends ItemStack> item : blue.entrySet()) {
            koun += item.getValue().getAmount();
            inventory.clear(item.getKey());
        }

        lore.set(hIndex,"Red:"+hayasa);
        lore.set(kIndex,"Blue:"+koun);

        toolMeta.setLore(lore);

        if((int) Math.log1p(hayasa) > 0)
            toolMeta.addEnchant(Enchantment.DIG_SPEED,(int) Math.log1p(hayasa),true);

        if((int) Math.log1p(koun) > 0)
            toolMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS,(int) Math.log1p(koun),true);

        tool.setItemMeta(toolMeta);

        inventory.setItem(toolIndex,tool);

        Location blockLoc = block.getLocation();
        World world = blockLoc.getWorld();

        world.playSound(blockLoc, Sound.BLOCK_ANVIL_USE,1,1);
    }
}
