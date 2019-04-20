(ns smarta-api.schedules.common.config
  (:require [environ.core :refer [env]]))


(def config {:marta-api-key (or (env :marta-api-key) "475ad2ba-5928-4063-9d00-ae06fbb02f3c")
             :marta-api-uri (or (env :marta-api-uri) "http://developer.itsmarta.com")})
