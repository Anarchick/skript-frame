package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.bergerkiller.bukkit.common.map.MapColorPalette;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.SkriptFrame;

@Name("Image to mc image")
@Description("Convert an image to a mc image look")
@Examples({
	"set {image} to image from url \"https://i.imgur.com/fC0OOYE.png\"",
	"set {_imageMC} to convert {image} as mc image"
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class ExprImageMC extends SimpleExpression<BufferedImage> {

	static {
		if (SkriptFrame.getBKCSupport()) Skript.registerExpression(ExprImageMC.class, BufferedImage.class, ExpressionType.SIMPLE, "convert %~bufferedimage% as (mc|mine[ ]craft) image");
    }
	
	private Expression<BufferedImage> image;
	
	@Override
	public Class<? extends BufferedImage> getReturnType() {
		return BufferedImage.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "convert image to mc image from " + image.getSingle(e);
	}

	@Override
	protected BufferedImage[] get(Event e) {
		BufferedImage img = image.getSingle(e);
		if (img == null) return null;
		final byte[] values = MapColorPalette.convertImage(img);
		final int w = img.getWidth(), h = img.getHeight();
		int i = 0;
		BufferedImage result = new BufferedImage(w, h, img.getType());
		for (int y = 0; y < h ; y++) {
			for (int x = 0; x < w ; x++) {
				result.setRGB(x, y, MapColorPalette.getRealColor(values[i++]).getRGB());
			}
		}
		return new BufferedImage[] {result};
	}

}
