package controllers

import javax.inject._
import org.dsa.iot.dslink.{DSLink, DSLinkFactory, DSLinkHandler}
import org.dsa.iot.dslink.node.Node
import org.dsa.iot.dslink.node.value.{Value, ValueType}
import org.slf4j.LoggerFactory
import play.api._
import play.api.libs.json._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  private val log = LoggerFactory.getLogger(getClass)
  log.info("Starting UrlFormListenerDSLink")

  val dslink = UrlFormListenerDSLinkHandler()

  val brk = "https://localhost:8443/conn"

  val provider = DSLinkFactory.generate(Array("--broker", brk), dslink)
  provider.start()


  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def postUrlForm() = Action(parse.formUrlEncoded) { implicit request: Request[Map[String, Seq[String]]] =>
    dslink.setJson(Json.toJson(request.body.mapValues(v => v.head)))
    Ok
  }
}

case class UrlFormListenerDSLinkHandler() extends DSLinkHandler {
  private val log = LoggerFactory.getLogger(getClass)
  override val isResponder = true

  var posted: Option[Node] = None

  override def onResponderInitialized(link: DSLink): Unit = {
    log.info("UrlFormListenerDSLink base initialized")
    val superRoot = link.getNodeManager.getSuperRoot

    val node = superRoot
      .createChild("Posted", true)
      .setDisplayName("Posted")
      .setValueType(ValueType.STRING)
      .setValue(new Value(""))
      .build

    posted = Some(node)
  }

  override def onResponderConnected(link: DSLink): Unit = {
    log.info("UrlFormListenerDSLink connected")
  }

  def setJson(newValueofRootNode: JsValue): Unit = {
    posted.map(n => n.setValue(new Value(newValueofRootNode.toString)))
  }
}
