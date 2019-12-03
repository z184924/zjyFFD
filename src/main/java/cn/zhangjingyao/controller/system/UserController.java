package cn.zhangjingyao.controller.system;

import cn.zhangjingyao.annotation.SystemLog;
import cn.zhangjingyao.controller.base.BaseController;
import cn.zhangjingyao.entity.PageData;
import cn.zhangjingyao.entity.system.User;
import cn.zhangjingyao.service.system.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 类名称：UserController
 * 创建时间：2019-02-26
 *
 * @author
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 新增或编辑
     */
    @SystemLog("新增或编辑User")
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", produces = "application/json;charset=UTF-8")
    public String saveOrUpdate() {
        logger.info("新增或编辑User");
        PageData pd = this.getPageData();
        if (pd.get("userId") == null || "".equals(pd.get("userId"))) {
            //添加主键
            pd.put("userId", this.get32UUID());
            //替换字段
            pd = this.replaceAttribute(pd);
            boolean saveFlag = this.userService.save(pd);
            if (!saveFlag) {
                return this.jsonContent("error", "已存在该账号");
            }
        } else {
            //替换字段
            pd = this.replaceAttribute(pd);
            this.userService.edit(pd);
        }
        return this.jsonContent("success", "保存成功");

    }

    /**
     * 新增
     */
    @SystemLog("新增User")
    @ResponseBody
    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    public String save() {
        logger.info("新增User");
        PageData pd = this.getPageData();
        //添加主键
        pd.put("userId", this.get32UUID());
        //替换字段
        pd = this.replaceAttribute(pd);
        boolean saveFlag = this.userService.save(pd);
        if (saveFlag) {
            return this.jsonContent("success", "保存成功");
        } else {
            return this.jsonContent("error", "已存在该账号");
        }
    }

    /**
     * 删除
     */
    @SystemLog("删除User")
    @ResponseBody
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public String delete() {
        logger.info("删除User");
        PageData pd = this.getPageData();
        userService.delete(pd);
        return this.jsonContent("success", "删除成功");
    }

    /**
     * 修改
     */
    @SystemLog("修改User")
    @ResponseBody
    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    public String edit() {
        logger.info("修改User");
        PageData pd = this.getPageData();
        //替换字段
        pd = this.replaceAttribute(pd);
        this.userService.edit(pd);
        return this.jsonContent("success", "保存成功");
    }

    /**
     * 分页查询列表
     */
    @SystemLog("分页查询User列表")
    @ResponseBody
    @RequestMapping(value = "/listPage", produces = "application/json;charset=UTF-8")
    public Object listPage() {
        PageData pd = this.getPageData();
        // 分页查询列表
        PageInfo<PageData> pageInfo = this.userService.listPage(pd);
        return this.jsonContent("success", pageInfo);
    }

    /**
     * 根据ID查询单条数据
     */
    @SystemLog("根据ID查询User数据")
    @RequestMapping(value = "/findById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object findById() {
        PageData pd = this.getPageData();
        PageData resultPD = this.userService.findById(pd);
        return this.jsonContent("success", resultPD);
    }

    /**
     * 修改密码
     */
    @SystemLog("修改密码")
    @ResponseBody
    @RequestMapping(value = "/editPassword", produces = "application/json;charset=UTF-8")
    public String editPassword() {
        User currentUser = this.getCurrentUser();
        logger.info("用户:" + currentUser.getAccount() + "修改密码");
        PageData oldUser = this.userService.findById(currentUser.getUserId());
        PageData pd = this.getPageData();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(pd.getString("oldPassword"), oldUser.getString("password"))) {
            oldUser.put("password", bCryptPasswordEncoder.encode(pd.getString("newPassword")));
            this.userService.edit(pd);
            return this.jsonContent("success", "修改成功");
        } else {
            return this.jsonContent("error", "旧密码错误");
        }
    }

    /**
     * 替换字段
     *
     * @param pd
     * @return 替换后PageData
     */
    private PageData replaceAttribute(PageData pd) {
        if ("true".equals(pd.getString("locked"))) {
            pd.put("locked", true);
        } else {
            pd.put("locked", false);
        }
        if ("true".equals(pd.getString("disable"))) {
            pd.put("disable", true);
        } else {
            pd.put("disable", false);
        }

        //密码加密
        String password = new BCryptPasswordEncoder().encode(pd.getString("password"));
        pd.put("password", password);
        return pd;
    }

}
