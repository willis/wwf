package com.mpaike.client.zoie.service.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.index.Payload;

import com.mpaike.util.ByteUtil;

public class MatchChineseTokenizer extends Tokenizer
{

    private List<String> list = null;
    private int listIndex = 0;
    private long dateTime=0;
    private int type = 0;

    public MatchChineseTokenizer(Reader reader)
    {
        super(reader);
        try
        {
            this.process();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public Token next() throws IOException
    {
    	Token token = null;
        if (this.listIndex >= this.list.size()){

        }else{
            String word = this.list.get(listIndex++);
            token = new Token();
            token.setTermBuffer(word);
            if(type==0){
            	token.setPayload(new Payload(ByteUtil.long2bytes(dateTime)));
            }
        }
        //System.out.println("next Token:"+listIndex+" ="+token);
        return token;
    }
    
    


    private void process() throws IOException
    {

        BufferedReader in = new BufferedReader(this.input);

        String line = null;
        StringBuffer buffer = new StringBuffer();
        while ((line = in.readLine()) != null)
        {
            buffer.append(line);
            //buffer.append("\n");
        }
        this.close();
        String date_gene = buffer.toString();
        int len=date_gene.indexOf("###");
        if(len!=-1){
        	dateTime = Long.valueOf(date_gene.substring(0, len));
        	if(dateTime==0){
        		type = 1;
        	}
        	len+=3;
        }else{
        	len = 0;
        }
        //System.out.println(date_gene.substring(len));
        String[] strs = date_gene.substring(len).toLowerCase().split(";");
        this.list = new ArrayList<String>(strs.length);
        String[] value;
        int rate;
        for(String s : strs){
        	value = s.split("=");
        	if(value.length==2){
        		rate = Math.round(Float.valueOf(value[1])*10);
        		for(int i=0;i<rate;i++){
        			this.list.add(value[0]);
        		}
        	}else{
        		this.list.add(value[0]);
        	}
        }
    }


	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
