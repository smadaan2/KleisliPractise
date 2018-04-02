package errors

import controllers.ApiErrors
import models.Contact
import play.api.libs.json.{JsError, JsPath, JsonValidationError}
import play.api.mvc.Result

trait FailureFactory[Failure] {
  def invalidContactType(ct: Contact): Failure
  def invalidPhonetype(ct: Contact): Failure
  def invalidPhoneNumber(ct: Contact): Failure
  def contactAlreadyExists(ct: Contact): Failure
}

object FailureFactory {
  implicit object ResultFailureFactory extends FailureFactory[Result] {
    override def invalidContactType(ct: Contact): Result = invalidContactType(ct)

    override def invalidPhonetype(ct: Contact): Result = invalidPhonetype(ct)

    override def invalidPhoneNumber(ct: Contact): Result = invalidPhoneNumber(ct)

    override def contactAlreadyExists(ct: Contact): Result = contactAlreadyExists(ct)
  }
}

trait FailureMaker[X, Failure] extends (X => Failure)

object FailureMaker {
  implicit val exceptionToFailure: FailureMaker[Throwable, Result] = new FailureMaker[Throwable, Result] {
    override def apply(v1: Throwable): Result = throw v1
  }

  implicit val failureMaker: FailureMaker[Seq[(JsPath, Seq[JsonValidationError])], Result] = new FailureMaker[Seq[(JsPath, Seq[JsonValidationError])], Result] {
    override def apply(v1: Seq[(JsPath, Seq[JsonValidationError])]): Result = ApiErrors.badJson(JsError(v1))
  }
}

