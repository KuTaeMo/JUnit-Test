package com.cos.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.h2.command.dml.MergeUsing.When;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// 단위 테스트 (Controller 관련 로직만 띄우기) - Controller, Filter, ControllerAdvice

@Slf4j
@WebMvcTest
//@ExtendWith //스프링에서 단위테스트할 때 필수! webmvctest에 들어가있음 -junit 5
public class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc; // 주소호출해서 테스트
	// MockMvc는 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체를 만들어서 애플리케이션 서버에 배포하지 않고도 스프링
	// MVC 동작을 재현할 수 있는 클래스를 의미

	@MockBean // IoC 환경에 bean 등록됨. cotroller를 테스트하기 위해선 service가 필요한데 메모리에 들어있지 않기 때문에 등록해준다.
	private BookService BookService;

	// BDDMockito 패턴
	@Test
	public void save_테스트() throws Exception {
		// given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book);
		when(BookService.저장하기(book)).thenReturn(new Book(1L, "스프링 따라하기", "코스")); // 스텁

		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("스프링 따라하기"))
		.andExpect(jsonPath("$.author").value("코스"))
		.andDo(MockMvcResultHandlers.print());
	}
	

}
