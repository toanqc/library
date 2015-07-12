package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.Author;
import mpp.library.model.dao.AuthorDAO;
import mpp.library.model.dao.db.connection.DataAccessFactory;
import mpp.library.model.service.AuthorService;

/**
 * 
 * @author Toan Quach
 *
 */
public class AuthorServiceImpl implements AuthorService {

	private AuthorDAO authorDAO;

	public AuthorServiceImpl() {
		authorDAO = (AuthorDAO) DataAccessFactory.getDAOImpl(AuthorDAO.class);
	}

	@Override
	public void saveAuthor(Author author) {
		authorDAO.save(author);
	}

	@Override
	public boolean updateAuthor(Author author) {
		return authorDAO.update(author);

	}

	@Override
	public List<Author> getList() {
		return authorDAO.getList();
	}

	@Override
	public Author get(int id) {
		return authorDAO.get(id);
	}

	@Override
	public int generateAuthorId() {
		return authorDAO.geneateAuthorId();
	}
}
