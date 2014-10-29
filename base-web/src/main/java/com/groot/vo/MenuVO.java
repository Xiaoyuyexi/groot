package com.groot.vo;

import java.util.List;

import com.groot.module.domain.GrootSystemMenu;


public class MenuVO{
	
	private List<GrootSystemMenu> menuContent;
	private List<GrootSystemMenu> menuItemContent;
	public List<GrootSystemMenu> getMenuContent() {
		return menuContent;
	}
	public void setMenuContent(List<GrootSystemMenu> menuContent) {
		this.menuContent = menuContent;
	}
	public List<GrootSystemMenu> getMenuItemContent() {
		return menuItemContent;
	}
	public void setMenuItemContent(List<GrootSystemMenu> menuItemContent) {
		this.menuItemContent = menuItemContent;
	}
}
