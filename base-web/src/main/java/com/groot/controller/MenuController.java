package com.groot.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemMenu;
import com.groot.module.domain.GrootSystemPermission;
import com.groot.module.domain.GrootSystemUser;
import com.groot.service.MenuService;
import com.groot.vo.Constant;
import com.groot.vo.MenuVO;
import com.web.bo.AjaxMessage;

@Controller
public class MenuController {
	@Autowired
	public MenuService menuService;
	
	
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("MenuManager:menu")
	@RequestMapping(value = "/new_menu_manager")
	public ModelAndView new_menu_manager() {
		return new ModelAndView("jsp/menu_manager");
	}
	/**
	 * 获取全部组织机构
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllNewMenu", method = RequestMethod.POST)
	public List<GrootSystemMenu> getAllNewMenu() {
		return menuService.getAllSystemMenuNew();
	}
	/**
	 * 获取除选中节点之外的组织机构
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuBymenuId", method = RequestMethod.POST)
	public List<GrootSystemMenu> getMenuBymenuId(String id) {
		return menuService.getMenuBymenuId(id);
	}
	@RequiresPermissions("TableName:view")
	@ResponseBody
	@RequestMapping(value = "/getTableNameByMenuId", method = RequestMethod.POST)
	public GrootSystemMenu getTableNameByMenuId(String id) {
		return menuService.getTableNameByMenuId(id);
	}
	/**
	 * 更新或添加一个菜单
	 * @param department
	 * @return
	 */
	@RequiresPermissions("MenuManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveNewMenu", method = RequestMethod.POST)
	public AjaxMessage saveNewMenu(GrootSystemMenu menu) {
		AjaxMessage msg = new AjaxMessage();
		msg.setIsSuccess(false);
		
		
		
		
		if(menuService.isMenuCode(menu.getMenuCode(),menu.getId())){
			msg.setContent("菜单代码已存在");
			return msg;
		}
		if( !menuService.canInsert(menu.getParentId())){
			msg.setContent("非法操作");
			return msg;
		}
		try{
			if(StringUtils.isBlank(menu.getId())){
				menu = menuService.insert(menu);
				msg.setObj(menu);
			}else{
				menuService.update(menu);
			}
			msg.setContent("保存成功");
			msg.setIsSuccess(true);
			msg.setObj(menu);
		}catch (Exception e) {
			e.printStackTrace();
			msg.setContent("保存异常");
		}
		return msg;
	}
	/**
	 * 删除一个组织结构
	 * @param id
	 * @return
	 */
	@RequiresPermissions("MenuManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeNewMenu", method = RequestMethod.POST)
	public int removeNewMenu(String id){
		return menuService.removeByPrimaryKey(id);
	}
	@ResponseBody
	@RequestMapping(value = "/getMenuListByUserId",method = RequestMethod.POST)
	public MenuVO getMenuListByUserId() {
		GrootSystemUser user = (GrootSystemUser)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		if(user == null){
			return null;
		}
		return menuService.getMenuListByUserId(user.getId());
	}
	/**
	 * 获取菜单权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuPermissionList",method = RequestMethod.POST)
	public List<GrootSystemPermission> getMenuPermissionList() {
		return menuService.getMenuPermissionList();
	}
	/**
	 * 根据父级查询所有的子级
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuByParentId",method = RequestMethod.POST)
	public List<GrootSystemMenu>  getMenuByParentId(String parentId ){
		return menuService.getMenuByParentId(parentId);
	}
}
