package mpp.library.model.dao.impl;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract serialization DAO let us to read / write an object to the given file
 * name
 * 
 * @author Toan Quach
 *
 * @param <T>
 */
public abstract class AbstractSerializationDAO<T> {

	public void writeObjectList(String fileName, List<T> objectList) {
		createNewFile(fileName);
		store(fileName, objectList);
	}

	public void writeObject(String fileName, T object) {
		List<T> objectList = getObjectList(fileName);
		if (objectList == null) {
			objectList = new ArrayList<T>();
		}
		objectList.add(object);

		store(fileName, objectList);
		System.out.println("Serialized data is saved in /storage/" + fileName);
	}

	private void store(String fileName, List<T> objectList) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName, false));
			oos.writeObject(objectList);
			oos.flush();
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createNewFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getObjectList(String fileName) {
		createNewFile(fileName);
		ArrayList<T> objectList = new ArrayList<T>();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			objectList = (ArrayList<T>) ois.readObject();
			ois.close();

			return objectList;
		} catch (EOFException e) {
			// do nothing
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return objectList;
	}

	/**
	 * Appendable object out stream class allows us to append a new object to
	 * the current serialization file
	 * 
	 * @author Toan Quach
	 *
	 */
	class AppendableObjectOutputStream extends ObjectOutputStream {

		public AppendableObjectOutputStream(OutputStream out)
				throws IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			// do not write a header
		}
	}
}