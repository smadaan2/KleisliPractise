package validation

import models.{Contact, ContactType, PhoneNumber, PhoneType}
import repository._
import validation.ValidationError._
import validation.ValidationUtils._

import scala.collection.mutable.ListBuffer

class ValidationService(phonebook: PhoneBook) {
  def validateAddingPhoneNumber(contactId: String, phoneNumber: PhoneNumber): Either[List[ValidationError], PhoneNumber] = {
    phonebook.contactsList.find(contact => contact.id == contactId && contact.phoneNumbers.exists(pno => pno == phoneNumber)) match {
      case Some(_) => validatePhoneNumber(phoneNumber)
      case None => Left(List(InvalidPhoneNumber(phoneNumber, "PhonenUmber already exist in Contact")))
    }
  }
}

object ValidationUtils {

  //TODO : Want to implement as functional programming way
  import ValidationImplicits._

  val listOfErrors = ListBuffer[ValidationError]()

  def validateContact(contact: Contact): Either[List[ValidationError], Contact] = {
    if (!contact.contactType.isInstanceOf[ContactType]) listOfErrors.append(InvalidContactType(contact, "Invalid Contact Type"))
    contact.phoneNumbers.foreach(pno => validatePhoneNumber(pno))
    makeResult(listOfErrors, contact)
  }

  def validatePhoneNumber(pno: PhoneNumber): Either[List[ValidationError], PhoneNumber] = {
    val phoneNumber = pno.number.toString
    if (!(phoneNumber.isNumeric)) listOfErrors.append(InvalidPhoneNumber(pno, s"PhoneNumber should consist only numbers"))
    if (!(phoneNumber.length == 10)) listOfErrors.append(InvalidPhoneNumber(pno, s"Length should be 10 minimum digits"))
    if (!(pno.isInstanceOf[PhoneType])) listOfErrors.append(InvalidPhoneNumber(pno, s"Invalid Phone Type of phonenumber"))
    makeResult(listOfErrors, pno)
  }

  def makeResult[A](listOfErrors: ListBuffer[ValidationError], a: A) = if (listOfErrors.isEmpty) Right(a) else Left(listOfErrors.toList)
}

object ValidationImplicits {

  implicit class OpsNum(val str: String) extends AnyVal {
    def isNumeric = scala.util.Try(str.toDouble).isSuccess
  }

}


//    def validateContactPhoneNumber: Contact => Either[List[ValidationError],Contact] = { contact =>
//      contact.phoneNumbers.map{ pno =>
//        val phoneNumber = pno.number.toString
//        if(phoneNumber.isNumeric() && phoneNumber.length == 10) Right(contact)
//        else Left(InvalidPhoneNumber(pno,"Invalid Phone Number"))
//      }
//    }
//
//    def validateContactPhoneType: Contact => List[Either[ValidationError,Contact]] = { contact =>
//      contact.phoneNumbers.map{ pno =>
//        if(pno.phoneType.isInstanceOf[PhoneType]) Right(contact)
//        else Left(InvalidPhoneType(pno,"Phone type is not valid and valid values are [Mobile,Fixed]"))
//      }
//    }
//
//    def validateContactType: Contact => Either[ValidationError,Contact] = { contact =>
//      if (contact.contactType.isInstanceOf[ContactType]) Right(contact)
//      else Left(InvalidContactType(contact, "Contact type is not valid and valid values are [Personal,Business]"))
//    }
//