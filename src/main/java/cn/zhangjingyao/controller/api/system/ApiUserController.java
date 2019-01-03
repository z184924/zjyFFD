package cn.zhangjingyao.controller.api.system;

import cn.zhangjingyao.controller.base.BaseController;
import cn.zhangjingyao.entity.PageData;
import cn.zhangjingyao.service.dict.DangerService;
import cn.zhangjingyao.service.system.user.UserService;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/** 
 * 类名称：ProjectController
 * 创建时间：2018-08-08
 */
@Controller
@RequestMapping(value="/api/user")
public class ApiUserController extends BaseController {

	@Resource
	private UserService userService;
	@Autowired
	private IdentityService identityService;

	
	/**
	 * 新增
	 */
	@ResponseBody
	@RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
	public String save() throws Exception{
		logBefore(logger, "新增User");
		PageData pd = this.getPageData();
//		this.userService.save(pd);
//		for(int i=0;i<3;i++){
//			List<PageData> list =new ArrayList<>();
//			list.add(pd);
//			this.userService.save(list);
//		}
		User user = identityService.newUser(pd.getString("account"));
		identityService.saveUser(user);
		return this.jsonContent("success", "保存成功");
	}
	

}