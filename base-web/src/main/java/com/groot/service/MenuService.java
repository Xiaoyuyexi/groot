package com.groot.service;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemMenuDAO;
import com.groot.module.dao.GrootSystemPermissionDAO;
import com.groot.module.domain.GrootSystemConfig;
import com.groot.module.domain.GrootSystemMenu;
import com.groot.module.domain.GrootSystemMenuExample;
import com.groot.module.domain.GrootSystemPermission;
import com.groot.module.domain.GrootSystemPermissionExample;
import com.groot.module.domain.GrootSystemUser;
import com.groot.vo.Constant;
import com.groot.vo.MenuVO;
import com.web.utils.lang.StringUtils;
@Transactional(readOnly=true)
@Service
public class MenuService {
	@Autowired
	public GrootSystemMenuDAO menuNewDao;
//	@Autowired
//	public BusiMenuDAO busiMenuDAO;
	@Autowired
	public GrootSystemPermissionDAO systemPermissionDAO;
//	@Autowired
//	public LookUpCdeDAO  lookUpDao;
	@Autowired
	public SelectDAO selectDao;
	
	@Autowired
	public ConfigService configService;
	/**
	 * 获取全部权限
	 * @return
	 */
	public List<GrootSystemPermission> getPermissionList(){
		return systemPermissionDAO.selectByExample(null);
	}
	/**
	 * 获取菜单权限
	 * @return
	 */
	public List<GrootSystemPermission> getMenuPermissionList(){
		GrootSystemPermissionExample example = new GrootSystemPermissionExample();
		example.createCriteria().andPermissionCodeLike("00____00").andPermissionKeyIsNotNull();
		return systemPermissionDAO.selectByExample(example);
	}
	public MenuVO getMenuListByUserId(String id) {
		List<GrootSystemMenu> menuContent = selectDao.list("SYSTEM.getMenuContentByUserId", id);
		List<GrootSystemMenu> menuItemContent = selectDao.list("SYSTEM.getMenuItemContentByUserId", id);
//		List<GrootSystemMenu> menuContent = busiMenuDAO.getMenuContentByUserId(id);
//		List<GrootSystemMenu> menuItemContent = busiMenuDAO.getMenuItemContentByUserId(id);
		
		MenuVO menuVO = new MenuVO();
		menuVO.setMenuContent(menuContent);
		menuVO.setMenuItemContent(menuItemContent);
		return menuVO;
	}
	public List<GrootSystemMenu> getAllSystemMenuNew(){
		GrootSystemMenuExample example = new GrootSystemMenuExample();
		example.setOrderByClause("menu_code");
		return menuNewDao.selectByExample(example);
	}
	public List<GrootSystemMenu> getMenuBymenuId(String id){
		GrootSystemMenuExample example = new GrootSystemMenuExample();
		example.createCriteria().andIdNotEqualTo(id);
		example.setOrderByClause("menu_code");
		return menuNewDao.selectByExample(example);
	}
	
	public GrootSystemMenu getTableNameByMenuId(String id){
		GrootSystemMenuExample example = new GrootSystemMenuExample();
		example.createCriteria().andIdEqualTo(id);
		List<GrootSystemMenu> menu = menuNewDao.selectByExample(example);
		if(menu.size()>0){
			return menu.get(0);
		}
		return null;
	}
 	/**
	 * 插入新数据
	 * @param newMenu
	 * @return
	 */
	@Transactional
	public GrootSystemMenu insert(GrootSystemMenu newMenu) {
		newMenu.setId(StringUtils.randomUUID());
		GrootSystemUser user=(GrootSystemUser) SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		newMenu.setCreateTime(new Date());
		newMenu.setCreateUserId(user.getId());
		newMenu.setUpdateTime(new Date());
		newMenu.setUpdateUserId(user.getId());
		newMenu.setStatus("10A");
		menuNewDao.insert(newMenu);
		return newMenu;
	}
	/**
	 * 更新数据
	 * @param newMenu
	 * @return
	 */
	@Transactional
	public int update(GrootSystemMenu  newMenu) {
		GrootSystemUser user=(GrootSystemUser) SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		newMenu.setUpdateTime(new Date());
		newMenu.setUpdateUserId(user.getId());
		return menuNewDao.updateByPrimaryKeySelective(newMenu);
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeByPrimaryKey(String id) {
		return menuNewDao.deleteByPrimaryKey(id);

	}
	
	public boolean canInsert(String parentId){
		if(parentId==null||"".equals(parentId)){
			return false;
		}
		
		GrootSystemConfig config = configService.getSystemConfig("10000004");
		if(null != config){
			int menuCode=Integer.parseInt(config.getConfigValue())*2  ;
			String menuCodeLike = "";
			for (int j = 0; j < menuCode; j++) {
				menuCodeLike = menuCodeLike+"_";
			}
			String menu = "0000000000000000";
			menu = menuCodeLike + menu.substring(menuCode, menu.length());
			GrootSystemMenuExample example = new GrootSystemMenuExample();
			example.createCriteria().andIdEqualTo(parentId).andMenuCodeLike(menu);
			@SuppressWarnings("unchecked")
			List<GrootSystemMenu> list = menuNewDao.selectByExample(example);
			if(list!=null && list.size()>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据父级查询所有的子级
	 * @param parentId
	 * @return
	 */
	public List<GrootSystemMenu>  getMenuByParentId(String parentId ){
		GrootSystemMenuExample example = new GrootSystemMenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return menuNewDao.selectByExample(example);
	}
	/**
	 * 查询code是否存在
	 * @param menuCode
	 * @return
	 */
	public boolean isMenuCode(String menuCode,String id){
		GrootSystemMenuExample example = new GrootSystemMenuExample();
		if(id==null || "".equals(id)){
			example.createCriteria().andMenuCodeEqualTo(menuCode);
		}else{
			example.createCriteria().andMenuCodeEqualTo(menuCode).andIdNotEqualTo(id);
		}
		List<GrootSystemMenu> ls = menuNewDao.selectByExample(example);
		if(ls!=null&&ls.size()>0){
			return true;
		}
		return false;
	}
}
