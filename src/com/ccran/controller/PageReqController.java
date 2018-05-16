package com.ccran.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author chenran
 * 进行路径请求的跳转
 */
@Controller
public class PageReqController {
	@RequestMapping("/")
	public ModelAndView index(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/csdn")
	public ModelAndView csdn(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("csdn");
		return mav;
	}
	
	@RequestMapping("/cnblogs")
	public ModelAndView cnblogs(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("cnblogs");
		return mav;
	}
}
