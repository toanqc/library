package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Author;

public interface AuthorDAO extends BaseDAO<Author> {

	List<Author> getAuthorsByPubId(int pubId);
	
}
