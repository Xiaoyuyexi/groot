package com.groot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemPermissionDAO;
import com.groot.module.domain.GrootSystemConfig;
import com.groot.module.domain.GrootSystemPermission;
import com.groot.module.domain.GrootSystemPermissionExample;
@Transactional(readOnly=true)
@Service
public class PermissionManageService {

	@Autowired
	public SelectDAO selectDao;
	@Autowired
	public GrootSystemPermissionDAO systemPermissionDAO;
	
	@Autowired
	public ConfigService configService;
	/**
	 * 
	 * @return
	 */
	public List<GrootSystemPermission> getAllPermissionList(){
		GrootSystemPermissionExample exa = new GrootSystemPermissionExample();
		exa.createCriteria().andIdIsNotNull();
		exa.setOrderByClause("PERMISSION_CODE");
		return systemPermissionDAO.selectByExample(exa);
	}
	public List<GrootSystemPermission> getotherPermissionList(String id){
		GrootSystemPermissionExample exa = new GrootSystemPermissionExample();
		exa.createCriteria().andIdNotEqualTo(id);
		exa.setOrderByClause("PERMISSION_CODE");
		return systemPermissionDAO.selectByExample(exa);
	}
	/**
	 * ͨ��id��ѯָ��Ȩ��
	 * @param purviewId
	 * @return
	 */
	public GrootSystemPermission selectPermissionById(String permissionId){
		return systemPermissionDAO.selectByPrimaryKey(permissionId);
	}
	
	/**
	 * 新增
	 * @param purview
	 */
	@Transactional
	public void insertPermission(GrootSystemPermission systemPermission){
//		purview.setCreateDate(new Date(System.currentTimeMillis()));
		systemPermissionDAO.insert(systemPermission);
	}
	
	/**
	 * 更新
	 * @param purview
	 */
	@Transactional
	public void updatePermission(GrootSystemPermission systemPermission){
		systemPermissionDAO.updateByPrimaryKeySelective(systemPermission);
	}
	
	/**
	 * 删除
	 * @param personConfig
	 * @return
	 */
	@Transactional
	public int deletePermission(GrootSystemPermission systemPermission) {
		if(systemPermission.getId() !=null){
			GrootSystemPermissionExample example = new GrootSystemPermissionExample();
			example.createCriteria().andIdEqualTo(systemPermission.getId());
			 systemPermissionDAO.deleteByExample(example);
			 return 0;
		}
	    return -1;
	}
	/**
	 * 根据父级获取所有子级
	 * @param ParentId
	 * @return
	 */
	public List<GrootSystemPermission> getPermissionByParentId(String ParentId){
		GrootSystemPermissionExample example = new GrootSystemPermissionExample();
		example.createCriteria().andParentIdEqualTo(ParentId);
		return systemPermissionDAO.selectByExample(example);
	}
	/**
	 * 查询code是否存在
	 * @param code
	 * @return
	 */
	public boolean isCode(String code,String id){
		GrootSystemPermissionExample example = new GrootSystemPermissionExample();
		if(id==null || "".equals(id)){
			example.createCriteria().andPermissionCodeEqualTo(code);
		}else{
			example.createCriteria().andPermissionCodeEqualTo(code).andIdNotEqualTo(id);
		}
		List<GrootSystemPermissionExample> ls = systemPermissionDAO.selectByExample(example);
		if(ls!=null&&ls.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 能否新增
	 * @param parentId
	 * @return
	 */
	public boolean canInsert(String parentId){
		if(parentId==null||"".equals(parentId)){
			return false;
		}
		
		GrootSystemConfig config = configService.getSystemConfig("10000004");
		if(null != config){
			int code=Integer.parseInt(config.getConfigValue())*2  ;
			String codeLike="";
			for (int j = 0; j < code; j++) {
				codeLike=codeLike+"_";
			}
			String menu="00000000";
			menu=codeLike+menu.substring(code, menu.length());
			GrootSystemPermissionExample example = new GrootSystemPermissionExample();
			example.createCriteria().andIdEqualTo(parentId).andPermissionCodeLike(menu);
			List<GrootSystemPermissionExample> list = systemPermissionDAO.selectByExample(example);
			if(list!=null && list.size()>0){
				return true;
			}
		}
		return false;
	}
	
	public int getPermissionCountByRule(String ruleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ruleId", ruleId);
		return selectDao.count("SYSTEM.getPermissionListByRole", param);
	}
}
