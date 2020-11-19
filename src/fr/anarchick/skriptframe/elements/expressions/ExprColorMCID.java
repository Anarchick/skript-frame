package fr.anarchick.skriptframe.elements.expressions;

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
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;

@Name("MC color ID")
@Description("Convert a color to the minecraft color palette ID")
@Examples({
	"# See https://minecraft.gamepedia.com/Map_item_format for more informations",
    "broadcast \"%mc color id from rgb(250, 238, 77)% should return 122\"",
	"# gold (30) * 4 + 2 = 122"
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class ExprColorMCID extends SimpleExpression<Number> {

	static {
	       Skript.registerExpression(ExprColorMCID.class, Number.class, ExpressionType.SIMPLE, "[the] (mc|mine[ ]craft) color id from %color%");
	   }
	
	private Expression<Color> color;
	
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
		color = (Expression<Color>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "mc color id from " + color.getSingle(e);
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		final Color c = color.getSingle(e);
		if (c == null) return null;
		final org.bukkit.Color bukkitColor = c.asBukkitColor();
		return new Number[] {(long) MapColorPalette.getColor(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue())};
	}

}
