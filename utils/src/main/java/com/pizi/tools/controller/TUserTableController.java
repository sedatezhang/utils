package com.pizi.tools.controller;


import com.pizi.tools.entity.TUserTable;
import com.pizi.tools.model.view.ReturnCode;
import com.pizi.tools.model.view.exception.BussinessException;
import com.pizi.tools.service.TUserTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 痞子
 * @since 2025-05-22
 */
@Slf4j
@RestController
@RequestMapping("/tUserTable")
@Api(value = "用户管理", tags = {"用户管理"})
public class TUserTableController {
    @Autowired
    private TUserTableService tUserTableService;

    @GetMapping(value = "/getUserList")
    @ApiOperation(value = "获取用户列表")
    public List<TUserTable> getUserList() {
        List<TUserTable> list = tUserTableService.list();
        if (list == null || list.isEmpty()) {
            throw new BussinessException(ReturnCode.RC500.getCode(), ReturnCode.RC500.getMsg());
        }
        log.error("list:{查看用户}", list);
        return list;
    }

}

