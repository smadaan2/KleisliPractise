package controllers

import models.Contact
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import services.impl.ContactRepositoryService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaz.{-\/, \/-}

class ContactController(cc: ControllerComponents, contactRepo: ContactRepositoryService[Future]) extends AbstractController(cc) {

  val newContactController: NewContactController[Future, Result] = new NewContactController[Future, Result](contactRepo)

  def create = Action.async(parse.json) { request =>
    newContactController.createNewContact(request).map {
      case -\/(ex) => Forbidden(Json.toJson(ex))
      case \/-(s) => Ok(Json.toJson(s))
    }
  }


//  def update(contactId: String) = Action.async(parse.json) { request =>
//    val contactResult: JsResult[Contact] = request.body.validate[Contact]
//    contactResult.fold(
//      errors => Future.successful(BadRequest(JsError.toJson(errors))),
//      contact => contactService.update(contactId, contact).map {
//        case Right(result) => Ok(Json.toJson(result))
//        case Left(e) => BadRequest(Json.toJson(e))
//      }
//    )
//  }
//
//
//  def delete(contactId: String) = Action.async {
//    contactService.delete(contactId).map(result => Ok(Json.toJson(result)))
//  }
//
//  def listContactsByType(contacttype: String) = Action.async {
//    contactService.listContactsByType(contacttype).map(result => Ok(Json.toJson(result)))
//  }
//
//  def listContactsByPartialName(name: String) = Action.async {
//    contactService.listContactsByPartialName(name).map(result => Ok(Json.toJson(result)))
//  }
//
//  def listPhoneNumberOfContact(contactId: String) = Action.async {
//    contactService.listPhoneNumberOfContact(contactId).map(result => Ok(Json.toJson(result)))
//  }

//  def listPhoneNumberOfContactFilterByType(id: String, ptype: String) = Action.async {
//    contactService.listPhoneNumberOfContactFilterByType(id, ptype).map(result => Ok(Json.toJson(result)))
//  }
//
//  def addPhoneNumberInContact(contactId: String) = Action.async(parse.json) { request =>
//    val phoneResult: JsResult[PhoneNumber] = request.body.validate[PhoneNumber]
//    phoneResult.fold(
//      errors => Future.successful(BadRequest(JsError.toJson(errors))),
//      phoneNumber => contactService.addPhoneNumberInContact(contactId, phoneNumber).map {
//        case Right(result) => Ok(Json.toJson(result))
//        case Left(e) => BadRequest(Json.toJson(e))
//      }
//    )
//  }
//
//
//  def deletePhoneNumberFromContact(id: String, phonenumber: String) = Action.async {
//    contactService.deletePhoneNumberFromContact(id, phonenumber.toLong).map(result => Ok(Json.toJson(result)))
//  }

}



