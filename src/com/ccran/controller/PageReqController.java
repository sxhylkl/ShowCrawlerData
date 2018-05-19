package com.ccran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccran.service.ShowAuthroBlogSumService;

/**
 * 
 * @author chenran
 * 进行路径请求的跳转
 */
@Controller
public class PageReqController {
	@Autowired
	ShowAuthroBlogSumService service;
	
	@RequestMapping("/")
	public ModelAndView index(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("index");
		int authorSum=service.getAuthorSum();
		int blogSum=service.getBlogSum();
		mav.addObject("authorSum",authorSum);
		mav.addObject("blogSum",blogSum);
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
