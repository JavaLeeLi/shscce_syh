package com.visionet.syh_mall.common.security;

import com.visionet.syh_mall.common.string.StringPool;



/**
 * 
 * @author visionet
 *
 */
public interface Digester {

	public static final String ENCODING = StringPool.UTF8;

	public static final String DIGEST_ALGORITHM = "SHA";

	public String digest(String text);

	public String digest(String algorithm, String text);

}
