package fr.rodtus.api.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * Created by Florian on 02/11/2017.
 */
public class setItem {
    public ItemStack setItem(Material material, String customName, List<String> lore, int amount, int data) {
        ItemStack item = new ItemStack(material, amount, (byte) data);
        ItemMeta customIT = item.getItemMeta();
        customIT.setDisplayName(customName);
        customIT.setLore(lore);
        item.setItemMeta(customIT);
        return item;
    }

    public ItemStack setPlayerSkull(Material material, String customName, List<String> lore, int amount, int data, Player player){
        ItemStack item = new ItemStack(material, amount, (short) data);
        SkullMeta customIT = (SkullMeta) item.getItemMeta();
        customIT.setOwner(player.getName());
        customIT.setDisplayName(customName);
        customIT.setLore(lore);
        item.setItemMeta(customIT);
        return item;
    }

    public ItemStack setSkull(String url, String customName, List<String> lore, int amount) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (url == null || url.isEmpty())
            return skull;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skullMeta.setDisplayName(customName);
        skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta);
        return skull;
    }

}
