/*package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.CopyDAO;

public class CopyDAOImpl extends AbstractSerializationDAO<Copy>implements CopyDAO {

	@Override
	public void save(Copy copy) {
		writeObject(SerializationFile.COPY.getValue(), copy);

	}

	@Override
	public void update(Copy copy) {
		List<Copy> copies = getCopyList();

		for (int i = 0; i < copies.size(); i++) {

			if (copies.get(i).getCopyNumber() == copy.getCopyNumber()) {
				if (copy.getPublication() instanceof Book && copies.get(i).getPublication() instanceof Book
						&& ((Book) copy.getPublication()).getISBN()
								.equals(((Book) copies.get(i).getPublication()).getISBN())) {

					copies.set(i, copy);

				} else if (copy.getPublication() instanceof Periodical
						&& copies.get(i).getPublication() instanceof Periodical
						&& ((Periodical) copy.getPublication()).getTitle()
								.equals(((Periodical) copies.get(i).getPublication()).getTitle())
						&& ((Periodical) copy.getPublication()).getIssueNumber()
								.equals(((Periodical) copies.get(i).getPublication()).getIssueNumber())) {

					copies.set(i, copy);
				}

			}
		}
		writeObjectList(SerializationFile.COPY.getValue(), copies);

	}

	@Override
	public List<Copy> getCopyList() {
		return getObjectList(SerializationFile.COPY.getValue());

	}

	@Override
	public Copy get(String id) {
		throw new UnsupportedOperationException("Method get of Copy not support at this moment");
	}

}
*/