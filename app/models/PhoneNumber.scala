package models

import models.PhoneNumber.PhoneType
import play.api.libs.json._

case class PhoneNumber(number: Long, `type`: PhoneType) {
  override def toString: String = number.toString
}

object PhoneNumber {
  implicit val PhoneNumberFormat: Format[PhoneNumber] = Json.format[PhoneNumber]

  sealed abstract class PhoneType(val value: String)
  case object Mobile extends PhoneType("mobile")
  case object Fixed  extends PhoneType("fixed")

  object PhoneType {

    implicit val identifyeeTypeFormat: Format[PhoneType] = new Format[PhoneType] {
      override def reads(json: JsValue) = json match {
        case JsString(s) => s match {
          case Mobile.value => JsSuccess[PhoneType](Mobile)
          case Fixed.value => JsSuccess[PhoneType](Fixed)
          case _ => JsError(s + "is not a supported phone type")
        }
        case _ => JsError("received wrong data type for phonetyp")
      }

      override def writes(enum: PhoneType) = JsString(enum.value)
    }
  }
}

//sealed trait PhoneType
//case object Mobile extends PhoneType
//case object Fixed  extends PhoneType

