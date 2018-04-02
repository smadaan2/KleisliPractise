package services.impl

import common.Async
import models.Contact
import play.api.libs.json.{Format, Json}
import repository.PhoneBook

import scalaz.syntax.either._
import scalaz.\/

final case class HttpError(status: Int, message: String)

object HttpError {
  implicit val HttpErrorImplicits: Format[HttpError] = Json.format[HttpError]
}

class ApiContactRepositoryService[M[_], Failure](phonebook: PhoneBook)(implicit m: Async[M, Failure]) extends ContactRepositoryService[M] {

  override def create(contact: Contact): M[\/[HttpError,Contact]] = {
    val contactWithId: Contact = contact.copy(id = phonebook.generateId)
    phonebook.contactsList.append(contactWithId)
      m.pure(contactWithId.right[HttpError])
  }

//  override def update(contactId: String, updtcontact: Contact): M[\/[HttpError,Contact]] = {
  //    findContactFromPhonebook(contact => contact.id == Some(contactId)).toRight(HttpError(404,"contact not found"))
  //      .map(contact => contact.copy(name = updtcontact.name, contactType = updtcontact.contactType, phoneNumbers = updtcontact.phoneNumbers)).pure
  //  }
  //
  //  override def delete(contactId: String): M[Contact] = phonebook.contactsList.remove(findIndex(contactId)).right.pure
  //
  //  override def listContactsByType(cType: String): M[List[Contact]] =
  //    filterContactFromPhonebook(contact => contact.contactType.toString == cType).pure
  //
  //  override def listContactsByPartialName(name: String): M[List[Contact]] =
  //    filterContactFromPhonebook(contact => contact.name.contains(name)).pure
  //
  //  override def listPhoneNumberOfContact(contactId: String): M[List[PhoneNumber]] =
  //    filterContactFromPhonebook(contact => contact.id == contactId).flatMap(_.phoneNumbers).pure

//  override def listPhoneNumberOfContactFilterByType(contactId: String, ptype: String): M[List[PhoneNumber]] =
//    filterContactFromPhonebook(contact => contact.id == contactId).flatMap(_.phoneNumbers).filter(_.phoneType == ptype).pure
//
//  override def addPhoneNumberInContact(contactId: String, phoneNumber: PhoneNumber): M[Option[Contact]] =
//    findContactFromPhonebook(contact => contact.id == contactId).map(_.addPhoneNumber(phoneNumber)).pure
//
//  override def deletePhoneNumberFromContact(contactId: String, phoneNumber: Long): M[Option[Contact]] = {
//    filterContactFromPhonebook(contact => contact.id == contactId).head.deletePhoneNumber(phoneNumber).pure
//  }

  private def findIndex(contactId: String): Int = phonebook.contactsList.toList.zipWithIndex.filter { case (contact, index) => contact.id == contactId }.head._2

  private def filterContactFromPhonebook(f: Contact => Boolean): List[Contact] = phonebook.contactsList.toList.filter(f)

  private def findContactFromPhonebook(f: Contact => Boolean): Option[Contact] = phonebook.contactsList.toList.find(f)

}



