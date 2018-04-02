package controllers

import controllers.Error.ErrorDetails
import play.api.libs.json._
import play.api.mvc.Result
import play.api.mvc.Results._

object ApiErrors {

   def invalidContactType[Req](ct: Req): Result = {
     val errorMessage = "contact type is invalid, make sure to use correct contact type"
     BadRequest(Json.toJson(Error("INVALID_CONTACT_TYPE", errorMessage, None, Seq.empty[ErrorDetails])))
   }

   def invalidPhonetype[Req](ct: Req): Result = {
     val errorMessage = "phone type is invalid, make sure to use correct phone type"
     BadRequest(Json.toJson(Error("INVALID_PHONE_TYPE", errorMessage, None, Seq.empty[ErrorDetails])))
   }

   def invalidPhoneNumber[Req](ct: Req): Result = {
     val errorMessage = "phone number is invalid, make sure to use correct phoneNumber"
     BadRequest(Json.toJson(Error("INVALID_PHONE_NUMBER", errorMessage, None, Seq.empty[ErrorDetails])))
   }

   def contactAlreadyExists[Req](ct: Req): Result = {
     val errorMessage = "contact already exits"
     Forbidden(Json.toJson(Error("CONTACT_ALREADY_EXISTS", errorMessage, None, Seq.empty[ErrorDetails])))
   }

  def badJson(jsError: JsError): Result = BadRequest(Json.toJson(Error("BAD_JSON", "The provided JSON does not conform to the expected schema.", None, Seq.empty[ErrorDetails])))
}


final case class Error(code: String,
                       message: String,
                       target: Option[String],
                       details: Seq[ErrorDetails])
object Error {
  final case class ErrorDetails(code: String,
                                message: String,
                                target: Option[String])
  object ErrorDetails{
    implicit val detailsErrorFormat: Format[ErrorDetails] = Json.format[ErrorDetails]
  }

  implicit val errorFormat: Format[Error] = Json.format[Error]
}



