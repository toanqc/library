package mpp.library.model.service;

import java.util.List;

/**
 * @author Neal
 *
 */
public interface OverdueCalculator<T> {

	/**
	 * Calculate and get overdue records
	 * 
	 * @return {@link List} of <T>
	 */
	List<T> getOverdueRecords();

}
