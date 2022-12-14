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
			HashMap<String, Object> paramMap = new HashMap<String, Object>(); //?????????????????? PARAM
		
			//????????? ????????? ??????
			paramMap.clear();
			paramMap.put("MEMBER_ID", MEMBER_ID);
			paramMap.put("MEMBER_PASSWORD", MEMBER_PASSWORD);
			
			selectTbMemberMap = tbMemberService.selectMemberCheck(paramMap);
			
			if (selectTbMemberMap == null) {
				returnMap.put("resultCode", "1000");
				returnMap.put("resultNote", "?????? ????????? ????????? ????????????.");

				return returnMap;
			}
			
			httpSession = httpServletRequest.getSession(true);
			httpSession.setAttribute("MEMBER_INFO", selectTbMemberMap);
			
			System.out.println("????????? ??????");
		
		} catch(Exception exception) {
			
			exception.printStackTrace();

			returnMap.put("resultCode", "9999");
			returnMap.put("resultNote", "?????????????????? ??????");

			return returnMap;
		}
		
		returnMap.put("resultCode", "0000");
		returnMap.put("resultNote", "??????");

		return returnMap;
	}
	
	
}
