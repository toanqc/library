package mpp.library.model.dao.db.connection;

import java.util.HashMap;
import java.util.Properties;

import mpp.library.model.dao.AddressDAO;
import mpp.library.model.dao.AuthorDAO;
import mpp.library.model.dao.BaseDAO;
import mpp.library.model.dao.CheckoutDAO;
import mpp.library.model.dao.CheckoutRecordDAO;
import mpp.library.model.dao.CheckoutRecordEntryDAO;
import mpp.library.model.dao.CopyDAO;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.impl.AddressDAODBImpl;
import mpp.library.model.dao.db.impl.AuthorDAODBImpl;
import mpp.library.model.dao.db.impl.CheckoutDAODBFacade;
import mpp.library.model.dao.db.impl.CheckoutRecordEntryDAODBFacade;
import mpp.library.model.dao.db.impl.CopyDAODBImpl;
import mpp.library.model.dao.db.impl.MemberDAODBImpl;
import mpp.library.model.dao.db.impl.PublicationDAODBImpl;
import mpp.library.model.dao.file.impl.CheckoutDAOFacade;
import mpp.library.model.dao.file.impl.CheckoutRecordDAOFacade;
import mpp.library.model.dao.file.impl.MemberDAOImpl;
import mpp.library.util.LibraryConstant;
import mpp.library.util.PropertiesUtil;

/**
 * @author Toan Quach
 */
public class DataAccessFactory {
	static HashMap<Class<? extends BaseDAO<?>>, BaseDAO<?>> mapDB = new HashMap<>();
	static HashMap<Class<? extends BaseDAO<?>>, BaseDAO<?>> mapFile = new HashMap<>();

	static {
		mapFile.put(MemberDAO.class, new MemberDAOImpl());
		// mapFile.put(BookDAO.class, new BookDAOImpl());
		// mapFile.put(CopyDAO.class, new CopyDAOImpl());
		// mapFile.put(PeriodicalDAO.class, new PeriodicalDAOImpl());
		mapFile.put(CheckoutDAO.class, new CheckoutDAOFacade());
		mapFile.put(CheckoutRecordDAO.class, new CheckoutRecordDAOFacade());
	}

	static {
		mapDB.put(MemberDAO.class, new MemberDAODBImpl());
		mapDB.put(AddressDAO.class, new AddressDAODBImpl());
		mapDB.put(CopyDAO.class, new CopyDAODBImpl());
		mapDB.put(PublicationDAO.class, new PublicationDAODBImpl());
		mapDB.put(AuthorDAO.class, new AuthorDAODBImpl());
		mapDB.put(CheckoutRecordEntryDAO.class, new CheckoutRecordEntryDAODBFacade());
		mapDB.put(CheckoutDAO.class, new CheckoutDAODBFacade());
	}

	public static BaseDAO<?> getDAOImpl(Class<? extends BaseDAO<?>> clazz) {
		Properties props = PropertiesUtil.getProperties(LibraryConstant.CONFIG_FILE);
		String fileSystem = props.getProperty(LibraryConstant.FILE_SYSTEM);

		return getDAO(clazz, fileSystem);
	}

	private static BaseDAO<?> getDAO(Class<? extends BaseDAO<?>> clazz, String fileSystem) {
		if (LibraryConstant.DATABASE.equals(fileSystem)) {
			if (!mapDB.containsKey(clazz)) {
				throw new IllegalArgumentException("No DAO found for class: " + clazz);
			}
			return mapDB.get(clazz);
		} else if (LibraryConstant.SERIALIZATION.equals(fileSystem)) {
			if (!mapFile.containsKey(clazz)) {
				throw new IllegalArgumentException("No DAO found for class: " + clazz);
			}
			return mapFile.get(clazz);
		}
		throw new IllegalArgumentException("DO NOT support the file system: " + fileSystem + " right now!");
	}
}
