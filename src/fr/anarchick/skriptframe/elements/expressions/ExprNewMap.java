package fr.anarchick.skriptframe.elements.expressions;


import org.bukkit.Bukkit;
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

@Name("New map")
@Description("Generate a new map and return his id")
@Examples({
	"set {_mapId} to new map"
})
@Since("1.0")

public class ExprNewMap extends SimpleExpression<Number> {

	static {
	       Skript.registerExpression(ExprNewMap.class, Number.class, ExpressionType.SIMPLE, "new map");
	   }
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "New map";
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[] {Long.valueOf(Bukkit.createMap(Bukkit.getWorlds().get(0)).getId())};
	}

}
