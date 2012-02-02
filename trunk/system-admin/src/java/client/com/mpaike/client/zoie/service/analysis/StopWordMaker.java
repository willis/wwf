package com.mpaike.client.zoie.service.analysis;

import java.util.Set;

import org.apache.lucene.analysis.StopFilter;


public class StopWordMaker
{

    private static final String CHAR_AND_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    private static Set stopWords = null;


    public static Set retreive()
    {
        if (stopWords == null)
        {

            StringBuffer buffer = new StringBuffer();
            for (char c = '\u0000'; c <= '\u007F'; c++)
            {

                if (CHAR_AND_NUM.indexOf(c) < 0)
                    buffer.append(c);
            }
            for (char c = '\uFF00'; c <= '\uFFEF'; c++)
                buffer.append(c);
            stopWords = StopFilter.makeStopSet(buffer.toString().toLowerCase()
                    .trim().split(""));
        }
        return stopWords;
    }
}
