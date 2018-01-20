package com.spinach.example.util.tools;

public enum IconEnum {
	ICON_CLOSE("关闭","conf/icon/close.png"),
	ICON_MONITOR("监控","conf/icon/01monitor.png"),
	ICON_EXPLAIN("讲解","conf/icon/02explain.png"),
	ICON_XIE("协调","conf/icon/03xie.png"),
	ICON_AIR("天气","conf/icon/04air.png"),
	ICON_NOTICE("通告","conf/icon/05notice.png"),
	ICON_PLAIN("飞机","conf/icon/06plain.png"),
	ICON_SAFEGUARD("保障","conf/icon/07safeguard.png"),
	ICON_GROUP("机组","conf/icon/08group.png"),
	ICON_OTHER("其他","conf/icon/09other.png");
	  
	 private String desc;
	 private String url;
	  
	 private IconEnum(String desc,String url) {
		 this.desc = desc;
		 this.url =url;
	 }
	  
	public String getDesc() {
		return this.desc;
	 }
	  
	 public String getUrl() {
	 	return this.url;
	 }
	  
}
