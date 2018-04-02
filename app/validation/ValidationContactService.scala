package validation

import errors.FailureFactory
import models.PhoneNumber.PhoneType
import models.{Contact, ContactType}


class ValidationContactService[Failure](implicit failures: FailureFactory[Failure]) {
  import scala.util.matching.Regex
  val phoneNumberPattern = new Regex("/^[0-9]{0,9}$/")

  type ValidateFn = (Contact) => Seq[Failure]

  def require(b: Contact => Boolean)(fn: Contact => Failure): ValidateFn = { ct => if (b(ct)) Seq() else Seq(fn(ct)) }

  def validateContactType: ValidateFn = require{contact: Contact => contact.contactType.isInstanceOf[ContactType]} (failures.invalidContactType)

  def validatePhoneType: ValidateFn = require{contact: Contact => contact.phoneNumbers.exists(pno => pno.`type`.isInstanceOf[PhoneType])} (failures.invalidContactType)

  def validatePhoneNumber: ValidateFn = require{contact: Contact => contact.phoneNumbers.exists(pno => phoneNumberPattern.equals(pno))}(failures.invalidPhoneNumber)

  def validatePhoneNumberAlreadyExists: ValidateFn = ???

  def validators: ValidateFn = Validation[Contact,Failure](validateContactType, validatePhoneNumber, validatePhoneType)
}

object ValidationContactService {
  implicit def default[Failure](implicit failures: FailureFactory[Failure]): ValidationContactService[Failure] = new ValidationContactService[Failure]
}
