package com.bai.guli.service.oss.controller;


import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.service.base.exception.CollegeException;
import com.bai.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping("/admin/oss/file")
@Api("OSS管理")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("图片上传")
    @PostMapping("upload")
    public R upload(@ApiParam("文件") @RequestParam("file") MultipartFile file,
                    @ApiParam("文件夹") @RequestParam("module") String module
    ){
        try {
            InputStream inputStream = file.getInputStream(); //获取文件的输入流
            String originalFilename = file.getOriginalFilename(); //获取文件名
            String url = fileService.uploadFile(inputStream, module, originalFilename); //返回一个对应的url地址
            return R.ok().message("文件上传成功").data("url",url);
        } catch (Exception e) {
            throw new CollegeException(ResultCode.FILE_UPLOAD_ERROR);//抛出异常到自定义异常类
        }
    }

    /**
     * 删除文件
     * @param url
     * @return
     */
    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public R removeFile(@ApiParam(value = "需要删除的文件的url",required = true)
                            @RequestBody String url){
        fileService.removeFile(url);
       return R.ok().message("删除成功");
    }

    @GetMapping("/test")
    public R test(){
        return R.ok().message("成功");
    }
}
