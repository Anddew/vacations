package com.evolutiongaming.vacations.config

import com.evolutiongaming.vacations.config.AppConfig.{KafkaConfig, ServerConfig}
import com.typesafe.config.Config
import pureconfig.ConfigSource
import pureconfig.generic.auto._

case class AppConfig(
  server: ServerConfig,
  kafka: KafkaConfig
)


object AppConfig {
  case class ServerConfig(
    host: String,
    port: Int
  )

  case class KafkaConfig()

  def apply(config: Config): AppConfig =
    ConfigSource.fromConfig(config).at("app").loadOrThrow[AppConfig]
}