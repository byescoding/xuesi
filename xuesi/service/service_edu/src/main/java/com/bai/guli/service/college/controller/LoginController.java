package com.bai.guli.service.college.controller;

import com.bai.guli.common.base.result.R;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin  //解决跨域
@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("/info")
    public R info(){
     return    R.ok().data("name","admin").data("roles","[admin]")
                .data("avatar","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600707715038&di=8288669b559017cd99f1583adbc88964&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3616242789%2C1098670747%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D900%26h%3D1350");
    }
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }
}
