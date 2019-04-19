(ns smarta-api.schedule-client.static.core
  (:require [smarta-api.schedule-client.common.util :refer :all]
            [smarta-api.schedule-client.static.static-schedule :as stan]))

(def static-rail-schedule (stan/load-rail-schedule))

(defn get-stations [schedule line direction]
  (reduce
   (fn [acc item]
    (conj acc (item :station-name))) []
   ((get-in static-rail-schedule [schedule line]) direction)))

(defn get-lines []
 (keys stan/rail-lines))

(defn get-directions []
  (set
   (reduce
    (fn [acc item]
      (conj acc (get-in stan/rail-lines [item]))) [] (get-lines))))

(defn get-schedules []
  stan/rail-schedules)

(defn parse-static-schedule []
  )

(defn get-schedule-by-station [schedule station-name]
      (let [stations (parse-static-schedule static-rail-schedule)]
        (filter #(= (% :station-name) station-name) stations)))
