package com.msj.blog;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.service.article.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsjBlogApplicationTests {

	@Resource
	private ArticleService articleService;

	@Test
	public void contextLoads() {
		articleService.findByPageAndAuditStatus(AuditStatus.online, 0, 10);
	}

}
