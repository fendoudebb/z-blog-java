package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.repository.article.ArticleModuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:44
 */
@Service
public class ArticleModuleServiceImpl implements ArticleModuleService {

    @Resource
    private ArticleModuleRepository articleModuleRepository;

    @Override
    public Optional<ArticleModule> findById(Long id) {
        return articleModuleRepository.findById(id);
    }

}
