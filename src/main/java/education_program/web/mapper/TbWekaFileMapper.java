package education_program.web.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbWekaFileMapper {
	
	public List<HashMap<String, Object>> selectWekaFile(HashMap<String, Object> paramMap) throws Exception;
	
	public HashMap<String, Object> selectWekaFileInfo(HashMap<String, Object> paramMap) throws Exception;
	
	public int insertWekaFile(HashMap<String, Object> paramMap) throws Exception;
	
}
