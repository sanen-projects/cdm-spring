package online.sanen.cdm.springSupport;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.mhdt.analyse.Validate;

/**
 * 
 * @author LazyToShow
 * Date: 2018/06/12
 * Time: 09:17
 */
@Aspect
public class MappingModule {

	@Around(value = "@within(org.springframework.stereotype.Controller)")
	public Object abc(ProceedingJoinPoint pjp) throws Throwable {
		
		Object obj = pjp.proceed(pjp.getArgs());
		
		if(Validate.isNullOrEmpty(obj) || !(obj instanceof String))
			return obj;
		
		Class<?> cls = pjp.getTarget().getClass();
    	
    	String temp = cls.getName().split("module")[1];
    	temp = temp.substring(1, temp.lastIndexOf(".")+1).replace(".", "/");
		
		return temp + obj.toString();
	}


}
