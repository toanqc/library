package mpp.library.model.dao.impl;

import mpp.library.model.Copy;
import mpp.library.model.dao.CopyDAO;

public class CopyDAOImpl extends AbstractSerializationDAO<Copy>implements CopyDAO {

	@Override
	public void save(Copy copy) {
		writeObject(SerializationFile.COPY.getValue(), copy);

	}

}
