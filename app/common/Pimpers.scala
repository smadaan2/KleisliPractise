package common

import errors.{FailureAdapter, FailureMaker}
import play.api.libs.json.{JsPath, JsResult, JsonValidationError}

import scalaz.Kleisli

class Pimpers[M[_], Failure](implicit async: Async[M, Failure]) {

  implicit class KliesliPimper[Req, Res](kleisli: Kleisli[M, Req, Res]) {
    def >|>(validation: Res => Seq[Failure])(implicit failureMaker: FailureMaker[Seq[Failure], Failure]): Kleisli[M, Req, Res] = Kleisli{ req: Req =>
      val result = kleisli(req)
      result >|> validation
    }

    def >+>[Res2](fn: Kleisli[M, Res, Res2]): Kleisli[M,Req, Res2] = Kleisli{ req: Req =>
      kleisli(req).flatMap(res => fn(res))
    }
  }

  implicit class JsResultPimper[T](jsResult: JsResult[T]) {
    def >>[T1](fn: T => T1)(implicit failureMaker: FailureMaker[Seq[(JsPath, Seq[JsonValidationError])], Failure]): M[T1] = jsResult.fold(_.turnIntoFailure[T1], fn(_).lift)
  }

  implicit class AsyncPimper[T](m: M[T]) {
    def map[T1](fn: T => T1): M[T1] = async.map(m)(fn)
    def flatMap[T1](fn: T => M[T1]): M[T1] = async.flatMap(m)(fn)
    def >|>(validation: T => Seq[Failure])(implicit failureMaker: FailureMaker[Seq[Failure], Failure]): M[T] = m.flatMap { res =>
      validation(res) match {
        case Nil => (res.lift: M[T])
        case Seq(apierror) => (async.fail(apierror): M[T])
        case f => (f.turnIntoFailure: M[T])
      }
    }
  }

  implicit class AnyPimper[T](t: T) {
    def lift: M[T] = async.pure[T](t)
    def turnIntoFailure[Res](implicit failureMaker: FailureMaker[T, Failure]): M[Res] = async.fail(failureMaker(t))
  }

//  implicit class AnyPimper1[T](t: Throwable) {
//    def turnIntoFailure[Res](implicit failureMaker: FailureMaker[T, Failure]): M[Res] = async.fail1(t)
//  }

  implicit class FailurePimper(failure: Failure) {
    def fail[T]: M[Failure] = async.fail(failure)
  }
}

object Pimpers{
  def apply[M[_],Failure](implicit async: Async[M, Failure],failureAdapter: FailureAdapter[Failure]): Pimpers[M,Failure] = new Pimpers
}