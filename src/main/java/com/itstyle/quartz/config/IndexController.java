package com.itstyle.quartz.config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author xuzhen
 * @version 1.0
 * @date 2023/5/5 16:05
 */
@Controller
public class IndexController {

	/**
	 * 页面跳转
	 * @param url
	 * @return
	 */
	@RequestMapping("{url}.shtml")
	public String page(@PathVariable("url") String url) {
		return  url;
	}
	/**
	 * 页面跳转(二级目录)
	 * @param module
	 * @param url
	 * @param url
	 * @return
	 */
	@RequestMapping("{module}/{url}.shtml")
	public String page(@PathVariable("module") String module,@PathVariable("url") String url) {
		return module + "/" + url;
	}

}
