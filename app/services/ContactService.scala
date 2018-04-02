//package services
//
//import common.AnyMonad
//import models.{Contact, PhoneNumber}
//import services.impl.ContactRepositoryService
//import validation.{ValidationError, ValidationService}
//import validation.ValidationUtils._
//
//
//class ContactService[M[_]](contactRepo: ContactRepositoryService[M], validateService: ValidationService)(implicit m: AnyMonad[M]) {
//
//  import common.AnyMonadPimper._
//
//  def create(contact: Contact): M[Either[List[ValidationError], Contact]] = {
//    for{
//     _ <- validateRequest()
//      _<-validateContactFromDatabase()
//      _<-createContact()
//    } yiel result
//
//
//    validateContact(contact) match {
//      case Left(e) =>
//        val y: Either[List[ValidationError], Contact] = Left[List[ValidationError], Contact](e)
//        y.pure
//      case Right(r) => contactRepo.create(r).map(a => Right(a))
//    }
//  }
//
//  def update(contactId: String, contact: Contact): M[Either[List[ValidationError], Option[Contact]]] = {
//    validateContact(contact) match {
//      case Left(e) =>
//        val y: Either[List[ValidationError], Option[Contact]] = Left[List[ValidationError], Option[Contact]](e)
//        y.pure
//      case Right(r) => contactRepo.update(contactId, r).map(a => Right(a))
//    }
//  }
//
//  def delete(contactId: String): M[Contact] = contactRepo.delete(contactId)
//
//  def listContactsByType(contactType: String): M[List[Contact]] = contactRepo.listContactsByType(contactType)
//
//  def listContactsByPartialName(name: String): M[List[Contact]] = contactRepo.listContactsByPartialName(name)
//
//  def listPhoneNumberOfContact(contactId: String): M[List[PhoneNumber]] = contactRepo.listPhoneNumberOfContact(contactId)
//
//  def listPhoneNumberOfContactFilterByType(contactId: String, ptype: String): M[List[PhoneNumber]] = contactRepo.listPhoneNumberOfContactFilterByType(contactId, ptype)
//
//  def addPhoneNumberInContact(contactId: String, phoneNumber: PhoneNumber): M[Either[List[ValidationError], Option[Contact]]] = {
//    validateService.validateAddingPhoneNumber(contactId, phoneNumber) match {
//        case Left(e) =>
//          val y: Either[List[ValidationError], Option[Contact]] = Left[List[ValidationError], Option[Contact]](e)
//          y.pure
//        case Right(r) => contactRepo.addPhoneNumberInContact(contactId, r).map(a => Right(a))
//      }
//  }
//
//  def deletePhoneNumberFromContact(contactId: String, phoneNumber: Long): M[Option[Contact]] = contactRepo.deletePhoneNumberFromContact(contactId, phoneNumber)
//
//}
//
//
//
//
//
