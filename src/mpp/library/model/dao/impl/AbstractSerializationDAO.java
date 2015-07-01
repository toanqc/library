package mpp.library.model.dao.impl;

import java.io.EOFException;
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
		boolean isAppend = false;
		for (T tObject : objectList) {
			if (!isAppend) {
				this.writeObject(fileName, tObject, false);
				isAppend = true;
			} else {
				this.writeObject(fileName, tObject, true);
			}
		}
	}

	public void writeObject(String fileName, T object) {
		this.writeObject(fileName, object, true);
	}

	public void writeObject(String fileName, T object, boolean isAppend) {
		ObjectOutputStream out = null;
		FileOutputStream fos = null;

		try {
			if (!isAppend) {
				fos = new FileOutputStream(fileName, false);
				out = new ObjectOutputStream(fos);
			} else {
				fos = new FileOutputStream(fileName, true);
				out = new AppendableObjectOutputStream(fos);
			}
			out.writeObject(object);
			out.flush();
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			if (out != null && fos != null) {
				try {
					out.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Serialized data is saved in /storage/" + fileName);
	}

	@SuppressWarnings("unchecked")
	public List<T> getObjectList(String fileName) {
		List<T> memberList = new ArrayList<T>();
		T tObject = null;
		ObjectInputStream in = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			Object object = in.readObject();
			while (object != null) {
				tObject = (T) object;
				memberList.add(tObject);
				object = in.readObject();
			}
		} catch (EOFException e) {
			// do nothing
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if (in != null && fis != null) {
				try {
					in.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return memberList;
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