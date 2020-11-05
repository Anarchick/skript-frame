package fr.anarchick.skriptframe.elements.effects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;

@Name("Save image")
@Description("Save an image as a file")
@Examples({
    "# /!\\ if the file already exist, it will be replaced /!\\ \n" +
	"# /!\\ You can't use a local variable in this effect /!\\ \n" +

    "command download: <string> <string>\n" +
        "\ttrigger:\n" +
        	"\t\tset {image} to image from url arg-1\n" +
        	"\t\tsave image {image} at \"plugins/images/%arg-2%.png\""
})
@Since("1.0")

public class EffSaveImage extends AsyncEffect {

	static {
		Skript.registerEffect(EffSaveImage.class, "(save|write) image %~bufferedimage% at %string%");
    }
	
	private Expression<BufferedImage> image;
	private Expression<String> path;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		path = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "save image " + image.toString(e, debug) + " to path " + path.toString(e, debug);
	}

	@Override
	protected void execute(Event e) {
		BufferedImage img = image.getSingle(e);
		String p = path.getSingle(e);
		if (img != null || p != null) {
			// Only PNG is supported for now
			String format = "png";
			try {
				ImageIO.write(img, format, new File(p));
			} catch (IOException ex) {
				Skript.exception(ex);
			}
		}
	}

}
