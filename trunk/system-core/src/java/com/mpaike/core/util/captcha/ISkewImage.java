package com.mpaike.core.util.captcha;

import java.awt.image.BufferedImage;

public interface ISkewImage {

    /**
     * The implementation method should draw the securityChars on the image
     * and skew it for security purpose. The return value is the finished image object
     *
     * @param securityChars
     * @return - BufferedImage finished skewed iamge
     */
    public BufferedImage skewImage(String securityChars);
}
