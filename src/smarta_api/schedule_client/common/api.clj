(ns smarta-api.schedule-client.common.api
  (:require [clojure.string :as string]
            [smarta-api.schedule-client.common.config :as config]))


(def marta-api-key (:marta-api-key config/config))
(def marta-api-base-uri (:marta-api-uri config/config))
(def marta-api-key-suffix (string/replace "?apiKey=%s" #"%s" marta-api-key))
(def marta-api-get-rail-schedule (str "/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals" marta-api-key-suffix))
(def marta-api-get-bus-list-all (str "/BRDRestService/RestBusRealTimeService/GetAllBus" marta-api-key-suffix))
(def marta-api-get-bus-route-schedule (str "/BRDRestService/RestBusRealTimeService/GetBusByRoute/%s" marta-api-key-suffix))

(defn get-rail-arrivals-endpoint [] (str marta-api-base-uri marta-api-get-rail-schedule))
(defn get-bus-list-endpoint [] (str marta-api-base-uri marta-api-get-bus-list-all))
(defn get-bus-arrivals-endpoint [route-number]
  (string/replace (str marta-api-base-uri marta-api-get-bus-route-schedule) #"%s" route-number))
