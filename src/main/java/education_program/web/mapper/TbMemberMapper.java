package education_program.web.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbMemberMapper {
	
	public List<HashMap<String, Object>> selectMember(HashMap<String, Object> paramMap) throws Exception;
	
	public HashMap<String, Object> selectMemberCheck(HashMap<String, Object> paramMap) throws Exception;
	
	public HashMap<String, Object> selectMemberInfo(HashMap<String, Object> paramMap) throws Exception;
	
}
