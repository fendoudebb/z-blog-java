package com.msj.blog.service.category;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.domain.category.PrimaryCategory;
import com.msj.blog.entity.vo.category.SecondaryCategoryVo;
import com.msj.blog.entity.vo.category.PrimaryCategoryVo;
import com.msj.blog.repository.article.PrimaryCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:44
 */
@Service
@CacheConfig(cacheNames = "article-primary-category")
public class PrimaryCategoryServiceImpl implements PrimaryCategoryService {

    @Resource
    private PrimaryCategoryRepository primaryCategoryRepository;

    @Override
    public Optional<PrimaryCategory> findById(Long id) {
        return primaryCategoryRepository.findById(id);
    }

    @Override
    @Cacheable(key = "'article-all-primary-category'")
    public List<PrimaryCategoryVo> findAllPrimaryCategories() {
        List<PrimaryCategory> primaryCategories = primaryCategoryRepository.findAll();
        if (primaryCategories.isEmpty()) {
            return Collections.emptyList();
        }
        List<PrimaryCategoryVo> primaryCategoryVos = new ArrayList<>();
        PrimaryCategoryVo primaryCategoryVo;
        for (PrimaryCategory primaryCategory : primaryCategories) {
            primaryCategoryVo = new PrimaryCategoryVo();
            BeanUtils.copyProperties(primaryCategory, primaryCategoryVo);
            ArrayList<SecondaryCategoryVo> secondaryCategoryVos = new ArrayList<>();
            SecondaryCategoryVo secondaryCategoryVo;
            for (SecondaryCategory secondaryCategory : primaryCategory.getSecondaryCategories()) {
                secondaryCategoryVo = new SecondaryCategoryVo();
                BeanUtils.copyProperties(secondaryCategory, secondaryCategoryVo);
                secondaryCategoryVos.add(secondaryCategoryVo);
            }
            primaryCategoryVo.setSecondaryCategoryVos(secondaryCategoryVos);
            primaryCategoryVos.add(primaryCategoryVo);
        }
        return primaryCategoryVos;
    }

}
