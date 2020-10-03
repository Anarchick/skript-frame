package fr.anarchick.skriptframe;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkriptFrame extends JavaPlugin {

	SkriptFrame instance;
	SkriptAddon addon;
	
	public void onEnable() {
		instance = this;
		addon = Skript.registerAddon(this);
		try {
			addon.loadClasses("fr.anarchick.skriptframe", "elements");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.getLogger().info("[Skript-Frame] has been enabled!");
	}
	
	public SkriptFrame getInstance( ) {
		return instance;
	}
	
	public SkriptAddon getAddonInstance() {
		return addon;
	}
}
