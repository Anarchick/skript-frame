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
import ch.njol.util.Kleenean;
import fr.anarchick.skriptframe.util.AsyncEffect;

@Name("Save image")
@Description("Save an image as a file")
@Examples({
    "# /!\\ if the file already exist, it will be replaced /!\\ \n\n" +

    "command download: <string>\n" +
        "\ttrigger:\n" +
        "\t\tcopy file path \"plugins/Skript/scrips/MyAwesomeScript.sk\" to file path \"plugins/Skript/scrips/MyAwesomeScriptCopy.sk\"\n" +
        "\t\tbroadcast \"Copied!\"",

    "# If you need to wait the end of the effect before execute a part of your code, you can\n" +
    "# use this effect as a section effect.\n" + 
    "# The code after this effect section will be executed when the effect section has finished to be executed.\n\n" +
    "command copy:\n" +
        "\ttrigger:\n" +
        "\t\tcopy file path \"plugins/Skript/scrips/MyAwesomeScript.sk\" to file path \"plugins/Skript/scrips/MyAwesomeScriptCopy.sk\":\n" +
        "\t\t\tbroadcast \"Copied!\""
})
@Since("1.0")

public class EffSaveImage extends AsyncEffect {

	static {
		registerAsyncEffect(EffSaveImage.class, "(save|write) image %bufferedimage% at %string%");
    }
	
	private Expression<BufferedImage> image;
	private Expression<String> path;
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean initAsync(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<BufferedImage>) exprs[0];
		path = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "save image " + image.toString(e, debug) + " to path " + path.toString(e, debug);
	}

	@Override
	protected void executeAsync(Event e) {
		BufferedImage img = image.getSingle(e);
		String p = path.getSingle(e);
		if (img != null || p != null) {
			// Only PNG is supported now
			String format = "png";
			try {
				ImageIO.write(img, format, new File(p));
			} catch (IOException ex) {;
				Skript.exception(ex);
			}
		}
	}

}
