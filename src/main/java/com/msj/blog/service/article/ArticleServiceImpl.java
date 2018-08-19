package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.article.ArticleUIPageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.service.category.SecondaryCategoryService;
import com.msj.blog.service.redis.RedisService;
import com.msj.blog.util.MarkdownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String KEY_PREFIX_ARTICLE_VO = "article-vo:";
    private static final String KEY_PREFIX_ARTICLE_PAGE = "article-page:";
    private static final String KEY_PREFIX_ARTICLE_ABOUT_US = KEY_PREFIX_ARTICLE_VO.concat("about_us");
    private static final String KEY_PREFIX_ARTICLE_DISCLAIMER = KEY_PREFIX_ARTICLE_VO.concat("disclaimer");

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private SecondaryCategoryService secondaryCategoryService;
    @Resource
    private RedisService redisService;

    @Override
    public Article saveOrUpdate(Article article) {
        Article saveArticle = articleRepository.save(article);
        if (Objects.equals(article.getArticleProperty(), ArticleProperty.PUBLIC)) {
            redisService.del(KEY_PREFIX_ARTICLE_VO + article.getId());
            redisService.delAll(KEY_PREFIX_ARTICLE_PAGE);
        } else if (Objects.equals(article.getArticleProperty(), ArticleProperty.ABOUT_US)) {
            redisService.del(KEY_PREFIX_ARTICLE_ABOUT_US);
        } else if (Objects.equals(article.getArticleProperty(), ArticleProperty.DISCLAIMER)) {
            redisService.del(KEY_PREFIX_ARTICLE_DISCLAIMER);
        }
        return saveArticle;
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
    public Page<Article> findByPageAndArticleProperty(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByArticlePropertyEqualsOrderByIdDesc(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndArticlePropertyExclude(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByArticlePropertyIsNotOrderByIdDesc(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEqualsOrderByUpdateTimeDesc(auditStatus, articleProperty, pageable);
    }

    @Override
    public List<Article> findByAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEquals(auditStatus, articleProperty);
    }

    @Override
    public boolean saveArticle(ArticleDto articleDto) {
        if (articleDto == null) {
            return false;
        }
        Article article = transferArticleDto2Article(articleDto, null);
        if (article == null) {
            return false;
        }
        Article a = saveOrUpdate(article);
        return a.getId() != null;
    }

    @Override
    public boolean editArticle(ArticleDto articleDto, Article article) {
        if (articleDto == null) {
            return false;
        }
        if (article == null) {
            return false;
        }
        article = transferArticleDto2Article(articleDto, article);
        if (article == null) {
            return false;
        }
        Article a = saveOrUpdate(article);
        return a.getId() != null;
    }

    @Override
    public Optional<ArticleVo> getAdminArticleById(Long id) {
        return findById(id).map(this::transferArticle2ArticleVo);
    }

    @Override
    public ArticleVo getUIArticleById(Long id) {
        Serializable cacheArticleVo = redisService.get(KEY_PREFIX_ARTICLE_VO + id);
        if (cacheArticleVo != null && cacheArticleVo instanceof ArticleVo) {
            return (ArticleVo)cacheArticleVo;
        }
        ArticleVo articleVo = findByIdAndAuditStatusAndArticleProperty(id, AuditStatus.ONLINE, ArticleProperty.PUBLIC).map(this::transferArticle2ArticleVo).orElse(null);
        if (articleVo != null) {
            redisService.set(KEY_PREFIX_ARTICLE_VO + id, articleVo);
        }
        return articleVo;
    }

    @Override
    public PageVo<ArticleAdminPageVo> findAdminArticleDraftByPage(Integer page, Integer size) {
        Page<Article> pages = findByPageAndArticleProperty(ArticleProperty.DRAFT, page, size);
        return transferArticlePage2ArticleAdminPage(pages);
    }

    @Override
    public PageVo<ArticleAdminPageVo> findAdminArticleListByPage(Integer page, Integer size) {
        Page<Article> pages = findByPageAndArticlePropertyExclude(ArticleProperty.DRAFT, page, size);
        return transferArticlePage2ArticleAdminPage(pages);
    }

    @Override
    public PageVo<ArticleUIPageVo> findUIArticleListByPage(Integer page, Integer size) {
        Serializable cachePageVo = redisService.get(KEY_PREFIX_ARTICLE_PAGE + page + "-" + size);
        if (cachePageVo != null && cachePageVo instanceof PageVo) {
            return (PageVo<ArticleUIPageVo>)cachePageVo;
        }
        Page<Article> pages = findByPageAndAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.PUBLIC, page, size);
        PageVo<ArticleUIPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleUIPageVo> articleUIPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleUIPageVo articleUIPageVo = new ArticleUIPageVo();
            SecondaryCategory secondaryCategory = article.getSecondaryCategory();
            BeanUtils.copyProperties(article, articleUIPageVo);
            if (secondaryCategory != null) {
                articleUIPageVo.setArticleCategory(secondaryCategory.getName());
                articleUIPageVo.setArticleCategoryAlias(secondaryCategory.getAlias());
            }
            articleUIPageVos.add(articleUIPageVo);
        });
        pageVo.setContent(articleUIPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        pageVo.setLastModifyTime(System.currentTimeMillis());
        redisService.set(KEY_PREFIX_ARTICLE_PAGE + page + "-" + size, pageVo);
        return pageVo;
    }

    @Override
    public ArticleVo findAboutUsArticle() {
        Serializable cacheAboutUsArticleVo = redisService.get(KEY_PREFIX_ARTICLE_ABOUT_US);
        if (cacheAboutUsArticleVo != null && cacheAboutUsArticleVo instanceof ArticleVo) {
            return (ArticleVo)cacheAboutUsArticleVo;
        }
        ArticleVo aboutUsArticleVo = transfer2SystemArticleVo(ArticleProperty.ABOUT_US);
        redisService.set(KEY_PREFIX_ARTICLE_ABOUT_US, aboutUsArticleVo);
        return aboutUsArticleVo;
    }

    @Override
    public ArticleVo findDisclaimerArticle() {
        Serializable cacheDisclaimerArticleVo = redisService.get(KEY_PREFIX_ARTICLE_DISCLAIMER);
        if (cacheDisclaimerArticleVo != null && cacheDisclaimerArticleVo instanceof ArticleVo) {
            return (ArticleVo)cacheDisclaimerArticleVo;
        }
        ArticleVo disclaimerArticleVo = transfer2SystemArticleVo(ArticleProperty.DISCLAIMER);
        redisService.set(KEY_PREFIX_ARTICLE_DISCLAIMER, disclaimerArticleVo);
        return disclaimerArticleVo;
    }

    @Override
    public ArticleDto findArticleDto(Article article) {
        return transferArticle2ArticleDto(article);
    }

    private PageVo<ArticleAdminPageVo> transferArticlePage2ArticleAdminPage(Page<Article> pages) {
        PageVo<ArticleAdminPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleAdminPageVo> articleAdminPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleAdminPageVo articleAdminPageVo = new ArticleAdminPageVo();
            BeanUtils.copyProperties(article, articleAdminPageVo);
            SecondaryCategory secondaryCategory = article.getSecondaryCategory();
            if (secondaryCategory != null) {
                articleAdminPageVo.setCategory(secondaryCategory.getName());
                articleAdminPageVo.setCategoryAlias(secondaryCategory.getAlias());
            }
            articleAdminPageVo.setAuditStatus(article.getAuditStatus().name());
            articleAdminPageVos.add(articleAdminPageVo);
        });
        pageVo.setContent(articleAdminPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        return pageVo;
    }

    private ArticleVo transfer2SystemArticleVo(ArticleProperty articleProperty) {
        return findByAuditStatusAndArticleProperty(AuditStatus.ONLINE, articleProperty)
                .stream()
                .findFirst()
                .map(article -> {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(article, articleVo);
                    articleVo.setContent(MarkdownUtil.parse(article.getContent()));
                    articleVo.setArticleCategoryAlias(article.getSecondaryCategory().getAlias());
                    return articleVo;
                }).orElse(null);
    }

    private ArticleVo transferArticle2ArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setContent(MarkdownUtil.parse(article.getContent()));
        SecondaryCategory secondaryCategory = article.getSecondaryCategory();
        if (secondaryCategory != null) {
            articleVo.setArticleCategory(secondaryCategory.getName());
            articleVo.setArticleCategoryAlias(secondaryCategory.getAlias());
        }
        return articleVo;
    }

    private Article transferArticleDto2Article(ArticleDto articleDto, Article article) {
        if (article == null) {
            article = new Article();
        }
        SecondaryCategory secondaryCategory = secondaryCategoryService.findByName(articleDto.getCategory()).orElse(null);
        if (secondaryCategory == null) {
            return null;
        }
        BeanUtils.copyProperties(articleDto, article);
        article.setSecondaryCategory(secondaryCategory);
        try {
            ArticleProperty articleProperty = ArticleProperty.valueOf(articleDto.getArticleProperty());
            article.setArticleProperty(articleProperty);
        } catch (IllegalArgumentException e) {
            log.info("enum error constant: {}", e.getMessage());
            return null;
        }
        return article;
    }

    private ArticleDto transferArticle2ArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article, articleDto);
        articleDto.setCategory(article.getSecondaryCategory().getAlias());
        articleDto.setArticleProperty(article.getArticleProperty().name());
        return articleDto;
    }
}
