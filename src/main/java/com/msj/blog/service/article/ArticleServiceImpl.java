package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.article.ArticleUIPageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.util.MarkdownUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleCategoryService articleCategoryService;

    @Override
    public Article saveOrUpdate(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Optional<Article> findByIdAndAuditStatusAndArticleProperty(Long id, AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findByIdAndAuditStatusEqualsAndArticlePropertyEquals(id, auditStatus, articleProperty);
    }

    @Override
    public Page<Article> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByOrderByUpdateTimeDesc(pageable);
    }

    @Override
    public Page<Article> findByPageAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEqualsOrderByUpdateTimeDesc(auditStatus, articleProperty, pageable);
    }

    @Override
    public Stream<Article> findByAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEquals(auditStatus, articleProperty);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"article-page-ui"}, allEntries = true)
    public boolean saveArticle(ArticleDto articleDto) {
        if (articleDto == null) {
            return false;
        }
        Article article = new Article();
        ArticleCategory articleCategory = articleCategoryService.findById(articleDto.getCategoryId()).orElse(null);
        BeanUtils.copyProperties(articleDto, article);
        article.setArticleCategory(articleCategory);
        Article a = saveOrUpdate(article);
        return a.getId() != null;
    }

    @Override
    public Optional<ArticleVo> getAdminArticleById(Long id) {
        return findById(id).map(this::transferArticle2ArticleVo);
    }

    @Override
    @Cacheable(key = "'article-ui-' + #p0")
    public Optional<ArticleVo> getUIArticleById(Long id) {
        return findByIdAndAuditStatusAndArticleProperty(id, AuditStatus.ONLINE, ArticleProperty.PUBLIC).map(this::transferArticle2ArticleVo);
    }

    @Override
    public PageVo<ArticleAdminPageVo> findAdminArticleByPage(Integer page, Integer size) {
        Page<Article> pages = findByPage(page, size);
        PageVo<ArticleAdminPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleAdminPageVo> articleAdminPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleAdminPageVo articleAdminPageVo = new ArticleAdminPageVo();
            BeanUtils.copyProperties(article, articleAdminPageVo);
            ArticleCategory articleCategory = article.getArticleCategory();
            if (articleCategory != null) {
                articleAdminPageVo.setCategory(articleCategory.getName());
            }
            articleAdminPageVo.setAuditStatus(article.getAuditStatus().getType());
            articleAdminPageVos.add(articleAdminPageVo);
        });
        pageVo.setContent(articleAdminPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        return pageVo;
    }

    @Override
    @Cacheable(value = {"article-page-ui"}, key = "'article-page-ui-' + #p0 + '-' + #p1")
    public PageVo<ArticleUIPageVo> findUIArticleByPage(Integer page, Integer size) {
        Page<Article> pages = findByPageAndAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.PUBLIC, page, size);
        PageVo<ArticleUIPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleUIPageVo> articleUIPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleUIPageVo articleUIPageVo = new ArticleUIPageVo();
            ArticleCategory articleCategory = article.getArticleCategory();
            BeanUtils.copyProperties(article, articleUIPageVo);
            if (articleCategory != null) {
                articleUIPageVo.setArticleCategory(articleCategory.getName());
                articleUIPageVo.setArticleCategoryAlias(articleCategory.getAlias());
            }
            articleUIPageVos.add(articleUIPageVo);
        });
        pageVo.setContent(articleUIPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        pageVo.setLastModifyTime(System.currentTimeMillis());
        return pageVo;
    }

    @Override
    @Cacheable(key = "'article-about-us'")
    public Optional<ArticleVo> findAboutUsArticle() {
        return findByAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.ABOUT_US)
                .findFirst()
                .map(article -> {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(article, articleVo);
                    articleVo.setContent(MarkdownUtil.parse(article.getContent()));
                    return articleVo;
                });
    }

    private ArticleVo transferArticle2ArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setContent(MarkdownUtil.parse(article.getContent()));
        ArticleCategory articleCategory = article.getArticleCategory();
        if (articleCategory != null) {
            articleVo.setArticleCategory(articleCategory.getName());
            articleVo.setArticleCategoryAlias(articleCategory.getAlias());
        }
        return articleVo;
    }
}
