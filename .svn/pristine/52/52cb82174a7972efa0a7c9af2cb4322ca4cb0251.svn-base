package online.sanen.cdm.springSupport;

import online.sanen.cdm.basic.BasicBean;

/**
 * 
 * <pre>
 *
 * @author online.sanen
 * Date:2017/12/24
 * Time:18:45
 * </pre>
 */
public class GenaralAnalyse {
	
	public static Object processPrimaryKey(Object object) {
		
		
		Object primaryKey = object;
		
		if(object instanceof BasicBean) {
			primaryKey = ((BasicBean)object).primarykey();
		}
		return primaryKey;
	}

}
