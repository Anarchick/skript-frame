package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprImageFromURL extends SimpleExpression<BufferedImage> {

	static {
	       Skript.registerExpression(ExprImageFromURL.class, BufferedImage.class, ExpressionType.SIMPLE, "[the] image [from] url %string%");
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
		String u = url.getSingle(e);
		if (u != null) {
			try {
				return CollectionUtils.array((ImageIO.read(new URL(u))));
			} catch (IOException ex) {
				Skript.exception(ex);
			}
		}
		return null;
	}

}
