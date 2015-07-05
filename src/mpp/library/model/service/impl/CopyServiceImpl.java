package mpp.library.model.service.impl;

import mpp.library.model.Copy;
import mpp.library.model.dao.CopyDAO;
import mpp.library.model.dao.impl.CopyDAOImpl;
import mpp.library.model.service.CopyService;

public class CopyServiceImpl implements CopyService {

	private CopyDAO copyDao;

	public CopyServiceImpl() {
		copyDao = new CopyDAOImpl();
	}

	@Override
	public Copy saveCopy(Copy copy) {

		copy.setId(copyDao.getNextId());
		copyDao.save(copy);

		return copy;
	}

	@Override
	public void updateCopy(Copy copy) {
		copyDao.update(copy);
	}

	@Override
	public Copy getCopy(int id) {
		return copyDao.getCopy(id);
	}

}
