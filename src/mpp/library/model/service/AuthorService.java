package mpp.library.model.service;

import java.util.List;

import mpp.library.model.Author;

/**
 * @author Toan Quach
 */
public interface AuthorService {

	/**
	 * Saves {@link Author} into serialization file
	 * 
	 * @param author
	 */
	void saveAuthor(Author author);

	/**
	 * Update {@link Author} based on author id to serialization file
	 * 
	 * @param author
	 * @return
	 */
	boolean updateAuthor(Author author);

	/**
	 * Return the list of {@link Author}
	 * 
	 * @return List
	 */
	List<Author> getList();

	/**
	 * Return the {@link Author} with matching id
	 * 
	 * @param id
	 *            the id in table
	 * @return {@link Author}
	 */
	Author get(int id);

	/**
	 * Generate new author id for new author
	 * 
	 * @return the unique author id
	 */
	int generateAuthorId();
}
