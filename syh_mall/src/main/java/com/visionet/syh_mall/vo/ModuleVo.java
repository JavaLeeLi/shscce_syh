package com.visionet.syh_mall.vo;

import java.util.LinkedList;
import java.util.List;

import com.visionet.syh_mall.entity.admin.SystemModule;

/**
 * 系统模块
 * @author xiaofb
 * @time 2017年8月18日
 */
public class ModuleVo {
	private String id;
	private String name;
	private String text;
	private String router;
	private String moduleIcon;
	private List<ModuleVo> subModules;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<ModuleVo> getSubModules() {
		return subModules;
	}
	public void setSubModules(List<ModuleVo> subModules) {
		this.subModules = subModules;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public String getModuleIcon() {
		return moduleIcon;
	}
	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}
	
	
	/**
	 * model转vo
	 * @param systemModule
	 * @return
	 */
	public static List<ModuleVo> changeModel(List<SystemModule> list){
		List<ModuleVo> voList = new LinkedList<ModuleVo>();
		ModuleVo menu = null;
		for (SystemModule systemModule : list) {
			menu = new ModuleVo();
			menu.setId(systemModule.getId());
			menu.setName(systemModule.getModuleCode());
			menu.setText(systemModule.getModuleName());
			menu.setRouter(systemModule.getModuleRoute());
			menu.setModuleIcon(systemModule.getModuleIcon());
			voList.add(menu);
		}
		return voList;
	}
	
	
}
