package com.kxy.controller;

import com.kxy.annotation.KXYRequestMapping;
import com.kxy.annotation.KXYController;

/**
 * @Auther: kxy
 * @Date: 2019/7/23 23:11
 * @Desraption
 **/

@KXYController
@KXYRequestMapping("/qq")
public class DemoController {

    @KXYRequestMapping(value = "/as")
    public void sda(){
        System.out.println("aowijd");
    }
}
