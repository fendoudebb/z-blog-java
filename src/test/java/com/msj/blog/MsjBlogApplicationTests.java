package com.msj.blog;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.repository.category.SecondaryCategoryRepository;
import com.msj.blog.service.article.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsjBlogApplicationTests {

	@Resource
	private SecondaryCategoryRepository secondaryCategoryRepository;

	@Test
	public void contextLoads() {
		List<SecondaryCategory> it = secondaryCategoryRepository.findSecondaryCategoryNamesByPrimaryCategoryName("IT");
	}

}
