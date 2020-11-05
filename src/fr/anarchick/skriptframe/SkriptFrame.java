package fr.anarchick.skriptframe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import fr.anarchick.skriptframe.map.FrameMapRenderer;
import fr.anarchick.skriptframe.map.MapsManager;

public class SkriptFrame extends JavaPlugin {

	static private SkriptFrame instance;
	static private SkriptAddon addon;
	
	static private final String IMAGES_DIRECTORY_NAME = "images";
	private static File imagesDirectory;
	
	public void onEnable() {
		if(instance != null)
            throw new IllegalStateException("Plugin initialized twice.");
		instance = this;
		addon = Skript.registerAddon(this);
		try {
			addon.loadClasses("fr.anarchick.skriptframe", "elements");
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
            return;
		}
		
		imagesDirectory = new File(this.getDataFolder(), IMAGES_DIRECTORY_NAME);
		if (!imagesDirectory.exists()) {
			try {
				imagesDirectory.mkdirs();
			} catch (SecurityException ex) {
				Skript.error("Could not create '" + imagesDirectory.getName() + "' plugin directory.");
				Skript.exception(ex);
			}
		} else {
			File filesList[] = imagesDirectory.listFiles();
			final Pattern pattern = Pattern.compile("map\\d+.png"); // Only support for PNG for now
			final Pattern patternId = Pattern.compile("(\\d+)");
		    Matcher matcherId;
		    int id;
		    int loaded = 0; // Amount of loaded frames
			for(File file : filesList) {
				if (pattern.matcher(file.getName()).matches()) {
					matcherId = patternId.matcher(file.getName());
					matcherId.find(); // Assume equal true
					id = Integer.parseInt(matcherId.group());
					BufferedImage image = null;
					try {
						image = ImageIO.read(file);
					} catch (IOException ex) {
						Skript.exception(ex);
					}
					@SuppressWarnings("deprecation")
					MapView view = Bukkit.getMap(id);
					if (view != null) {
						if (image.getWidth() != 128 || image.getHeight() != 128) {
							image = MapsManager.resize(image, 128, 128);
						}
						FrameMapRenderer.drawImage(image, id, view);
						loaded++;
					}
				}
			}
			Skript.info("Amount of loaded frames: " + loaded);
				
		}
		
		Bukkit.getLogger().info("[Skript-Frame] has been enabled!");
		
		/*
		Data data = Data.loadData("idcounts.dat");
		if (data != null) Bukkit.getLogger().info(data.toString());
		if (data == null) Bukkit.getLogger().info("data is null");
		*/
	}
	
	static public SkriptFrame getInstance( ) {
		return instance;
	}
	
	static public SkriptAddon getAddonInstance() {
		return addon;
	}
	
	static public File getImagesDirectory() {
		return imagesDirectory;
	}
	
}
