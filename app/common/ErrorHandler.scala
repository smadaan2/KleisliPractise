package common

import models.Contact
import play.api.libs.json._
import play.api.mvc.Result
import play.api.mvc.Results._

object ErrorHandler {

  def contactNotValid(contact: Contact): Result = {
    val errorMessage = "contact has "
    BadRequest(Json.toJson("", errorMessage, None))
  }

}



