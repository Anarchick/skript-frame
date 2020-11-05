package fr.anarchick.skriptframe.elements.expressions;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.map.MapsManager;

public class ExprImageFile extends SimpleExpression<BufferedImage> {

	static {
	       Skript.registerExpression(ExprImageFile.class, BufferedImage.class, ExpressionType.SIMPLE, "[the] image [from] file %string%");
	   }
	
	private Expression<String> path;
	
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
		path = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "image from file path " + path.getSingle(e);
	}

	@Override
	@Nullable
	protected BufferedImage[] get(Event e) {
		try {
			return new BufferedImage[] {MapsManager.imageFile(path.getSingle(e))} ;
		} catch (IOException ex) {
			Skript.exception(ex);
			return null;
		}
	}

}
