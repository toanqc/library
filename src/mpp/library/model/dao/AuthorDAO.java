package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Author;

public interface AuthorDAO extends BaseDAO<Author> {

	List<Author> getAuthorsByPubId(int pubId);

	/**
	 * Return the list of members
	 * 
	 * @return List
	 */
	List<Author> getList();

	/**
	 * Update the author with new information, the true if update successfully
	 * 
	 * @param author
	 *            Author
	 * @return boolean
	 */
	boolean update(Author author);

	/**
	 * Generate the new author id
	 * 
	 * @return the new author id
	 */
	int geneateAuthorId();
}
