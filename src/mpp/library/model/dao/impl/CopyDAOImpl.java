package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Copy;
import mpp.library.model.dao.CopyDAO;

public class CopyDAOImpl extends AbstractSerializationDAO<Copy>implements CopyDAO {

	@Override
	public void save(Copy copy) {
		writeObject(SerializationFile.COPY.getValue(), copy);

	}

	@Override
	public int getCount() {
		return getObjectList(SerializationFile.COPY.getValue()).size();
	}

	@Override
	public int getNextId() {
		return getObjectList(SerializationFile.COPY.getValue()).size() + 1;
	}

	@Override
	public void update(Copy copy) {
		List<Copy> copies = getCopyList();
		for (int i = 0; i < copies.size(); i++) {
			if (copies.get(i).getId() == copy.getId()) {
				copies.set(i, copy);
			}
		}
		writeObjectList(SerializationFile.COPY.getValue(), copies);

	}

	@Override
	public List<Copy> getCopyList() {
		return getObjectList(SerializationFile.COPY.getValue());

	}

	@Override
	public Copy getCopy(int id) {
		List<Copy> copies = getCopyList();
		
		Copy copy = null;
		for (Copy cpy : copies) {
			if (id == cpy.getId()) {
				copy = cpy;
				break;
			}
		}
		return copy;
	}

}
