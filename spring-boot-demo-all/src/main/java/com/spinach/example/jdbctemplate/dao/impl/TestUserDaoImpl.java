package com.spinach.example.jdbctemplate.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.spinach.example.jdbctemplate.base.JdbcDaoImpl;
import com.spinach.example.jdbctemplate.base.Page;
import com.spinach.example.jdbctemplate.base.Sql;
import com.spinach.example.jdbctemplate.dao.TestUserDao;
import com.spinach.example.mybatis.entity.TestUser;

@Repository("testUserDaoImpl2")
public class TestUserDaoImpl extends JdbcDaoImpl implements TestUserDao {

	//@Autowired
	//private JdbcTemplate jdbcTemplate;

	@Override
	public int insert(TestUser testUser) {
		String sql = "insert into test_user (user_name, create_time) values (?, ?)";
		return jdbcTemplate.update(sql, testUser.getUserName(), testUser.getCreateTime());
	}

	@Override
	public int deleteById(int id) {
		String sql = "delete from test_user where id=?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int updateById(TestUser testUser) {
		String sql = "update test_user set user_name=?, create_time=? where id=?";
		return jdbcTemplate.update(sql, testUser.getUserName(), testUser.getCreateTime(), testUser.getId());
	}

	@Override
	public List<TestUser> selectAll() {
		String sql = "select * from test_user";
		return queryForObjectList(sql, TestUser.class);
	}
	@Override
	public TestUser selectById(int id) {
		String sql = "select * from test_user where id=?";
		return queryForObject(sql, TestUser.class, id);
	}

	@Override
	public Page<TestUser> queryForPage(int pageCurrent, int pageSize, String name){
		// 确定参数
		/*String sql = "select * from test_user where name=?";
		return queryForPage(sql.toString(), pageCurrent, pageSize, TestUser.class, name);*/
		
		// 若name可能为空，则要进行判定，如下
		/*StringBuffer sql = new StringBuffer("select * from test_user where 1");
		if(!StringUtils.isNullOrEmpty(name)){
			// Sql.checkSql 的作用是防止sql注入
			sql.append(" and name = '").append(Sql.checkSql(name)).append("' ");
		}
		return queryForPage(sql.toString(), pageCurrent, pageSize, TestUser.class);*/
		
		// 若要like查询，如下
		StringBuffer sql = new StringBuffer("select * from test_user where 1");
		if(!StringUtils.isEmpty(name)){
			// Sql.checkSql 的作用是防止sql注入
			sql.append(" and user_name like '%").append(Sql.checkSql(name)).append("%' ");
		}
		return queryForPage(sql.toString(), pageCurrent, pageSize, TestUser.class);
	}	





}
