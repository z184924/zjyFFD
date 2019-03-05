package cn.zhangjingyao.service.system;

import cn.zhangjingyao.dao.DaoImpl;
import cn.zhangjingyao.entity.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@org.springframework.stereotype.Service
public class RoleService {

	@Autowired
	private DaoImpl dao;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void save(PageData pd)throws Exception{
		dao.save("RoleMapper.save", pd);
	}

	/**
	 * 批量新增
	 * @param list
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void save(List<PageData> list)throws Exception{
		for (PageData pd:list) {
			dao.save("RoleMapper.save", pd);
		}
	}

    /**
     * 删除
     * @param pd
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(PageData pd)throws Exception{
		dao.delete("RoleMapper.delete", pd);
	}

    /**
     * 批量删除
     * @param list
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(List<PageData> list)throws Exception{
        for (PageData pd:list) {
			dao.delete("RoleMapper.delete", pd);
		}
	}

    /**
     * 修改
     * @param pd
     * @throws Exception
     */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void edit(PageData pd)throws Exception{
		dao.update("RoleMapper.edit", pd);
	}

	/**
	 * 批量修改
     * @param list
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void edit(List<PageData> list)throws Exception{
        for (PageData pd:list) {
			dao.update("RoleMapper.edit", pd);
		}
	}

    /**
	 * 分页列表
     * @param page
     * @return
     * @throws Exception
     */
	public PageInfo<PageData> listPage(PageData pd)throws Exception{
        PageHelper.startPage(pd.getInt("pageNum"),pd.getInt("pageSize"));
        List<PageData> list = (List<PageData>) dao.findForList("RoleMapper.listAll", pd);
        return new PageInfo(list);
	}

    /**
     * 列表(全部)
     * @param pd
     * @return
     * @throws Exception
     */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RoleMapper.listAll", pd);
	}

     /**
      * 通过id获取数据
      * @param pd
      * @return
      * @throws Exception
      */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoleMapper.findById", pd);
	}

     /**
      * 批量删除
      * @param ArrayDATA_IDS
      * @throws Exception
      */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RoleMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 列表用户角色关系
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUserRole(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RoleMapper.listUserRole", pd);
	}

	/**
	 * 编辑用户角色关系
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void editUserRole(String roleId,List<PageData> list)throws Exception{
		dao.delete("RoleMapper.deleteUserRole", roleId);
		for (PageData pd:list) {
			dao.save("RoleMapper.saveUserRole", pd);
		}
	}

	/**
	 * 列表角色权限关系
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listRoleRights(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RoleMapper.listRoleRights", pd);
	}

	/**
	 * 编辑角色权限关系
	 * @param pd
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void editRoleRights(String roleId,List<PageData> list)throws Exception{
		dao.delete("RoleMapper.deleteRoleRights", roleId);
		for (PageData pd:list) {
			dao.save("RoleMapper.saveRoleRights", pd);
		}
	}


}

