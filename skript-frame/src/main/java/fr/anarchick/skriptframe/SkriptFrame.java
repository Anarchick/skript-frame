package fr.anarchick.skriptframe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import fr.anarchick.skriptframe.map.FrameMapRenderer;
import fr.anarchick.skriptframe.map.MapsManager;

public class SkriptFrame extends JavaPlugin {

	private static SkriptFrame INSTANCE;
	private static SkriptAddon ADDON;
	
	private static final String IMAGES_DIRECTORY_NAME = "maps";
	private static File imagesDirectory;
	private static boolean isBKCommonLib = false;
	
	public static final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
	
	public static final Version MINIMUM_BKCOMMONLIB_VERSION = new Version(4, 6, 0);
	public static Version BKCOMMONLIB_VERSION;
	public static final Version SKRIPT_VERSION =
            new Version(pluginManager.getPlugin("Skript").getDescription().getVersion());

	public void onEnable() {
		INSTANCE = this;
		ADDON = Skript.registerAddon(this);
		isBKCommonLib = pluginManager.isPluginEnabled("BKCommonLib");
		
		if (isBKCommonLib) {
			Logging.info("Support of BKCommonLib syntaxes");
			BKCOMMONLIB_VERSION = new Version(pluginManager.getPlugin("BKCommonLib").getDescription().getVersion());
		}
		
		try {
			ADDON.loadClasses("fr.anarchick.skriptframe", "elements");
		} catch (IOException e) {
			e.printStackTrace();
			pluginManager.disablePlugin(this);
            return;
		}
		
		imagesDirectory = new File(this.getDataFolder(), IMAGES_DIRECTORY_NAME);
		if (!imagesDirectory.exists()) {
			try {
				imagesDirectory.mkdirs();
			} catch (SecurityException ex) {
				Logging.warning("Could not create '" + imagesDirectory.getName() + "' plugin directory.");
				Skript.exception(ex);
			}
		} else {
			// Load all images related to mapId
			File filesList[] = imagesDirectory.listFiles();
			final Pattern pattern = Pattern.compile("map\\d+.png"); // Only support PNG for now
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
					MapView view = MapsManager.getMap(id);
					if (view != null) {
						if ((image.getWidth() != 128) || (image.getHeight() != 128)) {
							image = MapsManager.resize(image, 128, 128);
						}
						FrameMapRenderer.drawImage(image, id, view);
						loaded++;
					}
				}
			}
			Logging.info("Amount of loaded frames: " + loaded);
				
		}
		
		int pluginId = 10386;
		Metrics metrics = new Metrics(this, pluginId);
		metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
			SKRIPT_VERSION.toString()));
		metrics.addCustomChart(new Metrics.SimplePie("bkcommonlib_version", () ->
			BKCOMMONLIB_VERSION.toString()));
		
		Logging.info("is enable!");
	}
	
	static public SkriptFrame getInstance( ) {
		return INSTANCE;
	}
	
	static public SkriptAddon getAddonInstance() {
		return ADDON;
	}
	
	static public File getImagesDirectory() {
		return imagesDirectory;
	}
	
	static public boolean getBKCSupport() {
		return isBKCommonLib;
	}
	
}
