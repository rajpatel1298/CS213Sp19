package songlib.dao;

import java.util.List;
import java.util.Optional;

import songlib.models.SongRecordDto;

public interface Dao<T> {

	List<T> getAll();
	void saveAll(List<T> t);
}
