package online.sanen.cdm.springSupport;

import java.util.List;
import java.util.function.Consumer;

import online.sanen.cdm.basic.BasicBean;
import online.sanen.cdm.condition.Condition;

/**
 * 
 * @author LazyToShow
 * Date: 2017/10/26
 * Time: 11:59
 */
public interface GeneralService<T extends BasicBean> {
	
	public int insert(T entry);
	
	public int insert(T entry,String... exceptfields);
	
	public int insert(List<T> entrys);
	
	public int insert(List<T> entrys,String... exceptfields);
	
	public int delete(Object primaryKey);
	
	public int delete(Consumer<List<Condition>> consumer);
	
	public int update(T entry);
	
	public int update(List<T> entrys);
	
	public T find(Object primaryKey);
	
	public T find(Consumer<List<Condition>> consumer);
	
	public List<T> all();
	
	public List<T> all(Consumer<List<Condition>> consumer);
	
	public int count();
	
	public boolean create() throws ReflectiveOperationException;
	
}
