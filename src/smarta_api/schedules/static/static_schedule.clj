(ns smarta-api.schedules.static.static-schedule
  (:require [clojure.string :as string]
            [smarta-api.schedules.common.util :refer :all]
            [camel-snake-kebab.core :refer :all]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def rail-file-location "data/schedules/rail/%s/%s/%s.csv")

(def rail-schedules ["weekday" "saturday" "sunday"])

(def rail-lines  {"red" ["northbound" "southbound"]
                  "gold" ["northbound" "southbound"]
                  "blue" ["eastbound" "westbound"]
                  "green" ["eastbound" "westbound"]})

(defn rotate-matrix [matrix]
  (apply mapv vector matrix))

(defn get-rail-schedule-file-name [schedule line direction]
  (format rail-file-location schedule line direction))

(defn load-static-rail-schedule [schedule line direction]
  (with-open [reader (io/reader  (get-rail-schedule-file-name schedule line direction))]
    (rotate-matrix
     (doall
      (csv/read-csv reader)))))

(defn load-rail-station-arrivals [schedule line direction]
  (let [static-schedule (load-static-rail-schedule schedule line direction)]
    (reduce
     (fn [acc item]
      (merge acc
              {
               :station-name (get item 0)
               :arrivals (drop 1 item)
              })) [] static-schedule)))

(defn load-rail-directions [schedule line directions]
    (reduce (fn [acc item]
              (assoc acc (keyword item) (load-rail-station-arrivals schedule line item))
              ) {} directions))

(defn load-rail-lines [schedule lines]
  (reduce (fn [acc item]
            (assoc acc (keyword item) (load-rail-directions schedule item (rail-lines item)))
            ) {} lines))

(defn load-rail-schedule []
  (reduce (fn [acc item]
            (assoc acc (keyword item) (load-rail-lines item (keys rail-lines)))
            ) {} rail-schedules))
