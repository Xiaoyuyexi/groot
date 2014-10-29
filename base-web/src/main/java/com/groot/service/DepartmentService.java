package com.groot.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemDepartmentDAO;
import com.groot.module.domain.GrootSystemDepartment;
import com.groot.module.domain.GrootSystemDepartmentExample;
import com.groot.module.domain.GrootSystemUser;
import com.groot.vo.Constant;

@Transactional(readOnly=true)
@Service
public class DepartmentService {
	@Autowired
	public GrootSystemDepartmentDAO departmentDao;
	@Autowired
	public SelectDAO selectDao;
	
	public List<GrootSystemDepartment> getAllDepartment(){
		GrootSystemDepartmentExample exa = new GrootSystemDepartmentExample();
		exa.createCriteria().andStatusEqualTo("10A");
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> list = departmentDao.selectByExample(exa);
		return list;
	}
 	/**
	 * 插入新数据
	 * @param department
	 * @return
	 */
	@Transactional
	public GrootSystemDepartment insert(GrootSystemDepartment department) {
		department.setId(com.web.utils.lang.StringUtils.randomUUID());
		Date createTime = new Date();
		GrootSystemUser user = (GrootSystemUser) SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		department.setCreateTime(createTime);
		department.setCreateUserId(user.getId());
		department.setStatus("10A");
		departmentDao.insert(department);
		return department;
	}
	/**
	 * 更新数据
	 * @param department
	 * @return
	 */
	@Transactional
	public GrootSystemDepartment update(GrootSystemDepartment department) {
		department.setUpdateTime(new Date());
		departmentDao.updateByPrimaryKeySelective(department);
		return department;
	}

	/**
	 * 更新数据
	 * @param department
	 * @return
	 */
	@Transactional
	public GrootSystemDepartment get(String id) {
		return departmentDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeByPrimaryKey(String id) {
		return departmentDao.deleteByPrimaryKey(id);

	}
	/**
	 * 根据登陆 用户获取用户对应的code
	 * @return
	 */
	public String getDepartmentBuUserid(){
		GrootSystemUser user = (GrootSystemUser)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
		example.createCriteria().andUserIdEqualTo(user.getId());
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> ls = departmentDao.selectByExample(example);
		if(ls != null && ls.size() > 0){
			return ls.get(0).getDepartmentCode();
		}
		return null;
	}
	
	public String getDepartmentCodeByUserId(String userId) {
		GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
		example.createCriteria().andUserIdEqualTo(userId);
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> ls = departmentDao.selectByExample(example);
		if(CollectionUtils.isNotEmpty(ls)){
			return ls.get(0).getDepartmentCode();
		}
		return null;
	}
	
	public GrootSystemDepartment getDepartmentByUserId(String userId) {
		GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
		example.createCriteria().andUserIdEqualTo(userId);
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> ls = departmentDao.selectByExample(example);
		if(CollectionUtils.isNotEmpty(ls)){
			return ls.get(0);
		}
		return null;
	}
	
	/**
	 * 根据父级查询所有的子级
	 * @param parentId
	 * @return
	 */
	public List<GrootSystemDepartment>  getDepartmentByParentId(String parentId ){
		GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> list = departmentDao.selectByExample(example);
		return list;
	}
	
	/**
	 * 查询此用户是否已使用
	 * @param userId
	 * @return
	 */
	public boolean isUserId(String userId,String id){
		if(StringUtils.isNotBlank(userId)){
			
			GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
			if(StringUtils.isBlank(id)){
				example.createCriteria().andUserIdEqualTo(userId);
			}else{
				example.createCriteria().andUserIdEqualTo(userId).andIdNotEqualTo(id);
			}
			@SuppressWarnings("unchecked")
			List<GrootSystemDepartment> ls = departmentDao.selectByExample(example);
			if(ls != null && ls.size() > 0){
				return true;
			}
		}
		return false;
	}
	
	public List<GrootSystemDepartment> getDepartmentByCode(String code) {
		GrootSystemDepartmentExample example = new GrootSystemDepartmentExample();
		example.createCriteria().andDepartmentCodeEqualTo(code);
		@SuppressWarnings("unchecked")
		List<GrootSystemDepartment> ls = departmentDao.selectByExample(example);
		return ls;
	}
}
