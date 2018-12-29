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
public abstract class GeneralServiceImple<T extends BasicBean> implements GeneralService<T>{
	
	@Override
	public int insert(T entry) {
		return verify().insert(entry);
	}

	@Override
	public int delete(Object primaryKey) {
		return verify().delete(primaryKey);
	}

	public int update(T entry) {
		return verify().update(entry);
	}

	@Override
	public T find(Object primaryKey) {
		return  verify().find(primaryKey);
	}
	
	@Override
	public List<T> all() {
		return verify().all();
	}

	@Override
	public int insert(List<T> entrys) {
		return verify().insert(entrys);
	}

	@Override
	public int update(List<T> entrys) {
		return verify().insert(entrys);
	}
	
	@Override
	public int insert(T entry, String... exceptfields) {
		return verify().insert(entry, exceptfields);
	}

	@Override
	public int insert(List<T> entrys, String... exceptfields) {
		return verify().insert(entrys, exceptfields);
	}

	@Override
	public int delete(Consumer<List<Condition>> consumer) {
		return verify().delete(consumer);
	}

	@Override
	public T find(Consumer<List<Condition>> consumer) {
		return verify().find(consumer);
	}

	@Override
	public List<T> all(Consumer<List<Condition>> consumer) {
		return verify().all(consumer);
	}
	
	

	@Override
	public boolean create() throws ReflectiveOperationException {
		return verify().create() ;
	}

	@Override
	public int count() {
		return verify().count();
	}

	private final GeneralDao<T> verify() {
		
		GeneralDao<T> dao = this.getGeneralDao();
		
		if(dao!=null)return dao;
		
		throw new GeneralOprationException(
				"Unable to perform the operation, the abstract getGeneralDao method is an empty implementation",new NullPointerException());
	}
	
	protected abstract GeneralDao<T> getGeneralDao();
}
