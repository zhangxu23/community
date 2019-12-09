package my.comunity.common.controller;

import lombok.extern.slf4j.Slf4j;
import my.comunity.common.dto.FileDTO;
import my.comunity.common.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by codedrinker on 2019/6/26.
 */
@Controller
@Slf4j
public class FileController {
    @Autowired
    UCloudProvider uCloudProvider;
    private String path;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            path=uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/img/"+path);
        return fileDTO;
    }
}
