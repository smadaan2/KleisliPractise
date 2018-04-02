package models

import play.api.libs.json._

case class Contact(id: Option[String], name: String, contactType: ContactType, phoneNumbers: List[PhoneNumber]) {

  private def addPhoneNumber(phoneNumber: PhoneNumber): Contact = this.copy(phoneNumbers = phoneNumbers :+ phoneNumber)

  private def deletePhoneNumber(phoneNumber: Long): Option[Contact] = phoneNumbers.find(pno => pno.number == phoneNumber).map(p => this.copy(phoneNumbers = remove(List(p), phoneNumbers)))

  private def remove[T](phoneNumber: List[T], phoneNumberList: List[T]): List[T] = phoneNumberList diff phoneNumber

  override def toString = s"$name($contactType)-${phoneNumbers}"
}

object Contact {
  implicit val customFormat: Format[Contact] = Json.format[Contact]
}








