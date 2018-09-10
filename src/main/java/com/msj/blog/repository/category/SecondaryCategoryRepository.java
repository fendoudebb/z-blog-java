package com.msj.blog.repository.category;

import com.msj.blog.entity.domain.category.SecondaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * zbj: created on 2018/6/1 21:38.
 */
@Repository
public interface SecondaryCategoryRepository extends JpaRepository<SecondaryCategory, Long>, PagingAndSortingRepository<SecondaryCategory, Long> {

    Optional<SecondaryCategory> findByName(String name);

    @Query("select new SecondaryCategory(sc.name) from SecondaryCategory sc where sc.primaryCategory.name = ?1 order by sc.sort asc ")
    List<SecondaryCategory> findSecondaryCategoryNamesByPrimaryCategoryName(String primaryCategoryName);

}
