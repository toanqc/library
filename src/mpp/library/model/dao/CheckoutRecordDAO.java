/**
 * 
 */
package mpp.library.model.dao;

import mpp.library.model.CheckoutRecord;

/**
 * @author bpham4
 *
 */
public interface CheckoutRecordDAO extends BaseDAO<CheckoutRecord> {

	boolean update(CheckoutRecord record);
}
