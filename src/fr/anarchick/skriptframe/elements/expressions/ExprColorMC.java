package fr.anarchick.skriptframe.elements.expressions;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

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
import ch.njol.skript.util.Color;
import ch.njol.skript.util.ColorRGB;
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.SkriptFrame;
import fr.anarchick.skriptframe.map.MapsManager;

@Name("MC color")
@Description("Convert a color to the minecraft color palette")
@Examples({
    "broadcast \"%mc color from rgb(128, 177, 57)% should return 127, 178, 56\""
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class ExprColorMC extends SimpleExpression<Color> {

	static {
		if (SkriptFrame.getBKCSupport()) Skript.registerExpression(ExprColorMC.class, Color.class, ExpressionType.SIMPLE, "[the] (mc|mine[ ]craft) color from %color%");
    }
	
	private Expression<Color> color;
	
	@Override
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		color = (Expression<Color>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "mc color " + color.getSingle(e);
	}

	@Override
	@Nullable
	protected Color[] get(Event e) {
		final Color c = color.getSingle(e);
		if (c == null) return null;
		final org.bukkit.Color bukkitColor = c.asBukkitColor();
		final java.awt.Color javaColor = MapsManager.matchColor(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
		return new Color[] {new ColorRGB(javaColor.getRed(), javaColor.getGreen(), javaColor.getBlue())};
	}

}
