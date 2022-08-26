package education_program.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import education_program.web.mapper.TbMemberMapper;

@Service
public class TbMemberService {

	@Autowired
	TbMemberMapper tbMemberMapper;
	
	public List<HashMap<String, Object>> selectMember(HashMap<String, Object> paramMap) throws Exception {
		return tbMemberMapper.selectMember(paramMap);
	}
	
	public HashMap<String, Object> selectMemberCheck(HashMap<String, Object> paramMap) throws Exception {
		return tbMemberMapper.selectMemberCheck(paramMap);
	}
	
	public HashMap<String, Object> selectMemberInfo(HashMap<String, Object> paramMap) throws Exception {
		return tbMemberMapper.selectMemberInfo(paramMap);
	}
	
}
