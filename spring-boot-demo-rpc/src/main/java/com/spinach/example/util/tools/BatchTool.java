package com.spinach.example.util.tools;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 数据库批量操作工具。
 * </p>
 * @version 1.0
 */
public class BatchTool {
	private static SqlSessionFactory mySqlSessionFactory;
	private final static Logger logger = LoggerFactory.getLogger(BatchTool.class);

	static {
		mySqlSessionFactory = (SqlSessionFactory) SpringContextUtils.getBean("mySqlSessionFactory");
	}

	/**
	 * 数据库批量新增操作
	 * 
	 * @param mySqlSessionFactory 会话工厂：需在使用的类里面注入
	 * @param statement 新增语句
	 * @param list 所有新增对象
	 * @throws Exception
	 */
	public static void batchInsert(String statement, List<?> list) {
		SqlSession batchSqlSession = mySqlSessionFactory.openSession();
		try {
			for (Object obj : list) {
				batchSqlSession.insert(statement, obj);
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			batchSqlSession.rollback(true);
			logger.error(e.toString());
		} finally {
			batchSqlSession.close();
		}
	}

	/**
	 * 数据库批量修改操作
	 * 
	 * @param mySqlSessionFactory 会话工厂：需在使用的类里面注入
	 * @param statement 修改语句
	 * @param list 所有修改对象
	 * @throws Exception
	 */
	public static void batchUpdate(String statement, List<?> list) {
		SqlSession batchSqlSession = mySqlSessionFactory.openSession();
		try {
			for (Object obj : list) {
				batchSqlSession.update(statement, obj);
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			String str = "deadlock";
			boolean a = e.toString().contains(str);
			// 发送死锁异常时,即异常中带有deadlock时,不发邮件提示
			if (!a) {
				batchSqlSession.rollback(true);
			}
			logger.error(e.toString());
		} finally {
			batchSqlSession.close();
		}
	}
}
