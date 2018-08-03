package com.msj.blog.controller.ui.tool;

import com.msj.blog.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * zbj: create on 2018/06/07 12:21
 */
@Controller
@RequestMapping("/format")
public class FormatController extends BaseController {
    private static final long lastModifiedTime = System.currentTimeMillis();

    @ModelAttribute
    public void modelAttribute(Model model) {

    }

    @GetMapping("/json")
    public String json(WebRequest request) {
        boolean lastModified = request.checkNotModified(lastModifiedTime);
        if (lastModified) {
            return null;
        }
        return "tool/format_json";
    }

}
