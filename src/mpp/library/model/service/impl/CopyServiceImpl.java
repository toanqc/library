package mpp.library.model.service.impl;

import mpp.library.model.Copy;
import mpp.library.model.Publication;
import mpp.library.model.dao.CopyDAO;
import mpp.library.model.dao.db.impl.CopyDAODBImpl;
import mpp.library.model.service.CopyService;

public class CopyServiceImpl implements CopyService {

	private CopyDAO copyDao;

	public CopyServiceImpl() {
		copyDao = new CopyDAODBImpl();
	}

	@Override
	public void saveCopy(Copy copy) {
		copyDao.save(copy);
	}

	@Override
	public void updateCopy(Copy copy) {
		copyDao.update(copy);
	}

	@Override
	public Copy getAvailableCopy(Publication pub) {
		// TODO Auto-generated method stub
		return copyDao.getAvailableCopy(pub);
	}

}
