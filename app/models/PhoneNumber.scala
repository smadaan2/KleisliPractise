package models

import play.api.libs.json.{Format, Json}

case class PhoneNumber(number: Long, `type`: PhoneType) {
  override def toString: String = number.toString
}

object PhoneNumber {
  implicit val PhoneNumberFormat: Format[PhoneNumber] = Json.format[PhoneNumber]
}

sealed trait PhoneType
case object Mobile extends PhoneType
case object Fixed  extends PhoneType

object PhoneType {
  implicit val  implicitsPhoneType : Format[PhoneType] = Json.format[PhoneType]
}