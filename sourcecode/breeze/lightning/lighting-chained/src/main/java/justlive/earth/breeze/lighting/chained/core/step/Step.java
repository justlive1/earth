package justlive.earth.breeze.lighting.chained.core.step;

/**
 * 步骤接口
 * 
 * @author wubo
 *
 */
public interface Step {

  /**
   * 唯一标示
   * 
   * @return
   */
  String unqueId();

  /**
   * 前置处理
   * 
   * @param ctx
   */
  default void before(StepContext ctx) {}

  /**
   * 处理
   * 
   * @param ctx
   */
  void handle(StepContext ctx);

  /**
   * 后续处理
   * 
   * @param ctx
   */
  default void after(StepContext ctx) {}
}
