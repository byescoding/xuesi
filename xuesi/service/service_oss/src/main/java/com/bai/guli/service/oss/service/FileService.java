package com.bai.guli.service.oss.service;

import java.io.InputStream;

public interface FileService {
    /**
     *
     * @param inputStream  文件的输入流
     * @param module  文件夹的名字
     * @param OriginFileName 文件名
     * @return  对应的文件的url的地址
     */
    public String uploadFile(InputStream inputStream,String module,String OriginFileName);

    /**
     * 删除对应url  的文件
     * @param url
     */
    public void removeFile(String url);
}
