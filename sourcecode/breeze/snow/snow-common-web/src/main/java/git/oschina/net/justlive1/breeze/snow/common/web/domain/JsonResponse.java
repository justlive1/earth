package git.oschina.net.justlive1.breeze.snow.common.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * json返回实体
 * 
 * @author wubo
 *
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JsonResponse<T> {

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 信息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	@NonNull
	private T data;

	/**
	 * 成功返回
	 * 
	 * @param data
	 * @return
	 */
	public static <E> JsonResponse<E> success(E data) {
		JsonResponse<E> resp = new JsonResponse<E>(data);
		resp.setSuccess(true);
		return resp;
	}

	/**
	 * 失败返回
	 * 
	 * @return
	 */
	public static JsonResponse<Void> error(String message) {
		JsonResponse<Void> resp = new JsonResponse<>();
		resp.setMessage(message);
		return resp;
	}
}
