package org.zerock.controller;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	//requestMapping의 변화 -> 첫번째 method가 두번째 method로 변화함!
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("basic get........");
	}
	
	@GetMapping("/basicOnlyGet") //console에 제대로 뜨는지 체크 
	public void basicGet2() {
		log.info("basic get onlyGet...");
	}
	
	@GetMapping("/ex01") //Controller의 parameter 수집 예시
	public String ex01(SampleDTO dto) {
		log.info(""+dto);
		
		return "ex01";
	}
	
	@GetMapping("/ex02") //파라미터 수집과 변환(수집된 age가 자기 타입에 맞게 자동으로 숫자로 변환됨)
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name = " + name);
		log.info("age = " + age);
		
		return "ex02";
	}
	
	@GetMapping("/ex02List") //리스트, 배열 처리 방법 
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids = " + ids);
		
		return "ex02List";
	}
	
	@GetMapping("/ex02Bean") //객체 리스트 처리방법 [는 %5B, ]는 %5D로 넣자(문자열 인식x)
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos = " + list);
		
		return "ex02Bean";
	}
	
	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) { SimpleDateFormat
	 * dateForm = new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class, new
	 * CustomDateEditor(dateForm, false)); }
	 */
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo = " + todo);
		
		return "ex03";
	}
	
	@GetMapping("/ex04") //강제로 전달받은 파라미터를 모델에 담아 전달함
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto: " + dto);
		log.info("page: " + page);
		
		return "/sample/ex04";
	}
	
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05 called");
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() { //spring은 자동으로 브라우저에 json타입으로 객체 변환해 전달
		log.info("/ex06 called");
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07 called");
		String msg = "{\"name: \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload called");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			log.info("/exUploadPost called--------------");
			log.info("name: " + file.getOriginalFilename());
			log.info("size: " + file.getSize());
		});
		
	}
	
	@GetMapping(value="/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		log.info("Mime type: "+MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요!";
	}
	
	@GetMapping("/all")
	  public void doAll() {
	    
	    log.info("do all can access everybody");
	  }
	  
	  @GetMapping("/member")
	  public void doMember() {
	    
	    log.info("logined member");
	  }
	  
	  @GetMapping("/admin")
	  public void doAdmin() {
	    
	    log.info("admin only");
	  }  
	  	  
	  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	  @GetMapping("/annoMember")
	  public void doMember2() {
	    
	    log.info("logined annotation member");
	  }
	  
	  
	  @Secured({"ROLE_ADMIN"})
	  @GetMapping("/annoAdmin")
	  public void doAdmin2() {

	    log.info("admin annotaion only");
	  }
}
