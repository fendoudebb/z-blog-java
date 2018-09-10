package com.msj.blog.service.category.impl;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.repository.category.SecondaryCategoryRepository;
import com.msj.blog.service.category.SecondaryCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<String> findByPrimaryCategoryName(String primaryCategoryName) {
        return secondaryCategoryRepository.findSecondaryCategoryNamesByPrimaryCategoryName(primaryCategoryName)
                .stream().map(SecondaryCategory::getName).collect(Collectors.toList());
    }

}
