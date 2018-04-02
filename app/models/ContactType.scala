package models

import play.api.libs.json._

sealed abstract class ContactType(val value: String)

object ContactType {

  case object Personal extends ContactType("personal")

  case object Business extends ContactType("business")

  implicit val contactTypeFormat: Format[ContactType] = new Format[ContactType] {
    override def reads(json: JsValue) = json match {
      case JsString(s) => s match {
        case Personal.value => JsSuccess[ContactType](Personal)
        case Business.value => JsSuccess[ContactType](Business)
        case _ => JsError(s + "is not a supported identifyee type")

      }
      case _ => JsError("received wrong data type for identifyee")
    }

    override def writes(enum: ContactType) = JsString(enum.value)
  }
}
