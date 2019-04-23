(ns smarta-api.schedules.static.core
  (:require [smarta-api.schedules.common.util :refer :all]
            [smarta-api.schedules.static.static-schedule :as stan]))

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
      (concat acc (get-in stan/rail-lines [item]))) [] (get-lines))))

(defn get-schedules []
  stan/rail-schedules)

(defn get-station [name schedule line direction]
  (first (filter #(= (% :station-name) name) (get-in static-rail-schedule [schedule line direction]))))

(defn compose-station-schedule [station-name schedule line directions]
  (reduce
   (fn [nested-acc direction]
     (let [station (get-station station-name schedule (keyword line) (keyword direction))]
       (if (nil? station) nested-acc (assoc nested-acc (keyword direction) (station :arrivals))))) {} directions))

(defn get-schedule-by-station [schedule station-name]
  (reduce
   (fn [acc item]
     (let [[line directions] item
           composed-schedule (compose-station-schedule station-name schedule line directions)]
       (if (empty? composed-schedule) acc (assoc acc (keyword line) composed-schedule)))) {:station-name station-name} stan/rail-lines))
