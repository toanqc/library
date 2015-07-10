package mpp.library.model.dao;

public interface BaseDAO<T> {

	/**
	 * Save method in the information of an object t
	 * @param t return the saved object
	 */
	T save(T t);

	/**
	 * Get the t information object with id
	 * @param id the id to search
	 * @return return the found object
	 */
	T get(int id);
}
