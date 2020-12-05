package fr.anarchick.skriptframe.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapPalette;

import com.bergerkiller.bukkit.common.map.MapColorPalette;
import com.bergerkiller.bukkit.common.map.MapDisplay;
import com.bergerkiller.bukkit.common.map.MapTexture;
import com.bergerkiller.bukkit.common.utils.ItemUtil;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.bergerkiller.generated.net.minecraft.server.PacketPlayOutMapHandle;

import fr.anarchick.skriptframe.SkriptFrame;
import fr.anarchick.skriptframe.util.Utils;

public class MapsManager {

	public static BufferedImage imageFile(String path) throws IOException {
		if (path == null) return null;
		return (ImageIO.read(new File(path)));
	}
	
	public static BufferedImage imageURL(String url) throws IOException {
		if (url == null) return null;
		return (ImageIO.read(new URL(url)));
	}
	
	// Async writing in the plugin's folder
	public static void saveImage(BufferedImage img, int id) {
		new Thread(() -> {
			try {
				ImageIO.write(img, "png", new File(SkriptFrame.getImagesDirectory(), "map"+id+".png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}
	
	public static BufferedImage resize(BufferedImage img, int w, int h) {
		if ((w < 0) || (h < 0)) return null;
		BufferedImage resized = new BufferedImage(w, h, img.getType());
	    Graphics2D g = resized.createGraphics();
	    g.drawImage(img, 0, 0, w, h, null);
	    g.dispose();
		return resized;
	}
	
	// Client side map from mapID
	public static void sendMap(final Player[] players, final int id, final BufferedImage img) {
		MapTexture texture = MapTexture.fromImage(img);
		PacketPlayOutMapHandle packet = PacketPlayOutMapHandle.createNew();
		packet.setItemId(id);
        packet.setScale((byte) 1);
        packet.setTrack(true);
        packet.setLocked(true);
        packet.setXmin(0);
        packet.setYmin(0);
        packet.setWidth(128);
        packet.setHeight(128);
        packet.setPixels(texture.getBuffer());
        packet.setCursors(new MapCursor[0]);
        for (Player player : players) {
        	PacketUtil.sendPacket(player, packet);
        }
	}
	
	public static void sendMap(BufferedImage img, Player[] players) {
		ItemStack item = MapDisplay.createMapItem(SkriptFrame.getInstance(), FrameMapDisplay.class);
		ItemUtil.getMetaTag(item).putValue("imagePath", "some/url/path.png");;
		for (Player player : players) {
			player.getInventory().addItem(item);
		}	
	}
	
	public static Color matchColor(int r, int g, int b) {
		return MapColorPalette.getRealColor(MapColorPalette.getColor(r, g, b));
	}
	
	public static Color matchColor(Color c) {
		return MapColorPalette.getRealColor(MapColorPalette.getColor(c));
	}

	// 58 * 4 values in 1.16
	private static Color[] palette;
	
	static {
		try {
			Field field = MapPalette.class.getDeclaredField("colors");
			field.setAccessible(true);
			palette = (Color[]) field.get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static Color[] getPalette() {
		return palette;
	}

	
	// Result can differ accross mc version
	public static byte[] getByteColorMap(int id) {
		byte[] colors = null;
		World world = Bukkit.getServer().getWorlds().get(0);
		try {
			Object craftworld = Utils.getNmsClass("CraftWorld").getMethod("getHandle").invoke(world);
			Object worldMap = craftworld.getClass().getMethod("a").invoke("map_"+id);
			colors = (byte[]) worldMap.getClass().getDeclaredField("colors").get(null);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return colors;
	}
	
	
}
