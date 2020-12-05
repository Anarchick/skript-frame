package fr.anarchick.skriptframe.elements.expressions;

import java.util.ArrayList;

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
import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.SkriptFrame;
import fr.anarchick.skriptframe.map.MapsManager;

@Name("MC colors palette")
@Description("Get all of the minecraft colors palette")
@Examples({
    "broadcast \"%all mc colors palette%\""
})
@Since("1.0")

public class ExprColorMCPalette extends SimpleExpression<Color> {

	private static Color[] palette;
	static {
		if (SkriptFrame.getBKCSupport()) {
	       Skript.registerExpression(ExprColorMCPalette.class, Color.class, ExpressionType.SIMPLE, "all (mc|mine[ ]craft) colors palette");
	       
	       ArrayList<Color> skriptColor = new ArrayList<Color>();
	       for (java.awt.Color color : MapsManager.getPalette()) {
	    	   skriptColor.add(new ColorRGB(color.getRed(), color.getGreen(), color.getBlue()));
	       }
	       palette = skriptColor.toArray(new Color[0]);
		}
	}
	
	@Override
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "all mc colors palette";
	}

	@Override
	@Nullable
	protected Color[] get(Event e) {
		return palette;
	}

}
