package validation


trait Validation
object Validation {
  def apply[T, Failure](validations: (T => Seq[Failure])*): (T => Seq[Failure]) = new CompositeValidation(validations)
}

class CompositeValidation[T, Failure](validations: Seq[(T => Seq[Failure])]) extends (T => Seq[Failure]){
  def apply(t: T): Seq[Failure] = validations.flatMap(v => v(t))
}

