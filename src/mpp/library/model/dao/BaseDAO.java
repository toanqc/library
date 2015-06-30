package mpp.library.model.dao;


public interface BaseDAO<T> {

	/**
	 * 
	 * @param member
	 */
	void save(T member);

	/**
	 * 
	 * @param id
	 * @return
	 */
	T get(String id);
}
