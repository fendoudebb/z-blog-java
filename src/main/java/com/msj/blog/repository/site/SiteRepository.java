package com.msj.blog.repository.site;

import com.msj.blog.entity.domain.site.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * zbj: created on 2018/4/14 11:18.
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, PagingAndSortingRepository<Site, Long> {

}
