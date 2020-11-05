package fr.anarchick.skriptframe.map;

import java.awt.Color;

public enum Palette_1_16 {
	NONE(new Color(0, 0, 0, 0), 0), // transparent
	GRASS(new Color(127, 178, 56), 1),
	SAND(new Color(247, 233, 163), 2),
	WOOL(new Color(199, 199, 199), 3),
	FIRE(new Color(255, 0, 0), 4),
	ICE(new Color(160, 160, 255), 5),
	METAL(new Color(167, 167, 167), 6),
	PLANT(new Color(0, 124, 0), 7),
	SNOW(new Color(255, 255, 255), 8),
	CLAY(new Color(164, 168, 184), 9),
	DIRT(new Color(151, 109, 77), 10),
	STONE(new Color(112, 112, 112), 11),
	WATER(new Color(64, 64, 255), 12),
	WOOD(new Color(143, 119, 72), 13),
	QUARTZ(new Color(255, 252, 245), 14),
	COLOR_ORANGE(new Color(216, 127, 51), 15),
	COLOR_MAGENTA(new Color(178, 76, 216), 16),
	COLOR_LIGHT_BLUE(new Color(102, 153, 216), 17),
	COLOR_YELLOW(new Color(229, 229, 51), 18),
	COLOR_LIGHT_GREEN(new Color(127, 204, 25), 19),
	COLOR_PINK(new Color(242, 127, 165), 20),
	COLOR_GRAY(new Color(76, 76, 76), 21),
	COLOR_LIGHT_GRAY(new Color(153, 153, 153), 22),
	COLOR_CYAN(new Color(76, 127, 153), 23),
	COLOR_PURPLE(new Color(127, 63, 178), 24),
	COLOR_BLUE(new Color(51, 76, 178), 25),
	COLOR_BROWN(new Color(102, 76, 51), 26),
	COLOR_GREEN(new Color(02, 127, 51), 27),
	COLOR_RED(new Color(153, 51, 51), 28),
	COLOR_BLACK(new Color(25, 25, 25), 29),
	GOLD(new Color(250, 238, 77), 30),
	DIAMOND(new Color(92, 219, 213), 31),
	LAPIS(new Color(74, 128, 255), 32),
	EMERALD(new Color(0, 217, 58), 33),
	PODZOL(new Color(129, 86, 49), 34),
	NETHER(new Color(112, 2, 0), 35),
	TERRACOTTA_WHITE(new Color(209, 177, 161), 36),
	TERRACOTTA_ORANGE(new Color(159, 82, 36), 37),
	TERRACOTTA_MAGENTA(new Color(149, 87, 108), 38),
	TERRACOTTA_LIGHT_BLUE(new Color(112, 108, 138), 39),
	TERRACOTTA_YELLOW(new Color(186, 133, 36), 40),
	TERRACOTTA_LIGHT_GREEN(new Color(103, 117, 53), 41),
	TERRACOTTA_PINK(new Color(160, 77, 78), 42),
	TERRACOTTA_GRAY(new Color(57, 41, 35), 43),
	TERRACOTTA_LIGHT_GRAY(new Color(135, 107, 98), 44),
	TERRACOTTA_CYAN(new Color(87, 92, 92), 45),
	TERRACOTTA_PURPLE(new Color(122, 73, 88), 46),
	TERRACOTTA_BLUE(new Color(76, 62, 92), 47),
	TERRACOTTA_BROWN(new Color(76, 50, 35), 48),
	TERRACOTTA_GREEN(new Color(76, 82, 42), 49),
	TERRACOTTA_RED(new Color(142, 60, 46), 50),
	TERRACOTTA_BLACK(new Color(37, 22, 16), 51),
	CRIMSON_NYLIUM(new Color(189, 48, 49), 52),
	CRIMSON_STEM(new Color(148, 63, 97), 53),
	CRIMSON_HYPHAE(new Color(92, 25, 29), 54),
	WARPED_NYLIUM(new Color(22, 126, 134), 55),
	WARPED_STEM(new Color(58, 142, 140), 56),
	WARPED_HYPHAE(new Color(86, 44, 62), 57),
	WARPED_WART_BLOCK(new Color(20, 180, 133), 58)
	;

	private final Color color;
	private final int id;

	Palette_1_16(Color rgb, int id) {
		this.color = rgb;
		this.id = id;
	}

	public Color getColor() {
		return this.color;
	}
	
	public int getId() {
		return this.id;
	}
	
}

