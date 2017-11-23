package com.roncoo.example.util.intercepetor.entity;

public abstract class LogHolder {
	private static final ThreadLocal<LogModel> LOG_HOLDER = new ThreadLocal<>();
	
	public static LogModel getLog(){
		return LOG_HOLDER.get();
	}
	public static void setLog(LogModel log){
		LOG_HOLDER.set(log);
	}
	public static void clear(){
		LOG_HOLDER.set(null);
	}
}
