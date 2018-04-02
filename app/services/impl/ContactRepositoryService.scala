package services.impl

import models.{Contact, PhoneNumber}

import scalaz.\/

trait ContactRepositoryService[M[_]] {

  def create(contact: Contact): M[\/[HttpError,Contact]]

//  def update(contactId: String, contact: Contact): M[\/[HttpError,Contact]]
//
//  def delete(contactId: String): M[\/[HttpError,Contact]]
//
//  def listContactsByType(contactType: String): M[List[Contact]]
//
//  def listContactsByPartialName(name: String): M[List[Contact]]
//
//  def listPhoneNumberOfContact(contactId: String): M[List[PhoneNumber]]

  //def listPhoneNumberOfContactFilterByType(contactId: String, ptype: String): M[List[PhoneNumber]]

//  def addPhoneNumberInContact(contactId: String,phoneNumber: PhoneNumber): M[Option[Contact]]
//
//  def deletePhoneNumberFromContact(contactId: String, phoneNumber: Long): M[Option[Contact]]

}









