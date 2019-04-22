(ns smarta-api.schedules.live.bus.core
  (:require [cheshire.core :refer :all]
            [clj-http.client :as client]
            [clojure.string :as string]
            [smarta-api.schedules.common.api :as api]
            [smarta-api.schedules.common.util :refer :all]
            [camel-snake-kebab.core :refer :all]))

(defn get-bus-schedule []
  (parse-string (:body (client/get (api/get-bus-list-endpoint))) to-keyword))

(def bus-details (reduce (fn [acc item] (conj acc {:route (:route item)
                                                   :stopid (:stopid item)
                                                   :direction (:direction item)
                                                   :timepoint (:timepoint item)
                                                   :latitude (:latitude item)
                                                   :longitude (:longitude item)})) [] (get-bus-schedule)))

(def bus-timepoints (distinct (map #(:timepoint %) bus-details)))

(def bus-routes (distinct (map #(:route %) bus-details)))

(def bus-stops (distinct (map #(:stopid %) bus-details)))

(defn get-routes-by-stop [stop])

(defn get-stop-by-route [route]
  (filter #(= (:route %) route) bus-details))

(defn get-stops-by-timepoint [timepoint]
  (filter #(= (:timepoint %) timepoint) bus-details))
