package fr.anarchick.skriptframe.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import com.bergerkiller.bukkit.common.map.MapDisplay;

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
import fr.anarchick.skriptframe.SkriptFrame;
import fr.anarchick.skriptframe.map.FrameMapDisplay;

@Name("New map item")
@Description("Generate a new map and return the item")
@Examples({
	"set {_mapItem} to new map item"
})
@RequiredPlugins({"BKCommonLib"})
@Since("1.0")

public class ExprNewMap extends SimpleExpression<ItemStack> {

	static {
		if (SkriptFrame.getBKCSupport()) Skript.registerExpression(ExprNewMap.class, ItemStack.class, ExpressionType.SIMPLE, "new map item");
    }
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
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
	protected ItemStack[] get(Event e) {
		return new ItemStack[] {MapDisplay.createMapItem(SkriptFrame.getInstance(), FrameMapDisplay.class)};
	}

}
