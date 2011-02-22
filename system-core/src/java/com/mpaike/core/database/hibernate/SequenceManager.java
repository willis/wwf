package com.mpaike.core.database.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class SequenceManager {

	/**
	 * 装载ID
	 */
	private static final String LOAD_ID = "SELECT ID FROM SEQUENCEID WHERE IDTYPE=?";

	/**
	 * 更新ID
	 */
	private static final String UPDATE_ID = "UPDATE SEQUENCEID SET ID=? WHERE IDTYPE=? AND ID=?";

	/**
	 * 多例模式ID生成
	 */
	private static SequenceManager managers[];

	/**
	 * ID类型
	 */
	private int type;

	/**
	 * 当前ID
	 */
	private long currentID;

	/**
	 * 最大ID
	 */
	private long maxID;

	/**
	 * cache一次取得的ID
	 */
	private int blockSize;

	@Autowired
	private DataSource dataSource;

	/**
	 * 取得多例
	 */
	static {
		managers = new SequenceManager[9];
		//           类型100的id生成器()
		managers[0] = new SequenceManager(100, 100);
		//           类型200的id生成器(ID)
		managers[1] = new SequenceManager(200, 10);
		//           类型300的id生成器
		managers[2] = new SequenceManager(300, 50);
		//           类型400的id生成器
		managers[3] = new SequenceManager(400, 50);
		//           类型500的id生成器
		managers[4] = new SequenceManager(500, 50);
		//           类型600的id生成器
		managers[5] = new SequenceManager(600, 50);
		//           类型700的id生成器
		managers[6] = new SequenceManager(700, 50);
		//           类型800的id生成器
		managers[7] = new SequenceManager(800, 50);
		//           类型900的id生成器
		managers[8] = new SequenceManager(900, 50);
	}

	/**
	 * 构造函数
	 *
	 * @param type
	 *            int
	 * @param blockSize
	 *            int
	 */
	private SequenceManager(int type, int blockSize) {
		this.type = type;
		this.blockSize = blockSize;
		currentID = 0L;
		maxID = 0L;
	}

	public static long nextID(int type) {
		switch (type) {
		case 100:
			return managers[0].nextUniqueID();
		case 200:
			return managers[1].nextUniqueID();
		case 300:
			return managers[2].nextUniqueID();
		case 400:
			return managers[3].nextUniqueID();
		case 500:
			return managers[4].nextUniqueID();
		case 600:
			return managers[5].nextUniqueID();
		case 700:
			return managers[6].nextUniqueID();
		case 800:
			return managers[7].nextUniqueID();
		case 900:
			return managers[8].nextUniqueID();
		}
		throw new IllegalArgumentException("Invalid type");
	}

	/**
	 * 同步保证同时只有一个线程申请ID
	 * @return long
	 */
	public synchronized long nextUniqueID() {
		if (currentID >= maxID)
			getNextBlock(5);
		long id = currentID;
		currentID++;
		return id;
	}

	/**
	 * ID申请逻辑方法
	 * @param count int
	 */
	private void getNextBlock(int count) {
		if (count == 0) {
			System.out
					.println("Failed at last attempt to obtain an ID, aborting...");
			return;
		}
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		//boolean abortTransaction = false;
		boolean success = false;
		try {
			conn = dataSource.getConnection();
			//conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			pstmt2 = conn.prepareStatement(LOAD_ID);
			pstmt2.setInt(1, type);
			rs = pstmt2.executeQuery();
			if (!rs.next())
				throw new SQLException(
						"Loading the current ID failed. The ID table may not be correctly populated.");
			long currentID = rs.getLong(1);
			pstmt2.close();
			long newID = currentID + (long) blockSize;
			pstmt2 = conn.prepareStatement(UPDATE_ID);
			pstmt2.setLong(1, newID);
			pstmt2.setInt(2, type);
			pstmt2.setLong(3, currentID);
			success = pstmt2.executeUpdate() == 1;
			//commit
			conn.commit();
			
			if (success) {
				this.currentID = currentID;
				maxID = newID;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//abortTransaction = true;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt1 != null)
					pstmt1.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (pstmt2 != null)
					pstmt2.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (!success) {
			System.out
					.println("WARNING: failed to obtain next ID block due to thread contention. Trying again...");
			try {
				Thread.currentThread();
				Thread.sleep(75L);
			} catch (InterruptedException interruptedexception) {
			}
			getNextBlock(count - 1);
		}
	}

}