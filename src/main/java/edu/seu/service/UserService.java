package edu.seu.service;

import edu.seu.base.CodeEnum;
import edu.seu.base.PageInfo;
import edu.seu.base.Pagination;
import edu.seu.dao.LoginTicketDAO;
import edu.seu.dao.UserDao;
import edu.seu.exceptions.OICPMPIEExceptions;
import edu.seu.model.LoginTicket;
import edu.seu.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String register(User user, String oldEmail, String codeCaptcha, String emailCaptcha,
                           String oldCodeCaptcha, String oldEmailCaptcha) throws OICPMPIEExceptions {
        checkBeforeRegister(user, oldEmail, codeCaptcha, emailCaptcha, oldCodeCaptcha, oldEmailCaptcha);

        userDao.persist(user);

        return addLoginTicket(user.getId());

    }

    public String login(String nameEmail, String password, String codeCaptcha, String oldCodeCaptcha) throws OICPMPIEExceptions {
        checkBeforeLogin(nameEmail, password, codeCaptcha, oldCodeCaptcha);

        User old1 = userDao.selectByName(nameEmail);
        User old2 = userDao.selectByEmail(nameEmail);
        User old = old1 != null ? old1 : old2;
        if (old == null) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "该用户名/邮箱尚未注册！");
        }
        if (!StringUtils.equals(old.getPassword(), password)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "密码错误！");
        }
        //设置当前用户
        setCurrentUser(old);
        return addLoginTicket(old.getId());
    }

    private void checkBeforeRegister(User user, String oldEmail, String codeCaptcha, String emailCaptcha,
                                     String oldCodeCaptcha, String oldEmailCaptcha) throws OICPMPIEExceptions {
        if (StringUtils.isBlank(user.getName())) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "用户名不能为空！");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "密码不能为空！");
        }
        if (StringUtils.isBlank(user.getEmail()) || !EmailService.isMail(user.getEmail())) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱格式错误！");
        }
        if (!StringUtils.equals(codeCaptcha, oldCodeCaptcha)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误！");
        }
        if (!StringUtils.equals(user.getEmail(), oldEmail)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱已更换，需要重新发送验证码！");
        }
        if (!StringUtils.equals(emailCaptcha, oldEmailCaptcha)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱验证码错误！");
        }
        User old = userDao.selectByName(user.getName());
        if (old != null) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "该用户名已被注册！");
        }
        old = userDao.selectByEmail(user.getEmail());
        if (old != null) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "该邮箱已被注册！");
        }

    }

    private void checkBeforeLogin(String nameEmail, String password, String codeCaptcha, String oldCodeCaptcha) throws OICPMPIEExceptions {

        if (StringUtils.isBlank(nameEmail)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "用户名/邮箱不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "密码不能为空！");
        }
        if (!StringUtils.equals(codeCaptcha, oldCodeCaptcha)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误！");
        }
    }

    public void checkBeforeEmailCaptcha(String email, String codeCaptcha, String oldCodeCaptcha) throws OICPMPIEExceptions {
        if (StringUtils.isBlank(email) || !EmailService.isMail(email)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱格式错误！");
        }
        if (!StringUtils.equals(codeCaptcha, oldCodeCaptcha)) {
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误！");
        }
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        // 一天过期
        date.setTime(date.getTime() + 3600*24*1000);
        ticket.setExpireTime(new Timestamp(date.getTime()));
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.persist(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket,1);
        setCurrentUser(null);
    }


    public Map<String, Object> buildEmailCaptcha(String email, String emailCaptcha, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("to", email);
        map.put("subject", "OICPMPIE-验证码");
        String text = "<html><p><h3>验证码：" + emailCaptcha + "</h3></p></html>";
        map.put("text", text);
        return map;
    }

    public void modifyPassword(String email, String newPassword, String confirmPassword, String codeCaptcha, String oldCodeCaptcha) throws OICPMPIEExceptions {
        checkBeforeModifyPassword(email, newPassword, confirmPassword, codeCaptcha, oldCodeCaptcha);

        userDao.updatePassword(email, newPassword);
    }

    private void checkBeforeModifyPassword(String email,String newPassword, String confirmPassword,
                                           String codeCaptcha, String oldCodeCaptcha) throws OICPMPIEExceptions {

        if(StringUtils.isEmpty(email)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱不能为空！");
        }
        if(!StringUtils.equals(newPassword, confirmPassword)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "输入密码不一致！");
        }
        if(!StringUtils.equals(codeCaptcha, oldCodeCaptcha)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误");
        }
    }

    /**
     * 验证找回密码页
     */
    public void checkBeforeFindPassword(String codeCaptcha, String oldCodeCaptcha, String emailCaptcha, String oldEmailCaptcha) throws OICPMPIEExceptions {
        if(!StringUtils.equals(codeCaptcha, oldCodeCaptcha)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误!");
        }
        if(!StringUtils.equals(emailCaptcha, oldEmailCaptcha)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "邮箱验证码错误!");
        }
    }

    public void checkEmail(String email) throws OICPMPIEExceptions {
        User user = userDao.selectByEmail(email);
        if(user == null){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "该邮箱尚未注册！");
        }
    }

    public void updatePassword(String oldPassword, String newPassword, String codeCaptcha,
                               String oldCodeCaptcha, String ticket) throws OICPMPIEExceptions {
        User user = userDao.selectByName(currentUser.getName());
        if(!StringUtils.equals(user.getPassword(), oldPassword)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "旧密码输入有误");
        }
        if(!StringUtils.equals(codeCaptcha, oldCodeCaptcha)){
            throw new OICPMPIEExceptions(CodeEnum.USER_ERROR, "验证码错误!");
        }
        userDao.updatePassword(user.getEmail(), newPassword);
        logout(ticket);

    }

    public User getUser(String name){
        return userDao.selectByName(name);
    }

    public void updateUserInfo(String name,String phoneNum,String sex,
                               String company,String address,String domain){
        User user = userDao.selectByName(name);
        user.setPhoneNum(phoneNum);
        user.setSex(sex);
        user.setCompany(company);
        user.setAddress(address);
        user.setDomain(domain);
        userDao.updateInfo(user);
    }

    //分页
    public Pagination<User> queryAllUser(Integer page) {

        if (page == null) {
            page = 1;
        }
        Integer totalRow = userDao.countAllUser();
        PageInfo pageInfo = new PageInfo(totalRow, page);
        List<User> userList = userDao.selectAll(pageInfo.getBeginIndex(), pageInfo.getEndIndex());
        return new Pagination<User>(userList, pageInfo);
    }
}
