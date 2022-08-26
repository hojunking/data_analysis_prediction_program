package education_program.web.controller;

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
import org.springframework.web.servlet.ModelAndView;

import education_program.web.service.TbMemberService;

@RestController
public class TbMemberController {

	@Autowired
	private TbMemberService tbMemberService;
	
	
	@RequestMapping("/selectMember")
	public ModelAndView selectMember(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ModelAndView modelAndView = new ModelAndView();

		List<HashMap<String, Object>> selectMember = null;

		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			selectMember = tbMemberService.selectMember(paramMap);
			
			//System.out.println("selectMember="+selectMember.size());

		} catch (Exception exception) {
			exception.printStackTrace();

			return modelAndView;
		}

		modelAndView.addObject("selectMember", selectMember);

		modelAndView.setViewName("/selectMember");

		return modelAndView;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("/login");

		return modelAndView;
	}
	
	@RequestMapping("/loginProcess")
	@ResponseBody
	public Map<String, Object> loginProcess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String MEMBER_ID = httpServletRequest.getParameter("MEMBER_ID");
		String MEMBER_PASSWORD = httpServletRequest.getParameter("MEMBER_PASSWORD");
		
		HashMap<String, Object> selectTbMemberMap = null;
		HttpSession httpSession = null;
		
		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>(); //데이터베이스 PARAM
		
			//데이터 베이스 작업
			paramMap.clear();
			paramMap.put("MEMBER_ID", MEMBER_ID);
			paramMap.put("MEMBER_PASSWORD", MEMBER_PASSWORD);
			
			selectTbMemberMap = tbMemberService.selectMemberCheck(paramMap);
			
			if (selectTbMemberMap == null) {
				returnMap.put("resultCode", "1000");
				returnMap.put("resultNote", "해당 정보로 사용자 없습니다.");

				return returnMap;
			}
			
			httpSession = httpServletRequest.getSession(true);
			httpSession.setAttribute("MEMBER_INFO", selectTbMemberMap);
			
			System.out.println("로그인 성공");
		
		} catch(Exception exception) {
			
			exception.printStackTrace();

			returnMap.put("resultCode", "9999");
			returnMap.put("resultNote", "데이터베이스 오류");

			return returnMap;
		}
		
		returnMap.put("resultCode", "0000");
		returnMap.put("resultNote", "성공");

		return returnMap;
	}
	
	
}
