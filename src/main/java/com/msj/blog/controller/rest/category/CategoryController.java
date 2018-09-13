package com.msj.blog.controller.rest.category;

import com.msj.blog.controller.rest.BaseController;
import com.msj.blog.entity.dto.category.PrimaryCategoryNameDto;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import com.msj.blog.service.cache.CacheService;
import com.msj.blog.service.category.SecondaryCategoryService;
import com.msj.blog.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Resource
    private CacheService cacheService;
    @Resource
    private SecondaryCategoryService secondaryCategoryService;

    @PostMapping("/secondary")
    public Response secondaryCategory(@RequestBody @Valid PrimaryCategoryNameDto primaryCategoryNameDto) {
        String primaryCategoryName = primaryCategoryNameDto.getPrimaryCategoryName();
        String secondaryCategoryJsonStr;
        secondaryCategoryJsonStr = cacheService.getSecondaryCategoryNames(primaryCategoryName);
        if (StringUtils.isEmpty(secondaryCategoryJsonStr)) {
            List<String> secondaryCategoryNames = secondaryCategoryService.findByPrimaryCategoryName(primaryCategoryName);
            if (secondaryCategoryNames != null) {
                secondaryCategoryJsonStr = JSON.write(secondaryCategoryNames);
                if (!StringUtils.isEmpty(secondaryCategoryJsonStr)) {
                    cacheService.setSecondaryCategoryNames(primaryCategoryName, secondaryCategoryJsonStr);
                }
            }
        }
        if (StringUtils.isEmpty(secondaryCategoryJsonStr)) {
            return getResponse().msg(MsgTable.SECONDARY_CATEGORY_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(secondaryCategoryJsonStr, List.class));
        }
    }

}
