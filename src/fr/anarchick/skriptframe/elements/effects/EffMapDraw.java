package fr.anarchick.skriptframe.elements.effects;

import java.awt.image.BufferedImage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.SkriptFrame;
//import fr.anarchick.skriptframe.map.CustomMapView;
import fr.anarchick.skriptframe.map.FrameMapRenderer;
import fr.anarchick.skriptframe.map.MapsManager;
import fr.anarchick.skriptframe.util.Utils;

@Name("Draw image on map")
@Description("Draw a given image (from URL or FILE) to a map item")
@Examples({
	"set {image} to image from url arg-1",
	"draw image {image} to map 0"
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class EffMapDraw extends Effect {

	private static final String[] patterns = new String[] {
			"draw image %~bufferedimage% to map %integer%",
			"draw image %~bufferedimage% to map %integer% for %players%"};
	
	static {
		if (SkriptFrame.getBKCSupport()) Skript.registerEffect(EffMapDraw.class, patterns);
    }
	
	private Expression<BufferedImage> image;
	private Expression<Integer> id;
	@Nullable
	private Expression<Player> players;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		id = (Expression<Integer>) exprs[1];
		players = exprs.length > 2 ? (Expression<Player>) exprs[2] : null;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "draw image " + image.toString(e, debug) + " to map id " + id.toString(e, debug);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void execute(Event e) {
		BufferedImage img = image.getSingle(e);
		final Integer _id = id.getSingle(e);
		if (Utils.isAnyObjectNull(img, _id)) return;
		if ((img.getWidth() != 128) || (img.getHeight() != 128)) {
			img = MapsManager.resize(img, 128, 128);
		}
		// if client side
		if (players != null) {
			MapsManager.sendMap(players.getArray(e), _id, img);
			/*
			final CustomMapView view = new CustomMapView(_id);
			FrameMapRenderer.drawImage(img, _id, view);
			*/
		} else {
			final MapView view = Bukkit.getMap(_id);
			if (view != null) {
				FrameMapRenderer.drawImage(img, _id, view);
				MapsManager.saveImage(img, _id);
			}
		}
	}

}
