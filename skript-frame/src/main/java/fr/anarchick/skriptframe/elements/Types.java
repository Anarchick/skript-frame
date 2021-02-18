package fr.anarchick.skriptframe.elements;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import fr.anarchick.skriptframe.util.ImageUtils;

public class Types {

	static {
		if (Classes.getClassInfo("image") == null) {
			Classes.registerClass(new ClassInfo<>(BufferedImage.class, "image")
					.user("images?")
	                .name("Image")
	                .since("1.0")
	                .description("Represents an Image from a file or an URL")
	                //.examples("")
	                .parser(new Parser<BufferedImage>() {
	                	
	                	@Override
						@Nullable
	                	public BufferedImage parse(final String image, final ParseContext context) {
	                		return null;
	                	}
	                	
	                	@Override
	                    public boolean canParse(ParseContext context) {
	                        return false;
	                    }
	                	
	                	@Override
						public String toString(BufferedImage img, int flags) {
							return img.toString();
						}
	
						@Override
						public String toVariableNameString(BufferedImage img) {
							return img.toString();
						}
						
						@Override
						public String getVariableNamePattern() {
							return ".+";
						}
						
	                })
	                .serializer(new Serializer<BufferedImage>() {
	                    @Override
	                    public Fields serialize(BufferedImage image) throws NotSerializableException {
	                        Fields f = new Fields();
	                        try {
	                            f.putObject("image", ImageUtils.imageToByteArray(image));
	                            return f;
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                            return null;
	                        }
	                    }
	
	                    @Override
	                    public void deserialize(BufferedImage image, Fields fieldContexts) throws StreamCorruptedException, NotSerializableException {
	                        assert false;
	                    }
	
	                    @Override
	                    protected BufferedImage deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
	                        byte[] byteArray = (byte[]) fields.getPrimitive("image");
	                        try {
	                            return ImageUtils.byteArrayToImage(byteArray);
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                            return null;
	                        }
	                    }
	
	                    @Override
	                    public boolean mustSyncDeserialization() {
	                        return false;
	                    }
	
	                    @Override
	                    public boolean canBeInstantiated(Class<? extends BufferedImage> aClass) {
	                        return false;
	                    }
	
						@Override
						protected boolean canBeInstantiated() {
							// TODO Auto-generated method stub
							return false;
						}
	                })
	        );
		}
	}
}
