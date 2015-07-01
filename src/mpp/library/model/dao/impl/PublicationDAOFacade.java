package mpp.library.model.dao.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import mpp.library.model.Book;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.dao.BaseDAO;

public class PublicationDAOFacade extends AbstractSerializationDAO<Publication>
		implements BaseDAO<Publication> {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";
	
	@Override
	public void save(Publication publication) {
		// TODO Auto-generated method stub
		ObjectOutputStream out = null;
		String name = "";
		if (publication instanceof Book) {
			name = ((Book) publication).getISBN();
		}
		else if (publication instanceof Periodical){
			name = ((Periodical) publication).getTitle() + "_" + ((Periodical) publication).getIssueNumber();
		}
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(publication);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}

	@Override
	public Publication get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
