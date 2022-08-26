package education_program.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import education_program.web.mapper.TbWekaFileMapper;

@Service
public class TbWekaFileService {

	@Autowired
	TbWekaFileMapper tbWekaFileMapper;
		
	public List<HashMap<String, Object>> selectWekaFile(HashMap<String, Object> paramMap) throws Exception {
		return tbWekaFileMapper.selectWekaFile(paramMap);
	}
	
	public HashMap<String, Object> selectWekaFileInfo(HashMap<String, Object> paramMap) throws Exception {
		return tbWekaFileMapper.selectWekaFileInfo(paramMap);
	}

	public int insertWekaFile(HashMap<String, Object> paramMap) throws Exception {
		return tbWekaFileMapper.insertWekaFile(paramMap);
	}
	
}
