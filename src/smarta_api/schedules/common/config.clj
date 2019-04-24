(ns smarta-api.schedules.common.config
  (:require [environ.core :refer [env]]))

(def config {:marta-api-key (or (env :marta-api-key) "some-api-key")
             :marta-api-uri (or (env :marta-api-uri) "http://developer.itsmarta.com")})
