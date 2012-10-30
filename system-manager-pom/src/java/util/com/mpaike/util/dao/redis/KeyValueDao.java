package com.mpaike.util.dao.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class KeyValueDao {
	
	protected ShardedJedisPool shardedJedisPool;
	protected static final Charset charset = Charset.forName("UTF-8");

	public KeyValueDao(ShardedJedisPool shardedJedisPool) {
            this.shardedJedisPool = shardedJedisPool;
	}

	public void sadd(String key,Object obj){
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.sadd(key.getBytes(charset), objectToBytes(obj));
		shardedJedisPool.returnResource(jedis);
	}
	
	public Object spop(String key){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = bytesToObject(jedis.spop(key.getBytes(charset)));
		shardedJedisPool.returnResource(jedis);
		return obj;
	}
	

	public void put(String key,String field,Object value){
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.hset(key.getBytes(), field.getBytes(charset), objectToBytes(value));
		shardedJedisPool.returnResource(jedis);
	}
	
	public Object get(String key,String field){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = null;
		obj = bytesToObject(jedis.hget(key.getBytes(charset), field.getBytes(charset)));
		shardedJedisPool.returnResource(jedis);
		return obj;
	}
	
	public List<Object> getForKey(String key,String... fields){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = null;
		List<byte[]> fieldsByte = new ArrayList<byte[]>();
		for(String field : fields){
			fieldsByte.add(field.getBytes(charset));
		}
		List<byte[]> lists = jedis.hmget(key.getBytes(charset), (byte[][])fieldsByte.toArray());
		shardedJedisPool.returnResource(jedis);
		List<Object> objList = new ArrayList<Object>();
		for(byte[] bytes : lists){
			objList.add(bytesToObject(bytes));
		}
		return objList;
	}
	
	public void remove(String key,final String field){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long num = jedis.hdel(key.getBytes(charset), field.getBytes(charset));
		shardedJedisPool.returnResource(jedis);
	}
	
	
	public void remove(String key){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long num = jedis.expire(key.getBytes(charset),0);
		shardedJedisPool.returnResource(jedis);
	}
	
	public void set(String key,Object obj){
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.set(key.getBytes(charset), objectToBytes(obj));
		//System.out.println(JSONSerializer.toJSON(obj).toString());
		shardedJedisPool.returnResource(jedis);
	}
	
	public void set(String key,Object obj,int exp){
		ShardedJedis jedis = shardedJedisPool.getResource();
		jedis.setex(key.getBytes(charset), exp, objectToBytes(obj));
		//System.out.println(JSONSerializer.toJSON(obj).toString());
		shardedJedisPool.returnResource(jedis);
	}
	
	public Object get(String key){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = bytesToObject(jedis.get(key.getBytes(charset)));
		shardedJedisPool.returnResource(jedis);
		return obj;
	}
	
	public Object getSet(String key,Object value){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = bytesToObject(jedis.getSet(key.getBytes(charset),objectToBytes(value)));
		shardedJedisPool.returnResource(jedis);
		return obj;
	}
	
	public Long incr(final String key){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long num = jedis.incr(key);
		shardedJedisPool.returnResource(jedis);
		return num;
	}
	
	/*============================= protected ============================*/
	protected static byte[] objectToBytes(Object s){
		if(s==null){
			return null;
		}
        // 序列化后数据流给ByteArrayOutputStream 来保存。
        // ByteArrayOutputStream 可转成字符串或字节数组
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] b = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(s);
			b = baos.toByteArray();
			oos.close();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return b;
	}
	
	protected static Object bytesToObject(byte[] b){
		if(b==null){
			return null;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
        Object obj = null;
		try {
			bais = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
			ois.close();
			bais.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return obj;
	}
	
	
	
	
}
