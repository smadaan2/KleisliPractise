package common
import com.typesafe.scalalogging.LazyLogging
import controllers.{Assets, AssetsComponents, ContactController, HomeController}
import play.api.ApplicationLoader.Context
import play.api.mvc.Result
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator}
import play.filters.HttpFiltersComponents
import repository.PhoneBook
import router.Routes
import services.impl.ApiContactRepositoryService
import scala.concurrent.Future
import scalaz._
import Scalaz._
import std.scalaFuture._

class PhoneBookApi extends ApplicationLoader {
  def load(context: Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }
    new MyComponents(context).application
  }
}

class MyComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with HttpFiltersComponents
  with AssetsComponents
  with LazyLogging {

  private lazy val homeController = new HomeController(controllerComponents)
  private lazy val contactRepo = new ApiContactRepositoryService[Future,Result](PhoneBook)
  private lazy val contactController = new ContactController(controllerComponents,contactRepo)
  private lazy val assetsController: Assets = new Assets(httpErrorHandler,assetsMetadata)

  lazy val router: Routes = new Routes(httpErrorHandler,
    homeController,
    contactController,
    assetsController)

}