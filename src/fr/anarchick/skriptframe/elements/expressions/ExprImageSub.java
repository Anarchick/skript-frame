package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.util.Utils;

@Name("image Definition")
@Description("Get the width or height of an image")
@Examples({
    "# /!\\ to resize an image you have to use the resize effect /!\\",
	"set {image} to image from url \"https://i.imgur.com/fC0OOYE.png\"",
	"set {_subimage} to cropped image at (0, 0, 128 and 128) of image {image}"
})
@Since("1.0")

public class ExprImageSub extends SimpleExpression<BufferedImage> {

	private static final String[] patterns = new String[] {
			"[the] (trimmed|cropped|clipped) image at %integers% of image %~bufferedimage%",
			"[the] image %~bufferedimage%'s (trimmed|cropped|clipped) image at %integers%"};
	
	static {
       Skript.registerExpression(ExprImageSub.class, BufferedImage.class, ExpressionType.COMBINED, patterns);
    }
	
	private Expression<BufferedImage> image;
	private Expression<Integer> crop;
	
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
		
		if (matchedPattern == 0 ) {
			crop = (Expression<Integer>) exprs[0];
			image = (Expression<BufferedImage>) exprs[1];	
		} else {
			image = (Expression<BufferedImage>) exprs[0];
			crop = (Expression<Integer>) exprs[1];
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "Sub of image " + image.getSingle(e) + "from " + crop.getAll(e);
	}

	@Override
	@Nullable
	protected BufferedImage[] get(Event e) {
		final Integer[] crops = crop.getArray(e);
		final BufferedImage img = image.getSingle(e);
		if ((Utils.isAnyObjectNull(crops, img)) || (crops.length < 4)) return null;
		final Integer x = crops[0],  y = crops[1],  w = crops[2],  h = crops[3];
		if (x < 0 || y < 0 || w <= 0 || h <= 0) return null;
		BufferedImage sub = null;
		if ((x + w <= img.getWidth()) && (y + h <= img.getHeight())) {
			try {
				 sub = img.getSubimage(x, y, w, h);
			} catch (RasterFormatException ex) {
				Skript.exception(ex);
			}
		}
		return new BufferedImage[] {sub};
	}

}
