package com.cos.book.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;

//단위 테스트 (DB 관련된 Bean이 IoC에 등록되면 됨)
@Slf4j
@Transactional
@AutoConfigureTestDatabase(replace=Replace.ANY) // Replace.ANY 가짜 DB로 테스트, Replace.NONE 실제 DB로 테스트
@DataJpaTest // Repository들을 다 IoC에 등록해둠 -> Mock으로 설정 안해도 됨!
public class BookRepositoryUnitTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void save_테스트() {
		// given
		Book book = new Book(null, "책제목1", "책저자1");
		
		// when
		Book bookEntity = bookRepository.save(book);
	
		// then
		assertEquals("책제목1", bookEntity.getTitle());
	}
	
	@Test
	public void findAll_테스트() {
		// given
		bookRepository.saveAll(
				Arrays.asList(
						new Book(null, "스프링부트 따라하기", "코스"),
						new Book(null, "리엑트 따라하기", "코스")
				)
			);
		
		// when
		List<Book> bookEntitys = bookRepository.findAll();
		
		// then
		log.info("bookEntitys : "+bookEntitys.size() );
		assertNotEquals(0, bookEntitys.size());
		assertEquals(2, bookEntitys.size());
	}
}
