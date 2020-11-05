package fr.anarchick.skriptframe.elements.types;

import java.awt.image.BufferedImage;

import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class Types {

	static {
		Classes.registerClass(new ClassInfo<>(BufferedImage.class, "bufferedimage")
				.user("buffered ?images?")
                .name("BufferedImage")
                .description("Represents an Image from a file or an URL")
                //.examples("")
                .parser(new Parser<BufferedImage>() {
                	
                	@Override
					@Nullable
                	public BufferedImage parse(String image, ParseContext context) {
                		return null;
                	}
                	
					@Override
					public String getVariableNamePattern() {
						return "Buffered Image W:[0-9] H:[0-9] Hash:[0-9]";
					}

					@Override
					public String toString(BufferedImage img, int flags) {
						return "Buffered Image W:" + img.getWidth() + " H:" + img.getHeight() + " Hash:" + img.hashCode();
					}

					@Override
					public String toVariableNameString(BufferedImage img) {
						return "Buffered Image W:" + img.getWidth() + " H:" + img.getHeight() + " Hash:" + img.hashCode();
					}
					
                })
                );
	}
}
