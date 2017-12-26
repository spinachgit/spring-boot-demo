package com.spinach.example.mybatis.base;

import java.util.List;

public interface MybatisBaseDao<T> {

	<P> int insert(P param);

	<P> int update(P param);

	<P> int delete(P param);

	<T, P> T selectOne(P param);

	<T, P> List<T> selectList(P param);

}
