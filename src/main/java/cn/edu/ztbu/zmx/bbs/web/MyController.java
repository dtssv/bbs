package cn.edu.ztbu.zmx.bbs.web;

import cn.edu.ztbu.zmx.bbs.domain.User;
import cn.edu.ztbu.zmx.bbs.service.UserService;
import cn.edu.ztbu.zmx.bbs.util.LoginContext;
import cn.edu.ztbu.zmx.bbs.vo.ResultVo;
import cn.edu.ztbu.zmx.bbs.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Objects;

/**
 * @program bbs.MyController
 * @author: zhaomengxin
 * @date: 2021/1/10 12:19
 * @Description:
 */
@Controller
@RequestMapping(value = "my")
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Value("${image.upload}")
    private String uploadDir;
    /**
     * 新增/编辑文章
     */
    @RequestMapping(value = "add")
    public String add(){
        return "/post/add";
    }
    /**
     * 主页
     */
    @RequestMapping(value = "home")
    public String home(){
        User user = LoginContext.getLoginUser();
        return "redirect:/user/home?userId=" + user.getId();
    }
    /**
     * 我的设置
     */
    @RequestMapping(value = "set")
    public String set(){
        return "/user/set";
    }

    @ResponseBody
    @RequestMapping("detail")
    public ResultVo<User> detail(){
        User user = LoginContext.getLoginUser();
        if(user != null){
            User userInfo = userService.getById(user.getId());
            return ResultVo.success(userInfo);
        } else{
            return ResultVo.fail("未登录");
        }
    }

    @ResponseBody
    @RequestMapping("updateBasicInfo")
    public ResultVo updateBasicInfo(UserVo userVo){
        User user = LoginContext.getLoginUser();
        if(user != null){
            userVo.setId(user.getId());
            userVo.setUserName(user.getUsername());
            user = userService.saveBasic(userVo);
            reLogin(user);
            return ResultVo.success("");
        } else{
            return ResultVo.fail("未登录");
        }
    }

    @ResponseBody
    @RequestMapping("changePassword")
    public ResultVo changePassword(String nowPass,String newPass){
        User user = LoginContext.getLoginUser();
        if(user != null){
            ResultVo<User> resultVo = userService.changePassword(nowPass,newPass,user.getId(),user.getUsername());
            if(resultVo.isSuccess()){
                reLogin(resultVo.getData());
                return ResultVo.success("");
            }
            return ResultVo.fail(resultVo.getMsg());
        } else{
            return ResultVo.fail("未登录");
        }
    }

    @ResponseBody
    @RequestMapping("changeHeadImage")
    public ResultVo<String> changeHeadImage(@RequestParam("file") MultipartFile file,HttpServletRequest req){
        User user = LoginContext.getLoginUser();
        if(Objects.isNull(user) || Objects.isNull(file)){
            return  ResultVo.fail("上传失败");
        }
        try {
            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
            String destFileName = File.separator + "uploaded" + File.separator + fileName;
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(uploadDir + destFileName);
            destFile.getParentFile().mkdirs();
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
            user = userService.changeHeadImage(destFileName,user.getId(),user.getUsername());
            reLogin(user);
            return ResultVo.success(destFileName);
        } catch (Exception e) {
            return  ResultVo.fail("上传失败");
        }
    }

    private Boolean reLogin(User user){
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
        return  Boolean.TRUE;
    }
}
