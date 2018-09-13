package com.msj.blog.controller.rest.excel;

import com.msj.blog.controller.rest.BaseController;
import com.msj.blog.entity.dto.excel.ExcelData;
import com.msj.blog.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * zbj: create on 2018/08/08 16:26
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@PreAuthorize(value = "hasRole('ADMIN')")
public class ExcelController extends BaseController {

    @Resource
    private ExcelUtil excelUtil;

    @GetMapping(value = "/download", produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public ResponseEntity<byte[]> download() {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList<>();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = new ArrayList<>();
        row.add("11111111111");
        row.add("22222222222");
        row.add("3333333333");
        row.add("dasdasdas");
        rows.add(row);

        data.setRows(rows);
        ByteArrayOutputStream os = excelUtil.exportExcel2003(data);
        byte[] content = os.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=" + new String(("aaa.xls").getBytes()));
        return new ResponseEntity<>(content, httpHeaders, HttpStatus.OK);
    }
}
