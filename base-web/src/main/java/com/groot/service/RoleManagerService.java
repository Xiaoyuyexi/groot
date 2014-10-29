package com.groot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.BatchBaseDAO;
import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemRoleDAO;
import com.groot.module.dao.GrootSystemRolePermissionDAO;
import com.groot.module.domain.GrootSystemRole;
import com.groot.module.domain.GrootSystemRoleExample;
import com.groot.module.domain.GrootSystemRolePermission;
import com.groot.module.domain.GrootSystemRolePermissionExample;
import com.groot.module.domain.GrootSystemUser;
import com.groot.vo.RolePermissionVO;

@Transactional(readOnly=true)
@Service
public class RoleManagerService {

	@Autowired
	public GrootSystemRoleDAO systemRoleDAO;
//	@Autowired
//	public RolePermissionDAO rolePermissionDAO;
	@Autowired
	public GrootSystemRolePermissionDAO systemRolePermissionDAO;
	@Autowired
	public ShiroDbRealm shiroDbRealm;
	@Autowired
	public SelectDAO selectDao;
	@Autowired
	public BatchBaseDAO batchInsertDAO;
	/**
	 * ��ѯ���н�ɫ
	 * 
	 * @return
	 */
	public List<GrootSystemRole> getRoleList() {
		GrootSystemRoleExample roleExample = new GrootSystemRoleExample();
		roleExample.setOrderByClause("ID");
		return systemRoleDAO.selectByExample(roleExample);
	}
	/**
	 * ���ID��ѯ��ɫ
	 * 
	 * @param id
	 * @return
	 */
	public GrootSystemRole getRoleByPrimaryKey(String id) {
		return systemRoleDAO.selectByPrimaryKey(id);
	}
	/**
	 *���½�ɫ
	 * 
	 * @param priceSpiderUser
	 */
	@Transactional
	public int update(GrootSystemRole systemRole) {
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
		return systemRoleDAO.updateByPrimaryKeySelective(systemRole);
	}
	/**
	 * �����ɫ
	 * 
	 * @param priceSpiderUser
	 */
	@Transactional
	public int insert(GrootSystemRole systemRole) {
		systemRoleDAO.insert(systemRole);
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
		return 1;
	}

	/**
	 *  ɾ���ɫ
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeRoleById(String id) {
//		roleCustomDAO.deletePurviewRoleRefByRoleId(id);
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
		return systemRoleDAO.deleteByPrimaryKey(id);
	}

	/**
	 * ���roleIdɾ��˽�ɫ���� ��Ȩ�� ��Ϣ
	 * @param userId
	 * @return
	 */
//	public int deletePurviewRoleRefByRoleId(String roleId){
//		return roleCustomDAO.deletePurviewRoleRefByRoleId(roleId);
//	}
	/**
	 * ͨ��id��ѯָ����ɫ
	 * @param purviewId
	 * @return
	 */
//	public PriceSpiderRole selectRoleById(String roleId){
//		return priceSpiderRoleDAO.selectByPrimaryKey(roleId);
//	}

	/**
	 * �����ɫ���õ�Ȩ��
	 * @param purview
	 */
	@Transactional
	public void insertRolePermission(RolePermissionVO rolePermissionVO){
		GrootSystemRolePermission rolePermission = new GrootSystemRolePermission();
		rolePermission.setId(com.web.utils.lang.StringUtils.randomUUID());
		rolePermission.setPermissionId(rolePermissionVO.getPermissionId());
		rolePermission.setRoleId(rolePermissionVO.getRoleId());
		rolePermissionVO.setRolePermissionId(rolePermission.getId());
		systemRolePermissionDAO.insert(rolePermission);
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
	}
	
	/**
	 * 批量插入
	 * @param roleId
	 * @param rolePermissionIds
	 * @return
	 */
	@Transactional
	public List<GrootSystemRolePermission> batchInsert(String roleId,String rolePermissionIds){
		List<GrootSystemRolePermission> list = new ArrayList<GrootSystemRolePermission>();
		for(String permissionId :rolePermissionIds.split(";")){
			GrootSystemRolePermission rolePermission = new GrootSystemRolePermission();
			GrootSystemRolePermissionExample exm = new GrootSystemRolePermissionExample();
			exm.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdEqualTo(permissionId);
			systemRolePermissionDAO.deleteByExample(exm);
			rolePermission.setId(com.web.utils.lang.StringUtils.randomUUID());
			rolePermission.setPermissionId(permissionId);
			rolePermission.setRoleId(roleId);
			list.add(rolePermission);
		}
		batchInsertDAO.batchExecute(list, "GROOT_SYSTEM_ROLE_PERMISSION.ibatorgenerated_insert", 500);
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
		return list;
	}
	
