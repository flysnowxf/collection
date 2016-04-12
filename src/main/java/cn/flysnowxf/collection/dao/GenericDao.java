package cn.flysnowxf.collection.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface GenericDao<E> {

	public void add(E paramE);

	public void update(E paramE);

	public void delete(Serializable paramSerializable);
	
	public E get(Serializable paramSerializable);

	public List<E> getAll();

	public List<E> query(Map<String, Object> param);
	
	public int count();
	
	public int queryCount(Map<String, Object> param);
	
}
