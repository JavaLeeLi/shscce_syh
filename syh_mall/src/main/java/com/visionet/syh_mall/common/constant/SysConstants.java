package com.visionet.syh_mall.common.constant;

public interface SysConstants {
	
	/*---------------- Login ---------------------*/
	public static final String CLIENT_FLAG = "client_flag";
	public static final String CLIENT_WEB = "web";
	public static final String CLIENT_IOS = "ios";
	public static final String CLIENT_ANDROID = "android";
	
	public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
	
	/*---------------- Common ---------------------*/
	public static final int YES=1;	//是
	public static final int NO=0;	//否
	
	public static final int DELETE_FLAG_YES=1;	//删除
	public static final int DELETE_FLAG_NO=0;	//未删除
	
	public static final int HASH_NEXT = 1;
	public static final int NOT_HASH_NEXT = 0;
	
	public static final String TMP_DIR = "java.io.tmpdir";
	
	public static final String SORT_TYPE_AUTO = "auto";
	public static final String JSP_SEARCH_FILTER = "search_";
	
	public static final String PRIVACY_FILTER = "confidentiality";
	
	
	/*---------------- Console ---------------------*/
	public static final String CONSOLE_FLAG="consoleFlag";
	
	/*---------------- User ---------------------*/
	public static final Long USER_MAIL = 8L;
	
	public static final Integer USER_ACTIVITY_ENABLED = 0;
	public static final Integer USER_ACTIVITY_DISABLED = 1;
	
	public static final Integer IS_SEC_LOCK = 1;
	public static final Integer IS_SEC_UNLOCK = 0;	

	/*---------------- Role ---------------------*/
	public static final String PERMISSIONS = "view,edit";
	public static final String ADMIN_ROLE_TYPE = "A";
	public static final String COMMON_ROLE_TYPE = "C";
	
	public static final Long ADMIN_ROLE_ID = 1L;
	public static final Long SUBADMIN_ROLE_ID = 2L;
	public static final Long USER_ROLE_ID = 3L;
	public static final Long ASSISTANT_ROLE_ID = 4L;
	public static final Long AGENCY_ROLE_ID = 5L;
//	public static final Long HIDDEN_ROLE_ID = 6L;
	public static final Long MEETING_ROLE_ID = 10L;
	public static final Long REGISTER_ROLE_ID = 11L;
	public static final Long HELPDESK_ROLE_ID = 13L;
	public static final Long HDADMIN_ROLE_ID = 14L;
	
	public static final String ADMIN = "1"; //管理员 
	public static final String USER = "0";	//普通用户
	
	/*---------------- Organization ---------------------*/
	public static final Long COMMON_ORG_ID = 0L;
	public static final Long DEFAULT_ORG_ID = 1L;
    public static final Long SLOTH1_ORG_ID = 175L;

	/*---------------- SystemPlugin ---------------------*/
	public static final String SYS_TYPE_SUBSYSTEM = "S";
	public static final String SYS_TYPE_PUBLICMEDIA = "M";
	
	/*-------------------管理员id--------------------*/
	public static final Long ADMIN_ID = 1l;
	
	/*---------------- 短信 ---------------------*/
	public static final Integer SMS_UNSENT = 0;
	public static final Integer SMS_SENT = 1;
	public static final Integer SMS_DONOT_SEND = -1;
	
}
