package fr.anarchick.skriptframe.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
	
	private final String nms = Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit.".length()); 
	
	public String getNMS( ) {
		return nms;
	}
	static public boolean isAnyObjectNull(Object... objects) {
	    for (Object o: objects) {
	        if (o == null) {
	            return true;
	        }
	    }
	    return false;
	}
	
	static public Double fractional(Double n) {
		n = Math.abs(n);
		String doubleAsText = n.toString();
		return Double.parseDouble("0."+doubleAsText.split("\\.")[1]);
	}
	
	static public void sendPacket(Player player, Object packet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
		Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
	    Object plrConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
	    plrConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(plrConnection, packet);
	}

	static private Map<String, Class<?>> Classes = new HashMap<String, Class<?>>();
	
	static public Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
		return Classes.putIfAbsent(nmsClassName, Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName));
		/*
		if ( Classes.containsKey(nmsClassName) == true) {
			return Classes.get(nmsClassName);
		} else {
			Class<?> clazz = Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
			Classes.put(nmsClassName, clazz);
			return clazz;
		}
		*/
	   
	}
}

	
