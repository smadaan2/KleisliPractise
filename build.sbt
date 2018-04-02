name := "PhoneBookAPI"
 
version := "1.0" 
      
lazy val `phonebookapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( "com.typesafe.scala-logging" %% "scala-logging" % "3.6.0",
  "org.scalaz" %% "scalaz-core" % "7.2.16")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

      