package com.app.admin.controller;

import com.app.admin.service.AdminMenuService;
import com.app.core.common.ResultVo;
import com.app.core.constant.BaseDictConstant;
import com.app.core.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping("")
    public String defaultView() {
        return "redirect:admin/index";
    }

    @RequestMapping("/index")
    public String index() {
        return  "admin/index";
    }

    @RequestMapping(value = "/login",method= RequestMethod.GET)
    public String login() {
        return  "admin/login";
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes,HttpSession session ){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        session.removeAttribute(BaseDictConstant.LOGIN_USER);
        return "redirect:admin/login";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param verifyCode
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo ajaxLogin(String username,String password,String verifyCode ,HttpSession session) {
        ResultVo resultVo = new ResultVo();
        boolean pass = loginVerifyCode(resultVo,username,password,verifyCode,
                session.getAttribute(BaseDictConstant.VERIFY_CODE_KEY));
        if(!pass){
            return resultVo;
        }else{
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(usernamePasswordToken);   //完成登录
                User user=(User) subject.getPrincipal();
                session.setAttribute(BaseDictConstant.LOGIN_USER, user);
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
            }catch(AuthenticationException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                resultVo.setRetMessage(e.getMessage());
            } catch(Exception e) {
                logger.error(e.getMessage());
                resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
                resultVo.setRetMessage("登录失败，请稍后再试");
            }
            resultVo.pushDataMap("redirectUrl","admin/index");
        }
        return resultVo;
    }

    /**
     * 验证登录参数
     * @param resultVo
     * @param username
     * @param password
     * @param verifyCode
     * @param sessionVerifyCode
     * @return
     */
    private boolean loginVerifyCode(ResultVo resultVo,String username ,String password,String verifyCode ,Object sessionVerifyCode){
        if(StringUtils.isEmpty(username)){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("账号不能为空");
            return false;
        }
        if(StringUtils.isEmpty(password)){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("密码不能为空");
            return false;
        }
        /*if(StringUtils.isEmpty(verifyCode)){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("验证码不能为空");
            return false;
        }else if(StringUtils.isEmpty(sessionVerifyCode) ||
                (null!= sessionVerifyCode && !verifyCode.equalsIgnoreCase(String.valueOf(sessionVerifyCode)))){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
            resultVo.setRetMessage("验证码错误,请重新输入");
            return false;
        }*/
        return true;
    }

    /**
     * 获取菜单
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/sideMenu",method= RequestMethod.POST)
    @ResponseBody
    public ResultVo sideMenu(HttpServletRequest request, HttpSession session) {
        ResultVo resultVo = new ResultVo();
        try{
            resultVo.pushDataMap("menuList",adminMenuService.findAllMenu(false));
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_SUCCESS);
        }catch (Exception e){
            resultVo.setOperationFlag(ResultVo.OPERATION_FLAG_FAIL);
        }
        return resultVo;
    }
}
