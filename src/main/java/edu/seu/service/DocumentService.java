package edu.seu.service;

import edu.seu.base.CodeEnum;
import edu.seu.dao.DocumentDao;
import edu.seu.exceptions.OICPMPIEExceptions;
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

    private String currentPark;
    private String currentYear;
    private String currentInvest;
    private String currentUser;

    public String getCurrentUser() { return currentUser; }

    public void setCurrentUser(String currentUser) { this.currentUser = currentUser; }

    public String getCurrentPark() {
        return currentPark;
    }

    public void setCurrentPark(String currentPark) {
        this.currentPark = currentPark;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getCurrentInvest() {
        return currentInvest;
    }

    public void setCurrentInvest(String currentInvest) {
        this.currentInvest = currentInvest;
    }

    public void insertDocument(Document document) {
        documentDao.persist(document);
        setCurrentPark(document.getPark());
        setCurrentYear(document.getYear());
        setCurrentInvest(document.getInvest());
        setCurrentUser(document.getUser());
    }

    public List<Document> showDocument(String option, String park_year_invest, String userName) throws OICPMPIEExceptions {
        List<Document> documents = null;
        if (option.equals("park")) {
            documents = documentDao.showByPark(userName, park_year_invest);
        } else if (option.equals("year")) {
            documents = documentDao.showByYear(userName, park_year_invest);
        } else if (option.equals("invest")) {
            documents = documentDao.showByInvest(userName, park_year_invest);
        }

        if (documents == null) {
            throw new OICPMPIEExceptions(CodeEnum.DOCUMENT_ERROR, "未找到相关结果");
        }

        return documents;
    }

}
