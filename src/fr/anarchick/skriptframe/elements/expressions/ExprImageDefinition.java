package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;

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

@Name("Image definition")
@Description("Get the width or height of an image")
@Examples({
    "# /!\\ to resize an image you have to use the resize effect /!\\",
	"set {_image} to image from url \"https://i.imgur.com/fC0OOYE.png\"",
	"broadcast \"%width of {_image}%\""
})
@Since("1.0")

public class ExprImageDefinition extends SimpleExpression<Number> {

	static {
	       Skript.registerExpression(ExprImageDefinition.class, Number.class, ExpressionType.SIMPLE, "[the] (0¦width|1¦height) of image %~bufferedimage%");
	   }
	
	private Expression<BufferedImage> image;
	private int element;
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
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
		return "Definition of image " + image.getSingle(e);
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		BufferedImage img = image.getSingle(e);
		if (img == null) return null;
		return new Number[] {((element == 0) ? img.getWidth() : img.getHeight())};
	}

}
