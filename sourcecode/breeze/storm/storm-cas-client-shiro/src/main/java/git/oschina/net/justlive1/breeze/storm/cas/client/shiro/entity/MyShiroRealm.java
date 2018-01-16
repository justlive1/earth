/**
 * 
 */
package git.oschina.net.justlive1.breeze.storm.cas.client.shiro.entity;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author liuwenlai
 *
 */
@SuppressWarnings("deprecation")
public class MyShiroRealm extends CasRealm {

	@PostConstruct
	public void initProperty() {
		// TODO
		setCasServerUrlPrefix("");
		setCasService("");
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
		return null;
	}

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken paramAuthenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) paramAuthenticationToken;
		// TODO
		if (token.getUsername().equals("")) {
			return new SimpleAuthenticationInfo("", "", getName());
		} else {
			throw new AuthenticationException();
		}
	}

}
