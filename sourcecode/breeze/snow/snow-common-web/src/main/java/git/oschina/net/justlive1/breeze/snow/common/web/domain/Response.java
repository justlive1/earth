package git.oschina.net.justlive1.breeze.snow.common.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json返回实体
 * 
 * @author wubo
 *
 */
@Data
@NoArgsConstructor
public class Response<T> {

	public static final String SUCCESS = "00000";
	public static final String FAIL = "-99999";
	/**
	 * 返回结果编码
	 */
	private String code;

	/**
	 * 结果描述信息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private T data;

	/**
	 * 成功返回
	 * 
	 * @param data
	 * @return
	 */
	public static <E> Response<E> success(E data) {
		Response<E> resp = new Response<E>();
		resp.setData(data);
		resp.setCode(SUCCESS);
		return resp;
	}

	/**
	 * 失败返回
	 * 
	 * @return
	 */
	public static Response<Void> error(String message) {
		Response<Void> resp = new Response<>();
		resp.setCode(FAIL);
		resp.setMessage(message);
		return resp;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return SUCCESS.equals(code);
	}
}
