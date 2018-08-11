package com.msj.blog.repository.article;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * zbj: created on 2018/6/1 21:38.
 */
@Repository
public interface SecondaryCategoryRepository extends JpaRepository<SecondaryCategory, Long>, PagingAndSortingRepository<SecondaryCategory, Long> {

    Optional<SecondaryCategory> findByName(String name);

}
