package com.kxy.controller;

import com.kxy.annotation.KXYRequestMapping;
import com.kxy.annotation.KXYController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: kxy
 * @Date: 2019/7/23 23:11
 * @Desraption
 **/

@KXYController
@KXYRequestMapping("/qq")
public class DemoController {

    @KXYRequestMapping(value = "/as")
    public void sda(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("aowijd");
    }
}