	/**
	 * 批量删除
	 * @param roleId
	 * @param rolePermissionId
	 * @return
	 */
	@Transactional
	public String batchDelete(String rolePermissionId){
		List<GrootSystemRolePermission> list = new ArrayList<GrootSystemRolePermission>();
		for(String o :rolePermissionId.split(";")){
			GrootSystemRolePermission rolePermission = new GrootSystemRolePermission();
			GrootSystemRolePermissionExample exm = new GrootSystemRolePermissionExample();
			rolePermission = systemRolePermissionDAO.selectByPrimaryKey(o);
			String roleId = rolePermission.getRoleId();
			String permissionId = rolePermission.getPermissionId();
			exm.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdEqualTo(permissionId);
			systemRolePermissionDAO.deleteByExample(exm);
			rolePermission.setId(o);
			list.add(rolePermission);
		}
		batchInsertDAO.batchExecute(list, "GROOT_SYSTEM_ROLE_PERMISSION.ibatorgenerated_deleteByPrimaryKey", 500);
		shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
		return "1";
	}
	/**
	 * ���½�ɫ���õ�Ȩ��
	 * @param purview
	 */
//	public void updateRolePurview(PurviewRoleRef ref){
//		purviewRoleRefDAO.updateByPrimaryKeySelective(ref);
//	}
	
	/**
	 * ���IDɾ���ɫ���õ�Ȩ��
	 * @param personConfig
	 * @return
	 */
	@Transactional
	public int removeRolePermission(RolePermissionVO rolePermissionVO) {
		if(StringUtils.isNotBlank(rolePermissionVO.getRolePermissionId())){
			shiroDbRealm.clearAllCachedAuthorizationInfo();//删除shiro缓存
			return systemRolePermissionDAO.deleteByPrimaryKey(rolePermissionVO.getRolePermissionId());
		}
	    return -1;
	}
	
	/**
	 * ��ѯ��ɫ�������õ�Ȩ��
	 * 
	 * @return
	 */
	public List<RolePermissionVO> getRolePermissionList(Map conditions) {
		
		return selectDao.list("SYSTEM.getRolePermission", conditions);
//		return rolePermissionDAO.getRolePermissionList(conditions);
	}

	/**
	 * ���roleId��ѯ����û�����õ�Ȩ��
	 */
	public List getPermissionListNoSitedByRoleId(Map conditions) {
		
		return selectDao.list("SYSTEM.getPermissionListNoSited", conditions);
//		return rolePermissionDAO.getPermissionListNoSitedByRoleId(conditions);
	}
	
	
//	public PriceSpiderRoleDAO getPriceSpiderRoleDAO() {
//		return priceSpiderRoleDAO;
//	}

//
//	public void setPriceSpiderRoleDAO(PriceSpiderRoleDAO priceSpiderRoleDAO) {
//		this.priceSpiderRoleDAO = priceSpiderRoleDAO;
//	}
//	public PurviewRoleRefDAO getPurviewRoleRefDAO() {
//		return purviewRoleRefDAO;
//	}
//	public void setPurviewRoleRefDAO(PurviewRoleRefDAO purviewRoleRefDAO) {
//		this.purviewRoleRefDAO = purviewRoleRefDAO;
//	}
//	public RoleCustomDAO getRoleCustomDAO() {
//		return roleCustomDAO;
//	}
//	public void setRoleCustomDAO(RoleCustomDAO roleCustomDAO) {
//		this.roleCustomDAO = roleCustomDAO;
//	}
	public GrootSystemUser selectSystemUser(String loginName ,String password){ 
		Map  user=new HashMap<String, String>();
		user.put("loginName", loginName);
		user.put("password",password);
		
		return (GrootSystemUser) selectDao.object("SYSTEM.selectSystemUser", user);
		
//		return rolePermissionDAO.selectHkrtSystemUser(user);
	}
	/**
	 * 查询拥有该权限的所有用户
	 * @param permissionId
	 * @return
	 */
	public List<GrootSystemRolePermission> selectRoleNameByPermissionId(String permissionId) {
		
		return selectDao.list("SYSTEM.selectRoleNameByPermissionId", permissionId);
//		return rolePermissionDAO.selectRoleNameByPermissionId(permissionId);
	}
	
	/**
	 * 检查角色名称是否存在
	 */
	public boolean checkRoleNameIsExetis(GrootSystemRole role) {
		GrootSystemRoleExample e = new GrootSystemRoleExample();
		com.groot.module.domain.GrootSystemRoleExample.Criteria c = e.createCriteria().andNameEqualTo(role.getName());
		if (StringUtils.isNotEmpty(role.getId())) {
			c.andIdNotEqualTo(role.getId());
		}
		int num = systemRoleDAO.countByExample(e);
		if (num > 0) {
			return true;
		}
		return false;
	}
}
