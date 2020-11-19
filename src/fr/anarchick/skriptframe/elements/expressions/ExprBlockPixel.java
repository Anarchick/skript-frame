package fr.anarchick.skriptframe.elements.expressions;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.SkriptConfig;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import fr.anarchick.skriptframe.util.Utils;

@Name("Block pixel")
@Description("Return a vector of relative cursor's pixel position from target block face")
@Examples({
	"set {_vector} to target block pixel of player",
	"broadcast \"X:%x component of {_vector}% and Y: %y component of {_vector}%\""
})
@Since("1.0")

public class ExprBlockPixel extends SimplePropertyExpression<Entity, Vector> {

	static {
	       register(ExprBlockPixel.class, Vector.class, "target block pixel", "entity");
	   }

	@Override
	public Class<? extends Vector> getReturnType() {
		return Vector.class;
	}
	
	@Override
	@Nullable
	public Vector convert(Entity ent) {
		Vector vector = null;
		if (ent != null) {
			Double x, y;
			final RayTraceResult raycast = ((LivingEntity) ent).rayTraceBlocks(SkriptConfig.maxTargetBlockDistance.value());
			if (raycast != null) {
				final Vector hit = raycast.getHitPosition();
				final BlockFace face = raycast.getHitBlockFace();
				switch(face) {
					case DOWN:
					case UP:
						x = hit.getX();
						y = hit.getZ();
						break;
					case WEST:
					case EAST:
						x = hit.getZ();
						y = hit.getY();
						break;
					case NORTH:
					case SOUTH:
						x = hit.getX();
						y = hit.getY();
						break;
					default:
						return null;
				}
				x = Utils.fractional(x);
				y = Utils.fractional(y);
				if (face == BlockFace.NORTH || face == BlockFace.WEST) x = 1-x;
				vector = new Vector(Math.floor(x*128), Math.floor(y*128), 0); // Pixels are in range of 0-128 to match with the definition of a map
			}
		}
		return vector;
	}

	@Override
	protected String getPropertyName() {
		return "block pixel";
	}

}
