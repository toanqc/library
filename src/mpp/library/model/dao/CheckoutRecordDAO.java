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

	CheckoutRecord get(String memberId);

	boolean update(CheckoutRecord record);
}
