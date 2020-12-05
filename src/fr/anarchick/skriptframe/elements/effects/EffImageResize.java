package fr.anarchick.skriptframe.elements.effects;

import java.awt.image.BufferedImage;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.anarchick.skriptframe.map.MapsManager;
import fr.anarchick.skriptframe.util.Utils;

@Name("Resize image")
@Description("Resize an image by given pixel definition")
@Examples({
	"set {image} to image from url arg-1",
	"resize image {image} by 256 and 256"
})
@Since("1.0")

public class EffImageResize extends Effect {

	static {
		Skript.registerEffect(EffImageResize.class, "resize image %~bufferedimage% by %integer% and %integer%");
    }
	
	private Expression<BufferedImage> image;
	private Expression<Integer> w;
	private Expression<Integer> h;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		w = (Expression<Integer>) exprs[1];
		h = (Expression<Integer>) exprs[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "resize image " + image.toString(e, debug) + " to size " + w.toString(e, debug) + " and " + h.toString(e, debug);
	}

	@Override
	protected void execute(Event e) {
		final BufferedImage img = image.getSingle(e);
		final Integer _w = w.getSingle(e);
		final Integer _h = h.getSingle(e);
		if (Utils.isAnyObjectNull(img, _w, _h) || (_w <= 0) || (_h <= 0)) return;
		final BufferedImage resized = MapsManager.resize(img, _w, _h);
	    image.change(e, CollectionUtils.array(resized), ChangeMode.SET);
		}

}
