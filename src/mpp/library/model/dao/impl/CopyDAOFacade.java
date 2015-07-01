package mpp.library.model.dao.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import mpp.library.model.Copy;
import mpp.library.model.dao.BaseDAO;

public class CopyDAOFacade extends AbstractSerializationDAO<Copy> implements
		BaseDAO<Copy> {
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";

	@Override
	public void save(Copy copy) {
		// TODO Auto-generated method stub
		ObjectOutputStream out = null;
		String copyNumber = String.valueOf(copy.getCopyNumber());
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, copyNumber);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(copy);
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
	public Copy get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
