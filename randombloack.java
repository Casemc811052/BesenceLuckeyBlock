package net.besence.randomblock;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomBlockPlugin extends JavaPlugin implements Listener, CommandExecutor {
    private List<Material> eventBlocks = Arrays.asList(Material.DIAMOND_BLOCK);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("beluck").setExecutor(this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (eventBlocks.contains(block.getType())) {
            event.setCancelled(true);

            if (block.getType() == Material.DIAMOND_BLOCK && block.hasMetadata("BesenceLuckeyBlock")) {
                int randomIndex = new Random().nextInt(11);
                switch (randomIndex) {
                    case 0:
                        giveDiamondBlocks(player);
                        break;
                    case 1:
                        player.setHealth(0);
                        break;
                    case 2:
                        giveFlowers(player);
                        break;
                    case 3:
                        giveJumpBoost(player);
                        break;
                    case 4:
                        giveSpeedBoost(player);
                        break;
                    case 5:
                        giveFlight(player);
                        break;
                    case 6:
                        giveWitherEffect(player);
                        break;
                    case 7:
                        sendToTheEnd(player);
                        break;
                    case 8:
                        giveBlindness(player);
                        break;
                    case 9:
                        trapInObsidian(player);
                        break;
                    case 10:
                        liftUp(player);
                        break;
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("beluck")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
                    giveEventBlock(player);
                    return true;
                }
            }
        }
        return false;
    }

    private void giveEventBlock(Player player) {
        ItemStack eventBlock = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta meta = eventBlock.getItemMeta();
        meta.setDisplayName("BesenceLuckeyBlock");
        eventBlock.setItemMeta(meta);
        player.getInventory().addItem(eventBlock);
        player.sendMessage("You received a luck block!");
    }

    private void giveDiamondBlocks(Player player) {
        ItemStack diamondBlocks = new ItemStack(Material.DIAMOND_BLOCK, 10);
        player.getInventory().addItem(diamondBlocks);
        player.sendMessage("You received 10 diamond blocks!");
    }

    private void giveFlowers(Player player) {
        ItemStack flowers = new ItemStack(Material.YELLOW_FLOWER, 64);
        player.getInventory().addItem(flowers);
        player.sendMessage("You received a bunch of flowers!");
    }

    private void giveJumpBoost(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 2));
        player.sendMessage("You received a jump boost!");
    }

    private void giveSpeedBoost(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 2));
        player.sendMessage("You received a speed boost!");
    }

    private void giveFlight(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage("You can now fly!");
    }

    private void giveWitherEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 10, 2));
        player.sendMessage("You received the wither effect!");
    }

    private void sendToTheEnd(Player player) {
        player.teleport(player.getWorld().getEnvironment() == World.Environment.THE_END ?
                player.getWorld().getSpawnLocation() :
                player.getWorld().getEnvironment().getWorld().getSpawnLocation());
        player.sendMessage("You have been teleported to the End!");
    }

    private void giveBlindness(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 2));
        player.sendMessage("You are now blinded!");
    }

    private void trapInObsidian(Player player) {
        Location location = player.getLocation();
        location.getBlock().setType(Material.OBSIDIAN);
        player.teleport(location);
        player.sendMessage("You are trapped in obsidian!");
    }

    private void liftUp(Player player) {
        Location location = player.getLocation();
        double y = location.getY() + 50;
        location.setY(y);
        player.teleport(location);
        player.sendMessage("You are lifted high up in the sky!");
    }
}
