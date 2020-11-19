package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
import fr.anarchick.skriptframe.map.MapsManager;

@Name("Image URL")
@Description("Get an image from an URL")
@Examples({
	"set {_image} to image from url \"https://i.imgur.com/fC0OOYE.png\""
})
@Since("1.0")

public class ExprImageURL extends SimpleExpression<BufferedImage> {

	static {
	       Skript.registerExpression(ExprImageURL.class, BufferedImage.class, ExpressionType.SIMPLE, "[the] image [from] url %string%");
	   }
	
	private Expression<String> url;
	
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
		url = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "image from url " + url.getSingle(e);
	}

	@Override
	@Nullable
	protected BufferedImage[] get(Event e) {
		try {
			return new BufferedImage[] {MapsManager.imageURL(url.getSingle(e))};
		} catch (IOException ex) {
			Skript.exception(ex);
			return null;
		}
	}

}
