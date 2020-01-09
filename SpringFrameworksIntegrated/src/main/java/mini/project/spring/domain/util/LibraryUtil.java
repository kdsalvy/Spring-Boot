package mini.project.spring.domain.util;

import java.util.List;

import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;

public class LibraryUtil {

	public static <T> void verifyContent(List<T> list)
			throws NoContentException {
		if (list.isEmpty())
			throw new NoContentException("No Books Found");
	}

	public static void existsElseThrow(boolean exists, String errorMsg) throws NoSuchDataFoundToUpdate {
		if (!exists) {
			throw new NoSuchDataFoundToUpdate(errorMsg);
		}
	}
}
