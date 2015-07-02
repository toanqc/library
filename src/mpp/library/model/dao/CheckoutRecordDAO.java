/**
 * 
 */
package mpp.library.model.dao;

/**
 * @author bpham4
 *
 */
public interface CheckoutRecordDAO<T>{

	T get(String memberId) ;
	
	boolean update(T record);
}
