package vrbaidu.top.util;


public enum AreaEnum {
	/**
	 * LG:龙岗区
	 */
	LG("龙岗区",1L),
	/**
	 * BA:宝安区
	 */
	BA("宝安区",2L),
	/**
	 * HQN:华强北
	 */
	HQN("华强北",3L),
	/**
	 * QH:前海分行
	 */
	QH("前海分行",4L),
	/**
	 * NS:南山区
	 */
	NS("南山区",5L),
	/**
	 * FT:福田区
	 */
	FT("福田区",6L),
	/**
	 * YEB:营业部
	 */
	YEB("营业部",7L),
	/**
	 * LH:罗湖区
	 */
	LH("罗湖区",8L),
	/**
	 * ZJ:总计
	 */
	ZJ("总计",9L);
	
	private String displayName;
	private Long value;
	private AreaEnum(String displayName, Long value) {
		this.displayName = displayName;
		this.value=value;
	}


}
