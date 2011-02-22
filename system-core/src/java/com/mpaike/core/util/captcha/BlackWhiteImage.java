package com.mpaike.core.util.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlackWhiteImage implements ISkewImage {

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
		
      RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g2d.setRenderingHints(hints);

        g2d.setColor(Color.WHITE);
        
		for (int i = 0; i < 30; i++) {
			int x = random.nextInt(MAX_X);
			int y = random.nextInt(MAX_Y);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g2d.drawLine(x, y, x + xl, y + yl);
		}
        
        int startPosX = 25;
        char[] wc = securityChars.toCharArray();
        for (char element : wc) {
            char[] itchar = new char[] { element };
            Font itFont = new Font("Courier", 1, 33);
            g2d.setFont(itFont);

            g2d.drawChars(itchar, 0, itchar.length, startPosX, 33);
            startPosX = startPosX + (int) 28;
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
