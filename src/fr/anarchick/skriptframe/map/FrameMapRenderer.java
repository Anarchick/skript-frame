package fr.anarchick.skriptframe.map;

import java.awt.image.BufferedImage;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;


public class FrameMapRenderer extends MapRenderer{

	private BufferedImage img;
	
	public FrameMapRenderer(BufferedImage image, int id, MapView view) {
		this.img = image;
		view.addRenderer(this);
	}
	
	// Only this method should be called
	static public void drawImage(BufferedImage image, int id, MapView view) {
		view.getRenderers().clear();
		new FrameMapRenderer(image, id, view);
	}
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		view.setScale(MapView.Scale.NORMAL);
        canvas.drawImage(0,0,this.img);
    }
	
}
