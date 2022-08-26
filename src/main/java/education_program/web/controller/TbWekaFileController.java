package education_program.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import education_program.web.common.analysis.WekaJ48;
import education_program.web.common.analysis.WekaRandomTree;
import education_program.web.service.TbMemberService;
import education_program.web.service.TbWekaFileService;

@RestController
public class TbWekaFileController {

	@Autowired
	private TbWekaFileService tbWekaFileService;
	
	@RequestMapping("/fileUpload")
	public ModelAndView fileUpload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("/fileUpload");

		return modelAndView;
	}
	
	@RequestMapping("/fileUploadProcess")
	@ResponseBody
	public Map<String, Object> fileUploadProcess(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletResponse httpServletResponse) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		
		String WEKADATANAME = multipartHttpServletRequest.getParameter("WEKADATANAME");
		int WEKA_Y = Integer.parseInt(multipartHttpServletRequest.getParameter("WEKA_Y"));
		MultipartFile multipartFile = multipartHttpServletRequest.getFile("WEKAFILENAME");
		
		try {
			multipartFile.transferTo(new File("C:\\KNU\\UPLOAD\\"+multipartFile.getOriginalFilename()));
		} catch (Exception exception) {
			exception.printStackTrace();

			returnMap.put("resultCode", "9999");
			returnMap.put("resultMsg", "저장실패");
			
			return returnMap;
		}
		
		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("WEKADATANAME", WEKADATANAME);
			paramMap.put("WEKA_Y", WEKA_Y);
			paramMap.put("WEKAFILENAME", multipartFile.getOriginalFilename());
			
			tbWekaFileService.insertWekaFile(paramMap);
		} catch (Exception exception) {
			exception.printStackTrace();

			returnMap.put("resultCode", "9999");
			returnMap.put("resultMsg", "저장 실패");
			
			return returnMap;
			
		}
		
		returnMap.put("resultCode", "0000");
		returnMap.put("resultMsg", "저장 성공");
		
		return returnMap;
	}
	
	@RequestMapping("/selectWekaFile")
	public ModelAndView selectWekaFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ModelAndView modelAndView = new ModelAndView();

		List<HashMap<String, Object>> selectWekaFile = null;

		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			selectWekaFile = tbWekaFileService.selectWekaFile(paramMap);

		} catch (Exception exception) {
			exception.printStackTrace();

			return modelAndView;
		}

		modelAndView.addObject("selectWekaFile", selectWekaFile);

		modelAndView.setViewName("/selectWekaFile");

		return modelAndView;
	}
	
	@RequestMapping("/wekaProcess")
	@ResponseBody
	public Map<String, Object> wekaProcess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String WEKA_NO = httpServletRequest.getParameter("WEKA_NO");
		
		//DB 질의
		HashMap<String, Object> selectWekaFileInfo = null;
		
		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>(); //데이터베이스 PARAM
		
			//데이터 베이스 작업
			paramMap.clear();
			paramMap.put("WEKA_NO", WEKA_NO);
			
			selectWekaFileInfo = tbWekaFileService.selectWekaFileInfo(paramMap);
			
			//WekaJ48 weka = new WekaJ48(); 
			WekaRandomTree weka = new WekaRandomTree();
			
			//returnMap = weka.processWekaJ48("D:\\KNU\\UPLOAD\\"+selectWekaFileInfo.get("WEKAFILENAME"), Integer.parseInt(selectWekaFileInfo.get("WEKA_Y").toString()));
			returnMap = weka.processWekaRandomTree("C:\\KNU\\UPLOAD\\"+selectWekaFileInfo.get("WEKAFILENAME"), Integer.parseInt(selectWekaFileInfo.get("WEKA_Y").toString()));
			
			/*
			System.out.println("returnMap="+returnMap.get("DATA_COUNT"));
			System.out.println("returnMap="+returnMap.get("CORRECT_COUNT"));
			System.out.println("returnMap="+returnMap.get("INCORRECT_COUNT"));
			
			
			System.out.println("returnMap="+returnMap.get("ALL"));
			*/
			
		} catch(Exception exception) {
			
			exception.printStackTrace();

			returnMap.put("resultCode", "9999");
			returnMap.put("resultNote", "데이터베이스 오류");

			return returnMap;
		}
		
		//DB 정보 J48알고리즘 결과
		
		return returnMap;
	}
}
