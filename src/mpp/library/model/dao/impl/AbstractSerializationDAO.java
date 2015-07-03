package mpp.library.model.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, false))) {
			oos.writeObject(objectList);
			oos.flush();
		} catch (IOException i) {
			i.printStackTrace();
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

		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			objectList = (ArrayList<T>) ois.readObject();
			ois.close();
			return objectList;
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} 

		return objectList;
	}
}