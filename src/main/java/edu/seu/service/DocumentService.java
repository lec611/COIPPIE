package edu.seu.service;

import edu.seu.base.CodeEnum;
import edu.seu.dao.DocumentDao;
import edu.seu.exceptions.COIPPIEExceptions;
import edu.seu.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjx
 * @date 19/10/27
 */
@Service
public class DocumentService {
    @Autowired
    private DocumentDao documentDao;

    public void insertDocument(Document document){
        documentDao.persist(document);
    }

    public List<Document> showDocument(String option,String park_year_invest,String userName) throws COIPPIEExceptions {
        List<Document> documents = null;
        if(option.equals("park")){
            documents = documentDao.showByPark(userName,park_year_invest);
        }
        else if(option.equals("year")){
            documents = documentDao.showByYear(userName,park_year_invest);
        }
        else if(option.equals("invest")){
            documents = documentDao.showByInvest(userName,park_year_invest);
        }

        if(documents == null){
            throw new COIPPIEExceptions(CodeEnum.DOCUMENT_ERROR,"未找到相关结果");
        }

        return documents;
    }

}
