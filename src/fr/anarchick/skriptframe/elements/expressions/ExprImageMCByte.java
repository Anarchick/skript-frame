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

@Name("Image to mc palette ID")
@Description({
	"Convert an image to a mc palette ID, it return a list of numbers in range -128 to 128 (signed) or 0 to 256 (unsigned)",
	"This is intended to be use with packet"
})
@Examples({
    "# Be sure to use an image of size 128*128",
	"set {image} to image from url \"https://i.imgur.com/fC0OOYE.png\"",
	"set {_imageMCByte} to convert {image} as mc palette"
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class ExprImageMCByte extends SimpleExpression<Number> {

	static {
	       Skript.registerExpression(ExprImageMCByte.class, Number.class, ExpressionType.SIMPLE, "convert %~bufferedimage% as (mc|mine[ ]craft) palette id (0¦signed|1¦unsigned)");
	   }
	
	private Expression<BufferedImage> image;
	private int element;
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		element = parser.mark;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "Image to mc byte palette from " + image.getSingle(e);
	}

	@Override
	protected Number[] get(Event e) {
		BufferedImage img = image.getSingle(e);
		if (img == null) return null;
		final byte[] values = MapColorPalette.convertImage(img);
		final Number[] result = new Number[values.length];
		if (element == 0) {
			for (int i = 0; i < values.length; i++) {
				result[i] = (long) values[i]; // range -128-128
			}
		} else {
			for (int i = 0; i < values.length; i++) {
				result[i] = (long) (values[i] & 0xFF); // range 0-255
			}
		}
		return result;
	}

}
