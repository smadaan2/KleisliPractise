package repository

import java.util.UUID

import models.Contact

import scala.collection.mutable.ListBuffer

trait PhoneBook {
  val contactsList: ListBuffer[Contact] = ListBuffer()

  def generateId: Option[String] = Some(new UUID(1, 100).toString)
}

object PhoneBook extends PhoneBook
