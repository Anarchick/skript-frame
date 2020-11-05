package fr.anarchick.skriptframe.map;

import java.util.Collection;
import java.util.Collections;

import com.bergerkiller.bukkit.common.map.MapDisplay;
import com.bergerkiller.bukkit.common.map.MapTexture;



public class FrameMapDisplay extends MapDisplay {
	
	private static Collection<FrameMapDisplay> all_maplands_displays = Collections.emptySet();
	
	public static Collection<FrameMapDisplay> getAllDisplays() {
        return all_maplands_displays;
	}
	
    @Override
    public void onAttached() {
    	MapTexture image = MapTexture.fromImageFile(properties.get("imagepath", ""));
        getLayer().draw(image, 0, 0);
	}
}
