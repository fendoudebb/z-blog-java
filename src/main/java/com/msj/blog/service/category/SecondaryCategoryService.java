package com.msj.blog.service.category;

import com.msj.blog.entity.domain.category.SecondaryCategory;

import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:38
 */
public interface SecondaryCategoryService {

    Optional<SecondaryCategory> findById(Long id);

    Optional<SecondaryCategory> findByName(String name);

    List<String> findByPrimaryCategoryName(String primaryCategoryName);

}
