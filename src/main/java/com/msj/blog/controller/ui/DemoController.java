package com.msj.blog.controller.ui;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.Response;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.wechat.WechatCallback;
import com.msj.blog.event.DemoEvent;
import com.msj.blog.service.article.ArticleService;
import com.msj.blog.task.DemoAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * zbj: created on 2018/6/17 21:10.
 */
@Slf4j
@Controller
public class DemoController extends BaseController {

    @Resource
    private ArticleService articleService;

    @Resource
    private DemoAsyncTask asyncTask;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RestTemplate restTemplate;

    private static final Long time = System.currentTimeMillis();

    /**
     * 304返回,json字符串缓存
     */
    @GetMapping("/aaa")
    public ResponseEntity<ArticleVo> a() {
        System.out.println("11111111111");
        ArticleVo articleVo = articleService.getUIArticleById(1L).orElse(null);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .lastModified(articleVo.getUpdateTime().getLong(ChronoField.NANO_OF_SECOND))
//                .eTag(String.valueOf(articleVo.getUpdateTime().getTime()))
                .body(articleVo);
    }

    /**
     * 304返回,页面缓存
     */
    @GetMapping("/aaa/page")
    public String aPage(WebRequest webRequest) {
        System.out.println("11111111111");
        boolean b = webRequest.checkNotModified(time);
        Locale locale = webRequest.getLocale();
        String description = webRequest.getDescription(true);
        System.out.println("description: " + description);
        String contextPath = webRequest.getContextPath();
        System.out.println("contextPath: " + contextPath);
        String remoteUser = webRequest.getRemoteUser();
        System.out.println("remoteUser: " + remoteUser);
        System.out.println("2222222222: " + b + ",locale: " + locale.toString());
        if (b) {
            return null;
        }
        return "tool/format_json";
    }

    /**
     * Async 线程池
     */
    @GetMapping("/bbb")
    @ResponseBody
    public Callable<Response> bbb() throws InterruptedException {
        asyncTask.sleep();
        System.out.println("aaaa:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
        return () -> {
            System.out.println("bbbbbb:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
            Thread.sleep(Long.parseLong("2000"));
            System.out.println("cccccccccc:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
            return getResponse("aaa");
        };
    }

    /**
     * 解析XmlRootElement注解
     *
     * @param xml
     */
    @RequestMapping(value = "/bbb/rest", consumes = MediaType.APPLICATION_XML_VALUE, produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public WechatCallback rest(@RequestBody WechatCallback xml) {
        log.info("wechat callback xml: " + xml);
        ArticleVo articleVo = restTemplate.getForObject("http://localhost:9999/bbb", ArticleVo.class);
        return xml;
    }

    /**
     * DeferredResult,NIO
     */
    @RequestMapping("/ccc")
    @ResponseBody
    public DeferredResult<String> ccc() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // Add deferredResult to a Queue or a Map...
        deferredResult.setResult("abcd");
        System.out.println("1111111");
        new Thread(() -> {
            try {
                System.out.println("22222222222");
                Thread.sleep(2000);
                System.out.println("3333333333");
                deferredResult.setResult("Result");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return deferredResult;
    }

    /**
     * redis 作为mq
     */
    @RequestMapping("/mq")
    @ResponseBody
    public String mq(@RequestParam String text) {
        stringRedisTemplate.convertAndSend("phone", text);
        return text;
    }

    @Resource
    private ApplicationContext applicationContext;

    @GetMapping("/event")
    @ResponseBody
    public void demoEvent() {
        applicationContext.publishEvent(new DemoEvent(this));
    }

    @GetMapping("/rest/docs")
    @ResponseBody
    public String restDocs() {
        return "rest docs ok";
    }

    public static void main(String[] args) {

    }
}
