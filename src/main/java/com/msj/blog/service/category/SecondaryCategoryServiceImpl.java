package com.msj.blog.service.category;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.repository.article.SecondaryCategoryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:38
 */
@Service
public class SecondaryCategoryServiceImpl implements SecondaryCategoryService {

    @Resource
    private SecondaryCategoryRepository secondaryCategoryRepository;

    @Override
    public Optional<SecondaryCategory> findById(Long id) {
        return secondaryCategoryRepository.findById(id);
    }

    @Override
    public Optional<SecondaryCategory> findByName(String name) {
        return secondaryCategoryRepository.findByName(name);
    }

}
