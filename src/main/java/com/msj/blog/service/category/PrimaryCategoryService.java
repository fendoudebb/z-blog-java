package com.msj.blog.service.category;

import com.msj.blog.entity.domain.category.PrimaryCategory;
import com.msj.blog.entity.vo.category.PrimaryCategoryVo;

import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:43
 */
public interface PrimaryCategoryService {

    Optional<PrimaryCategory> findById(Long id);

    List<PrimaryCategoryVo> findAllPrimaryCategories();

}
