package justlive1.earth.breeze.lighting.chained.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps.Remote;
import justlive1.earth.breeze.lighting.chained.core.manage.StepManager;
import justlive1.earth.breeze.lighting.chained.core.step.StepContext;
import justlive1.earth.breeze.lighting.chained.model.webhook.Payload;
import justlive1.earth.breeze.snow.common.base.util.Checks;

/**
 * api控制层
 * 
 * @author wubo
 *
 */
@RestController
@RequestMapping("/api")
public class ApiController {

  static final String PING = "ping";
  static final String PONG = "pong";

  @Autowired
  StepManager stepManager;

  /**
   * webhook调用
   * 
   * @param source
   * @return
   */
  @RequestMapping("/github/webhook")
  public ResponseEntity<String> webhook(@RequestBody Payload payload,
      @RequestHeader("User-Agent") String agent, @RequestHeader("X-GitHub-Event") String event,
      @RequestHeader("X-Hub-Signature") String signature) {

    if (PING.equals(event)) {
      return ResponseEntity.ok().body(PONG);
    }

    Remote remote = Checks.notNull(stepManager.findByType(CoreProps.REMOTE_TYPE.GITHUB.name()));

    if (!agent.startsWith(remote.getAgent())
        || (remote.getEvents() != null && !remote.getEvents().contains(event))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    String ref = payload.getRef().replace(remote.getRefPrefix(), "");
    String url =
        payload.getRepository().getUrl() + remote.getFilePrefix() + ref + remote.getFileSuffix();
    String projectName = ref + "-" + payload.getRepository().getName();

    StepContext ctx = new StepContext();
    ctx.put(StepContext.REMOTE, remote);
    ctx.put(StepContext.REMOTE_FILE, url);
    ctx.put(StepContext.PROJECT_NAME, projectName);

    stepManager.excute(ctx);

    return ResponseEntity.ok().body(PONG);
  }

}
