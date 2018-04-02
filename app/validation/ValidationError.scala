package validation

import models.{Contact, PhoneNumber}
import play.api.libs.json.{Format, Json}

sealed trait ValidationError

object ValidationError {

  case class ContactAlreadyExist(contact: Contact)  extends ValidationError

  object ContactAlreadyExist {
    implicit val ContactAlreadyExistFormat: Format[ContactAlreadyExist] = Json.format[ContactAlreadyExist]
  }

  case class InvalidContactType(contact: Contact, errorMsg: String) extends ValidationError

  object InvalidContactType {
    implicit val InvalidContactTypeFormat: Format[InvalidContactType] = Json.format[InvalidContactType]
  }

  case class InvalidPhoneNumber(phoneNumber: PhoneNumber, errorMsg: String) extends ValidationError

  object InvalidPhoneNumber {
    implicit val InvalidPhoneNumberFormat: Format[InvalidPhoneNumber] = Json.format[InvalidPhoneNumber]
  }

//  case class PhoneNumberAlreadyExistInContact(phoneNumber: PhoneNumber, errorMsg: String) extends ValidationError
//
//  object PhoneNumberAlreadyExistInContact {
//    implicit val PhoneNumberAlreadyExistInContactFormat: Format[PhoneNumberAlreadyExistInContact] = Json.format[PhoneNumberAlreadyExistInContact]
//  }
//  implicit  val jsonFormat: Format[ValidationError] = Jsonx.formatSealed[ValidationError]
}
















