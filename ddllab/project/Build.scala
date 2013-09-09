import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "ddllab"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final", 
    "mysql" % "mysql-connector-java" % "5.1.25", 
    javaCore,
    javaJdbc,
    javaJpa
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
