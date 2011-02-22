package com.mpaike.core.util.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SimpleImage implements ISkewImage {

    private static final int MAX_LETTER_COUNT = Config.getPropertyInt(Config.MAX_NUMBER);
    private static final int LETTER_WIDTH = Config.getPropertyInt(Config.LTR_WIDTH);
    private static final int IMAGE_HEIGHT = Config.getPropertyInt(Config.IMAGE_HEIGHT);
    private static final double SKEW = Config.getPropertyDouble(Config.SKEW);
    private static final int DRAW_LINES = Config.getPropertyInt(Config.DRAW_LINES);
    private static final int DRAW_BOXES = Config.getPropertyInt(Config.DRAW_BOXES);
    private static final int MAX_X = LETTER_WIDTH * MAX_LETTER_COUNT;
    private static final int MAX_Y = IMAGE_HEIGHT;

    private static final Color [] RANDOM_BG_COLORS = {
        Color.RED, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK,
        Color.YELLOW};

    private static final Color [] RANDOM_FG_COLORS = {
        Color.BLACK, Color.BLUE, Color.DARK_GRAY};

    public BufferedImage skewImage(String securityChars) {
    	
    	Random random = new Random();
        BufferedImage outImage = new BufferedImage(MAX_X, MAX_Y,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outImage.createGraphics();
		
        //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(java.awt.Color.WHITE);
        g2d.fillRect(0, 0, MAX_X, MAX_Y);

        Font font = new Font("Arial Black", 1, 32);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, (MAX_X) - 1, MAX_Y - 1);

		for (int i = 0; i < 30; i++) {
			int x = random.nextInt(MAX_X);
			int y = random.nextInt(MAX_Y);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g2d.drawLine(x, y, x + xl, y + yl);
		}
		
		char c[] = securityChars.toCharArray();
		for (int i = 0; i < c.length; i++) {
			g2d.setColor(new Color(20 + (int) (Math.random() * 110D),
					20 + (int) (Math.random() * 110D), 20 + (int) (Math
							.random() * 110D)));
			g2d.drawString(String.valueOf(c[i]), (i * LETTER_WIDTH) + 3, 28 + (int) (Math.random() * 6));
		}

		g2d.dispose();

        return outImage;
    }

    private void paindBoxes(Graphics2D g2d) {
        setRandomBGColor(g2d);
        g2d.fillRect(getRandomX(), getRandomY(),
                getRandomX(), getRandomY());
    }

    private int getRandomX() {
        return (int) (Math.random() * MAX_X);
    }

    private int getRandomY() {
        return (int) (Math.random() * MAX_Y);
    }

    private void setRandomFont(Graphics2D g2d) {
        Font font = new Font("dialog", 1, 33);
        g2d.setFont(font);
    }

    private void setRandomFGColor(Graphics2D g2d) {
        int colorId = (int) (Math.random() * RANDOM_FG_COLORS.length);
        g2d.setColor(RANDOM_FG_COLORS[colorId]);
    }

    private void setRandomBGColor(Graphics2D g2d) {
        int colorId = (int) (Math.random() * RANDOM_BG_COLORS.length);
        g2d.setColor(RANDOM_BG_COLORS[colorId]);
    }
}
