package edu.seu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.seu.base.CodeEnum;
import edu.seu.base.CommonResponse;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.Document;
import edu.seu.service.DocumentService;
import edu.seu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Administrator
 * @date 19/10/27
 */
@Controller
@RequestMapping("/document")
public class DocumentController {
    public static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/insert")
    public void insert(MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response){
        Document document = new Document();
        document.setPark(request.getParameter("parkName"));
        document.setYear(request.getParameter("constructionYear"));
        document.setInvest(request.getParameter("investmentUnit"));
        document.setNation(request.getParameter("country"));
        document.setIndustry(request.getParameter("planningIndustry"));
        document.setSchedule(request.getParameter("planningScale"));
        document.setAssess(request.getParameter("evaluationScale"));
        document.setBuild(request.getParameter("constructionScale"));
        document.setConstruct(request.getParameter("constructionUnit"));
        document.setOperate(request.getParameter("operatingUnit"));
        document.setUser(request.getParameter("personName"));
        document.setCareer(request.getParameter("job"));
        document.setCompany(request.getParameter("workAddress"));
        document.setPhone(request.getParameter("phone"));
        document.setEmail(request.getParameter("email"));
        document.setAddress(request.getParameter("address"));

        try {
            // 判断文件是否成功上传
            if (imageFile != null) {
                // 指定上传图片的保存路径
                String path = request.getServletContext().getRealPath("/image/");
                // 获取上传的文件名全称
                String fileName = imageFile.getOriginalFilename();
                // 获取上传文件的后缀名
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                // 给文件定义一个新的名称,杜绝文件重名覆盖现象
                String newFileName = UUID.randomUUID().toString() + suffix;

                // 创建File对象,注意这里不是创建一个目录或一个文件,可以理解为是:获取指定目录中文件的管理权限(增改删除判断等 . . .)
                File tempFile = new File(path);
                // 判断File对象对应的目录是否存在
                if (!tempFile.exists()) {
                    // 创建以此抽象路径名命名的目录,注意mkdir()只能创建一级目录,所以它的父级目录必须存在
                    tempFile.mkdir();
                }
                // 在指定路径中创建一个文件(该文件是空的)
                File file = new File(path + newFileName);
                // 将上传的文件写入指定文件
                imageFile.transferTo(file);

                // 设置图片路径
                document.setImage(newFileName);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        documentService.insertDocument(document);
        try {
            response.sendRedirect(request.getContextPath()+"/informationSurvey.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return JSON.toJSONString("success");
    }

    @ResponseBody
    @RequestMapping("/showDocument")
    public String showDocument(HttpServletRequest request){
        try {
            String option = request.getParameter("option");
            String park_year_invest = request.getParameter("park_year_invest");
            String userName = userService.getCurrentUser().getName();

            List<Document> documents = documentService.showDocument(option,park_year_invest,userName);
            JSONArray array = new JSONArray();
            for (int i = 0; i < documents.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("park", documents.get(i).getPark());
                object.put("year", documents.get(i).getYear());
                object.put("invest", documents.get(i).getInvest());
                array.add(object);
            }
            return JSON.toJSONString(array.toString());
        } catch (OICPMPIEExceptions e){
            LOGGER.info(e.getMessage());
            return new CommonResponse(e.getCodeEnum().getValue(),e.getMessage()).toJSONString();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new CommonResponse(CodeEnum.USER_ERROR.getValue(),e.getMessage()).toJSONString();
        }
    }

}
