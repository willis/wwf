package com.mpaike.core.util.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class StripImage implements ISkewImage {

	private static final int MAX_LETTER_COUNT = Config
			.getPropertyInt(Config.MAX_NUMBER);
	private static final int LETTER_WIDTH = Config
			.getPropertyInt(Config.LTR_WIDTH);
	private static final int IMAGE_HEIGHT = Config
			.getPropertyInt(Config.IMAGE_HEIGHT);
	private static final double SKEW = Config.getPropertyDouble(Config.SKEW);
	private static final int DRAW_LINES = Config
			.getPropertyInt(Config.DRAW_LINES);
	private static final int DRAW_BOXES = Config
			.getPropertyInt(Config.DRAW_BOXES);
	private static final int MAX_X = LETTER_WIDTH * MAX_LETTER_COUNT;
	private static final int MAX_Y = IMAGE_HEIGHT;

	private static List<String> fonts = new ArrayList<String>();
	private Random random = new Random();

	public StripImage() {
		intiFont();
	}

	private void intiFont() {
		GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
		String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames(Locale.ENGLISH);
		for (String s : names) {
			fonts.add(s);
		}
	}

	public BufferedImage skewImage(String securityChars) {
		BufferedImage outImage = new BufferedImage(MAX_X, MAX_Y,
				BufferedImage.TYPE_BYTE_INDEXED);
		Graphics2D g2d = outImage.createGraphics();
		char[] cs = securityChars.toCharArray();
		g2d.setColor(new Color(240, 240, 240));
		g2d.fillRect(0, 0, MAX_X, MAX_Y);


		  g2d.setFont(new Font("Arial Black", Font.PLAIN ,30));
		  g2d.setColor(Color.GREEN);
		  g2d.drawString(securityChars, 33, 25 + (int) (Math.random() * 6));
		  

		  //shear
		shear(g2d, MAX_X , MAX_Y, Color.WHITE); 
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < 30; i++) {
			int x = random.nextInt(MAX_X);
			int y = random.nextInt(MAX_Y);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g2d.drawLine(x, y, x + xl, y + yl);
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, (MAX_X) - 1, MAX_Y - 1);
		g2d.dispose();
		return outImage;
	}

	private void shear(Graphics g, int w1, int h1, Color color) {

		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	public void shearX(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (9.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	public void shearY(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(2) + 10; // 50; 

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (period>>1) * Math.sin((double) i / (double) period
						+ ((double) phase)
						/ (double) frames);
		g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

}
