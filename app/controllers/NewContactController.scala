package controllers

import common.{Async, Pimpers}
import errors.FailureFactory
import models.Contact
import play.api.libs.json.JsValue
import play.api.mvc.Request
import services.impl.{ContactRepositoryService, HttpError}
import validation.ValidationContactService

import scalaz._


class ControllerServices[M[_],Failure](contactRepoService: ContactRepositoryService[M]) {
  val pimpers: Pimpers[M, Failure] = new Pimpers[M, Failure]
  import pimpers._
  val makeContactRequest = Kleisli{request: Request[JsValue] => request.body.validate[Contact] >> identity }
  val createContact: Kleisli[M, Contact, \/[HttpError,Contact]] = Kleisli{ ct: Contact => contactRepoService.create(ct)}
  //def updateContact(contactId: String): Kleisli[M, Contact, \/[HttpError,Contact]] = Kleisli{ct: Contact => contactRepoService.update(contactId,ct) }
}


class NewContactController[M[_],Failure](contactRepoService: ContactRepositoryService[M])
                                        (implicit validationOfContact: ValidationContactService[Failure],
                                         failures: FailureFactory[Failure],
                                         aync: Async[M,Failure]) extends ControllerServices[M,Failure](contactRepoService) {
  import pimpers._
  val createNewContact = makeContactRequest >|> validationOfContact.validators >+> createContact
  //def updateContact(contactId: String) = makeContactRequest >|> validationOfContact.validators  >+> updateContact(contactId)
}


//trait MAndFailureAdapter[M[_], Failure] {
//
//  def futureOptionToAsync[T](f: M[Option[T]], error: => Failure): M[T]
//
//}
//
//object MAndFailureAdapter {
//  override def futureOptionToFuture[T](f: Future[Option[T]], error: => Result): Future[T] = HttpResult.fromFOption(error)(exceptionToFailure)(f)
//}



