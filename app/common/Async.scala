package common

import play.api.mvc.Result
import scala.concurrent.Future


trait Async[M[_], Failure] {
  def flatMap[T, T1](m: M[T])(fn: T => M[T1]): M[T1]
  def map[T,T1](m: M[T]) (fn: T => T1): M[T1]
  def pure[T](t: T): M[T]
  def fail[T](f: Failure): M[T]
}

object Async{
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val futureAync: Async[Future, Result] = new Async[Future, Result] {
    override def map[T,T1](m: Future[T])( fn: T => T1): Future[T1]=m.map(fn)
    override def pure[T](t: T): Future[T] = Future.successful(t)
    override def flatMap[T, T1](m: Future[T]) (f: T => Future[T1]): Future[T1] =   m.flatMap(f)
    override def fail[T](f: Result): Future[Result]  = Future.successful(f)
  }
}
