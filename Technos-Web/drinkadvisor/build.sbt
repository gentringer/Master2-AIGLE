name := "drinkadvisor2"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     



libraryDependencies ++= Seq(
    javaJdbc,
    javaEbean,
    cache,
    "mysql" % "mysql-connector-java" % "5.1.27"
)


play.Project.playJavaSettings


