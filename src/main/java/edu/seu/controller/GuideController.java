package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import edu.seu.base.CodeEnum;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.User;
import edu.seu.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author lec,wjx
 * @date 2019/10/11
 */
@RequestMapping("/guideFile")
@Controller
public class GuideController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuideController.class);

    @Autowired
    private UserService userService;

    /**
     * 是否管理员登录
     */
    public void adminAuth() throws OICPMPIEExceptions {
        User user = userService.getCurrentUser();
        if (user == null || user.getIsAdmin() != 1) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "此操作需要管理员权限！");
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String uploadGuide(MultipartFile file, HttpServletRequest request) {
        try {
            //判断是否有管理员权限
            adminAuth();

            String filename = file.getOriginalFilename();
            System.out.println(filename);
            ServletContext context = request.getSession().getServletContext();
            String realPath = context.getRealPath("/file");
            System.out.println(realPath);

            if(!"pdf".equals(FilenameUtils.getExtension(filename))){
                System.out.println("非PDF文件");
                return JSON.toJSONString("File is not PDF!");
            }
            File mkdir = new File(realPath);
            if(!mkdir.exists()) {
                mkdir.mkdirs();
            }

            File f = new File(realPath,"使用说明书.pdf");
            file.transferTo(f);

            return JSON.toJSONString("success");
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return JSON.toJSONString("error");
        }
    }

    @ResponseBody
    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadGuide(HttpServletRequest request) throws IOException {
        ServletContext context = request.getSession().getServletContext();
        String realPath = context.getRealPath("/file/使用说明书.pdf");
        File file = new File(realPath);

        if(file.exists())
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDispositionFormData("attachment",URLEncoder.encode(file.getName(), "UTF-8"));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),httpHeaders, HttpStatus.OK);
        }else{
            return null;
        }

    }
}
