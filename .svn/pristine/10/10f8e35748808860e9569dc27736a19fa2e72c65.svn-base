package online.sanen.cdm.springSupport;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mhdt.analyse.Validate;
import com.mhdt.annotation.Table;
import com.mhdt.toolkit.Reflect;

import online.sanen.cdm.Bootstrap;
import online.sanen.cdm.QueryMap;
import online.sanen.cdm.QuerySql;
import online.sanen.cdm.QueryTable;
import online.sanen.cdm.basic.BasicBean;
import online.sanen.cdm.condition.Condition;

/**
 * 
 * @author LazyToShow <br>
 *         Date: 2017/10/26 <br>
 *         Time: 11:59
 */
public abstract class GeneralDaoImpl<T extends BasicBean>
		implements GeneralDao<T>, ApplicationListener<ContextRefreshedEvent> {

	private ApplicationContext applicationContext = null;

	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event) {
		this.applicationContext = event.getApplicationContext();
		this.bootstrap = initBootstrap();
		System.err.println("Init Cdm-for-Spring :" + getDefaultTableName());
		afterInitialization();
	}

	protected void afterInitialization() {
		
	}
	
	private String primaryKey;

	@SuppressWarnings("unused")
	private String getPrimaryKey() {
		if (primaryKey == null) {
			try {
				BasicBean basicBean = (BasicBean) Reflect.newInstance(getEntry().getName());
				primaryKey = basicBean.primarykey() == null ? "id" : basicBean.primarykey();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return primaryKey;
	}

	@Override
	public int insert(List<T> entrys) {
		return getBootStrap().query(entrys).insert();
	}

	@Override
	public final int delete(Object object) {

		return getBootStrap().query(getEntry(), GenaralAnalyse.processPrimaryKey(object)).delete();
	}
	
	@Override
	public int delete(Consumer<List<Condition>> consumer) {
		return getBootStrap().query(getDefaultTableName()).addCondition(consumer).delete();
	}

	@Override
	public final int update(T entry) {
		return getBootStrap().query(entry).update();
	}

	@Override
	public final int update(List<T> entrys) {
		return getBootStrap().query(entrys).update();
	}

	@Override
	public final T find(Object primarykey) {
		return (T) getBootStrap().query(getEntry(), GenaralAnalyse.processPrimaryKey(primarykey))
				.find();
	}
	
	@Override
	public T find(Consumer<List<Condition>> conds) {
		return queryTBForEntry().addCondition(conds).unique();
	}
	
	@Override
	public final int insert(T entry) {
		if(entry == null)
			throw new NullPointerException("Entry is null");
		
		return getBootStrap().query(entry).insert();
	}
	
	@Override
	public int insert(T entry, String... exceptfields) {
		if(entry == null)
			throw new NullPointerException("Entry is null");
		
		return getBootStrap().query(entry).setExceptFields(exceptfields).insert();
	}

	@Override
	public int insert(List<T> entrys, String... exceptfields) {
		if(entrys == null || entrys.isEmpty())
			throw new NullPointerException("Entrys is null or is empty");
		
		return getBootStrap().query(entrys).setExceptFields(exceptfields).insert();
	}

	@Override
	public final List<T> all() {
		return queryTBForEntry().list();
	}
	
	@Override
	public List<T> all(Consumer<List<Condition>> consumer) {
		return queryTBForEntry().addCondition(consumer).list();
	}


	@Override
	public final int count() {
		return getBootStrap().createSQL("select count(1) from " + getDefaultTableName())
				.unique();
	}
	
	private String defaultTableName;

	public final String getDefaultTableName() {

		if (defaultTableName == null) {

			if (Reflect.hasAnnotation(getEntry(), Table.class)) {
				defaultTableName = Reflect.getTableNameValue(getEntry()).toUpperCase();
			} else {
				defaultTableName = getEntry().getSimpleName().toUpperCase();
			}
		}
		
		return defaultTableName;
	}

	protected final QueryTable queryTB() {

		return getBootStrap().query(getDefaultTableName());
	}

	protected final QueryTable queryTBForEntry() {
		return getBootStrap().query(getDefaultTableName()).addEntry(getEntry());
	}

	protected final QuerySql createSQL(String sql, Object... paramers) {
		return getBootStrap().createSQL(sql, paramers);
	}

	protected final QuerySql createSQLForEntry(String sql, Object... paramers) {
		return getBootStrap().createSQL(sql, paramers).addEntry(getEntry());
	}
	
	protected final QueryMap queryMap(Map<String,Object> map) {
		return getBootStrap().query(getDefaultTableName(), map);
	}

	private Bootstrap bootstrap;

	protected Bootstrap initBootstrap() {

		Bootstrap bootstrap = null;

		if (this.applicationContext == null)
			throw new NullPointerException("ApplicationListener Uninitialized.");

		if (Validate.isNullOrEmpty(applyBootStrapBeanId())) {
			try {
				bootstrap = this.applicationContext.getBean(Bootstrap.class);
			} catch (NoUniqueBeanDefinitionException e) {
				bootstrap = this.applicationContext.getBeansOfType(Bootstrap.class).values().iterator().next();
			}

		} else {
			bootstrap = this.applicationContext.getBean(applyBootStrapBeanId(), Bootstrap.class);
		}

		return bootstrap;
	}

	/**
	 * 
	 * Assin the BootStrap-Id pass override this method.
	 * 
	 * @return BootStrap-Id
	 */
	protected String applyBootStrapBeanId() {

		// Entry annotations
		String id = Reflect.getBootStrapId(getEntry());

		// Class annotations
		if (Validate.isNullOrEmpty(id))
			id = Reflect.getBootStrapId(getClass());

		if (!Validate.isNullOrEmpty(id))
			return id;
		else
			return null;
	}

	private Class<T> cls;

	@SuppressWarnings("unchecked")
	public final Class<T> getEntry() {

		if (this.cls == null) {

			Class<?> targetClass = null;

			if (this.getClass().getName().contains("$")) {

				try {
					targetClass = Class.forName(getClass().getName().substring(0, getClass().getName().indexOf("$")),
							false, getClass().getClassLoader());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			} else {
				targetClass = this.getClass();
			}

			this.cls = (Class<T>) Reflect.getGeneric(targetClass);
		}

		return this.cls;
	}
	
	

	@Override
	public boolean create() throws ReflectiveOperationException {
		
		if(!getBootStrap().query(getDefaultTableName()).isExsites()) {
			try {
				getBootStrap().query((BasicBean)Reflect.newInstance(getEntry().getName())).create();
				return true;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw e;
			}
		}
		return false;
	}

	@Override
	public Bootstrap getBootStrap() {
		return this.bootstrap;
	}

}
