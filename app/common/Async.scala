package common

import play.api.mvc.Result
import scala.concurrent.Future
import scala.util.{ Right, Left }
import scala.concurrent.ExecutionContext.Implicits.global

trait Async[M[_], Failure] {
  def flatMap[T, T1](m: M[T])(fn: T => M[T1]): M[T1]
  def map[T,T1](m: M[T]) (fn: T => T1): M[T1]
  def pure[T](t: T): M[T]
  def fail[T](f: Failure): M[T]
}

case class ResultException(result: Result) extends Throwable

object Async {
  implicit val futureAync: Async[Future, Result] = new Async[Future, Result] {
    override def map[T, T1](m: Future[T])(fn: T => T1): Future[T1] = m.map(fn)
    override def pure[T](t: T): Future[T] = Future.successful(t)
    override def flatMap[T, T1](m: Future[T])(f: T => Future[T1]): Future[T1] = m.flatMap(f)
    override def fail[T](f: Result): Future[T] = Future.failed[T](ResultException(f)) recoverWith {
      case ResultException(result) => result
    }
  }
}