网站的安全架构

---

# 网站应用的攻击与防御

- XSS攻击
	- 跨站点脚本攻击(Cross Site Script)，指黑客通过篡改网页，注入恶意HTML脚本，在用户浏览网页时，控制用户浏览器进行恶意操作
	- 反射型 嵌入恶意脚本链接
	- 持久型 提交含有恶意脚本请求，保存到Web站点数据库
	- 消毒 对某些html危险字符转义，如">"转义成"&gt;"
	- HttpOnly 禁止页面JavaScript访问带有HttpOnly属性的Cookie，防止XSS攻击者窃取Cookie
	
- 注入攻击
	- SQL注入
		- 获取表结构信息的手段
			- 开源
			- 错误回显
			- 盲注
		- 避免方式
			- 消毒 和XSS一样 过滤请求
			- 参数绑定
	- OS注入

- CSRF工具
	- 跨站点请求伪造(Cross Site Request Forgery)，攻击者通过跨站请求，以合法用户的身份进行非法操作
	- 核心是利用浏览器Cookie或服务器Session策略，盗取用户身份
	- 表单Token
		- 页面表单增加随机数作为Token，每次响应Token不同
		- 页面正常提交会包含Token，伪造请求无法获取
		- 服务器校验请求参数中Token是否存在
	- 验证码
	- Referer check 图片防盗链
	
- 其他攻击和漏洞
	- Error Code
	- HTML注释
	- 文件上传
	- 路径遍历