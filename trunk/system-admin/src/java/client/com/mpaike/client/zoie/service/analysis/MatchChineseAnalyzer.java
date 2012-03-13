package com.mpaike.client.zoie.service.analysis;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.util.Version;


public class MatchChineseAnalyzer extends Analyzer
{

    public final static String[] STOP_WORDS =
    { "a", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
            "into", "is", "it", "no", "not", "of", "on", "or", "s", "such",
            "t", "that", "the", "their", "then", "there", "these", "they",
            "this", "to", "was", "will", "with", "", "www" };


    private Set stopTable;


    public MatchChineseAnalyzer()
    {
        this.stopTable = StopWordMaker.retreive();
    }


    public MatchChineseAnalyzer(String[] stopWords)
    {
        this.stopTable = StopFilter.makeStopSet(stopWords);
    }




    @Override
    public TokenStream tokenStream(String fieldName, Reader reader)
    {
        return new MatchChineseTokenizer(reader);
    }

}
